package com.basejava.storage;

import com.basejava.exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;

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

    @Before
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

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i, "Name " + i));
        }
        storage.save(new Resume("overflow", "Overflow Name"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        storage.get(RESUME_1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> resumes = storage.getAllSorted();
        Assert.assertEquals(3, resumes.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Assert.assertTrue(storage.getAllSorted().isEmpty());
    }

    @Test
    public void update() throws Exception {
        Resume updatedResume = new Resume("uuid1", "Updated Name");
        storage.update(updatedResume);
        Assert.assertSame(updatedResume, storage.get("uuid1"));
    }

    private void assertSize(int expectedSize) {
        Assert.assertEquals(expectedSize, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}