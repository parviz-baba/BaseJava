package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected int doSize() {
        return size;
    }

    public final void doSave(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", null);
        } else {
            int index = getIndex(r.getUuid());
            if (index >= 0) {
                throw new ExistStorageException(r.getUuid());
            } else {
                saveAtIndex(r, -(index + 1));
                size++;
            }
        }
    }

    public final void doDelete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteAtIndex(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume doGet(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected Resume[] doGetAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void doUpdate(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    protected abstract void saveAtIndex(Resume r, int index);

    protected abstract void deleteAtIndex(int index);

    protected abstract int getIndex(String uuid);
}