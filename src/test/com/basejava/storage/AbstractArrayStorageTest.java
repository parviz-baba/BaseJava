package test.com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.storage.AbstractArrayStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;
import com.basejava.storage.Storage;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() throws Exception {
        Resume updatedResume = new Resume(UUID_1);
        storage.update(updatedResume);
        Assert.assertSame(updatedResume, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expectedResumes = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(expectedResumes, storage.getAll());
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() throws Exception {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(newResume, storage.get("uuid4"));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        int storageLimit;
        Field field = AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT");
        field.setAccessible(true);
        storageLimit = field.getInt(null);
        try {
            for (int i = 4; i <= storageLimit; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Overflow occurred too early!");
        }
        storage.save(new Resume("overflow"));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        try {
            storage.get(UUID_1);
            Assert.fail("Expected NotExistStorageException was not thrown");
        } catch (NotExistStorageException e) {
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {

    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}