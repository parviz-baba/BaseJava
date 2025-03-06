package com.basejava.storage.strategy;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void write(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeItems(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeItems(dos, r.getSections().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                if (section instanceof TextSection) {
                    dos.writeUTF(((TextSection) section).getContent());
                } else if (section instanceof ListSection) {
                    writeItems(dos, ((ListSection) section).getItems(), dos::writeUTF);
                } else if (section instanceof OrganizationSection) {
                    writeItems(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                        dos.writeUTF(org.getHomePage().getName());
                        dos.writeUTF(org.getHomePage().getUrl());
                        writeItems(dos, org.getPositions(), position -> {
                            writeLocalDate(dos, position.getStartDate());
                            writeLocalDate(dos, position.getEndDate());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        });
                    });
                }
            });
        }
    }

    private <T> void writeItems(DataOutputStream dos, Collection<T> items, ElementWriter<T> writer) throws IOException {
        dos.writeInt(items.size());
        for (T item : items) {
            writer.write(item);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeLong(ld.toEpochDay());
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private <T> List<T> readItems(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        if (sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE) {
            return new TextSection(dis.readUTF());
        } else if (sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS) {
            return new ListSection(readItems(dis, () -> dis.readUTF()));
        } else if (sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION) {
            return new OrganizationSection(
                    readItems(dis, () -> new Organization(
                            new Link(dis.readUTF(), dis.readUTF()),
                            readItems(dis, () -> new Organization.Position(
                                    readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()
                            ))
                    ))
            );
        }
        throw new IllegalStateException();
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.ofEpochDay(dis.readLong());
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }
}