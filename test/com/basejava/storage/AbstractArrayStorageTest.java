package com.basejava.storage;

import com.basejava.exception.StorageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;


import java.io.IOException;
import java.util.List;

public abstract class AbstractArrayStorageTest {
    private static final Resume RESUME_1 = new Resume("uuid1", "Name Surname 1");
    private static final Resume RESUME_2 = new Resume("uuid2", "Name Surname 2");
    private static final Resume RESUME_3 = new Resume("uuid3", "Name Surname 3");
    private static final Resume RESUME_4 = new Resume("uuid4", "Name Surname 4");
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveOverflow() {
        for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i, "Name " + i));
        }
        Assertions.assertThrows(StorageException.class, () ->
                storage.save(new Resume("overflow", "Overflow Name")));
    }

    @Test
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(RESUME_1.getUuid()));
    }

    @Test
    public void deleteNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getAll() throws IOException {
        List<Resume> resumes = storage.getAllSorted();
        Assertions.assertEquals(3, resumes.size());
    }

    @Test
    public void clear() throws IOException {
        storage.clear();
        assertSize(0);
        Assertions.assertTrue(storage.getAllSorted().isEmpty());
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume("uuid1", "Updated Name");
        storage.update(updatedResume);
        Assertions.assertEquals(updatedResume, storage.get("uuid1"));
    }

    private void assertSize(int expectedSize) {
        Assertions.assertEquals(expectedSize, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}