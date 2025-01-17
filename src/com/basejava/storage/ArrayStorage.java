package com.basejava.storage;

import com.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void addElement(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void removeElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}