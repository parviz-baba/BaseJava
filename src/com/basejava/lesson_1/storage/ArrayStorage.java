package com.basejava.lesson_1.storage;

import com.basejava.lesson_1.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected void doSave(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void doDelete(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}