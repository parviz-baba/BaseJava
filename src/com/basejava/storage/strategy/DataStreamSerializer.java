package com.basejava.storage.strategy;

import com.basejava.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void write(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = ((ListSection) entry.getValue()).getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = ((OrganizationSection) entry.getValue()).getOrganizations();
                        dos.writeInt(orgs.size());
                        for (Organization org : orgs) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl() == null ? "" : org.getHomePage().getUrl());
                            List<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
                                dos.writeLong(position.getStartDate().toEpochDay());
                                dos.writeLong(position.getEndDate().toEpochDay());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                ContactType type = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(type, value);
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSize = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(type, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int orgSize = dis.readInt();
                        List<Organization> orgs = new ArrayList<>();
                        for (int j = 0; j < orgSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.isEmpty()) url = null;
                            List<Organization.Position> positions = new ArrayList<>();
                            int posSize = dis.readInt();
                            for (int k = 0; k < posSize; k++) {
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                long startDate = dis.readLong();
                                long endDate = dis.readLong();
                                positions.add(new Organization.Position(title, description, startDate, endDate));
                            }
                            orgs.add(new Organization(new Link(name, url), positions));
                        }
                        resume.addSection(type, new OrganizationSection(orgs));
                        break;
                }
            }
            return resume;
        }
    }
}