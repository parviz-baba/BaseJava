package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    private static final Resume RESUME_1 = new Resume("uuid1", "Name Surname 1");
    private static final Resume RESUME_2 = new Resume("uuid2", "Name Surname 2");
    private static final Resume RESUME_3 = new Resume("uuid3", "Name Surname 3");
    private static final Resume RESUME_4 = new Resume("uuid4", "Name Surname 4");
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
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
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assertions.assertEquals(4, storage.size());
        Assertions.assertEquals(RESUME_4, storage.get("uuid4"));
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void saveOverflow() {
        if (storage instanceof AbstractArrayStorage) {
            try {
                for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume("uuid" + i));
                }
            } catch (StorageException e) {
                Assertions.fail("Overflow happened too early");
            }
            assertThrows(StorageException.class, () -> storage.save(new Resume("overflow")));
        } else {
            System.out.println("saveOverflow test skipped for non-array storage");
        }
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    public void get() {
        Assertions.assertEquals(RESUME_1, storage.get("uuid1"));
        Assertions.assertEquals(RESUME_2, storage.get("uuid2"));
        Assertions.assertEquals(RESUME_3, storage.get("uuid3"));
    }

    @Test
    public void getAll() {
        List<Resume> resumes = storage.getAllSorted();
        Assertions.assertEquals(3, resumes.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume updatedResume = new Resume("uuid1", "Updated Name");
        storage.update(updatedResume);
        Assertions.assertSame(updatedResume, storage.get("uuid1"));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}