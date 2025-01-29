package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            addElement(r, (Integer) index);
            size++;
        }
    }

    @Override
    protected void doDelete(Object index) {
        int idx = (int) index;
        removeElement(idx);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object index) {
        int idx = (int) index;
        return storage[idx];
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.stream(storage, 0, size)
                .sorted(Comparator.comparing(Resume::getFullName)
                        .thenComparing(Resume::getUuid)) // Əgər adlar eynidirsə, UUID ilə sıralayırıq
                .toList();
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        int idx = (int) index;
        storage[idx] = r;
    }

    protected abstract void removeElement(int index);

    protected abstract void addElement(Resume r, int index);
}