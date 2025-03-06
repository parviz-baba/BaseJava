package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

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
    protected Resume[] doGetAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return java.util.stream.IntStream.range(0, size)
                .filter(i -> uuid.equals(storage[i].getUuid()))
                .findFirst()
                .orElse(-1);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}