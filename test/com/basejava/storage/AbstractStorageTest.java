package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\projects\\storage");
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement11", "Achievement12", "Achievement13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Organization11", "https://Organization11.ru",
                        new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                        new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty")),
                        new Organization("Organization12", "https://Organization12.ru")));

        R2 = new Resume(UUID_2, "Name2");
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        R2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "https://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));

        R3 = new Resume(UUID_3, "Name3");
        R3.addContact(ContactType.EMAIL, "mail3@ya.ru");
        R3.addSection(SectionType.OBJECTIVE, new TextSection("Objective3"));

        R4 = new Resume(UUID_4, "Name4");
        R4.addContact(ContactType.PHONE, "44444");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        if (!STORAGE_DIR.exists()) {
            STORAGE_DIR.mkdirs();
        }
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume(R1.getUuid(), "Updated Name");
        updatedResume.getContacts().putAll(R1.getContacts());
        updatedResume.addContact(ContactType.EMAIL, "new_email@ya.ru");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(R1.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(R4));
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(R1.getUuid()));
        assertEquals(R2, storage.get(R2.getUuid()));
        assertEquals(R3, storage.get(R3.getUuid()));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(R1));
    }

    @Test
    public void delete() {
        storage.delete(R1.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(R1.getUuid()));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> expected = Arrays.asList(R1, R2, R3);
        Collections.sort(expected);
        assertEquals(expected, list);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}