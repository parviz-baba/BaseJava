package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
//    @Override
//    protected void addElement(Resume r, int index) {
//        System.arraycopy(storage, index, storage, index + 1, size - index);
//        storage[index] = r;
//    }

    @Override
    protected void addElement(Resume r, int index) {
        int insertionPoint = -index - 1; // Binary search mənfi dəyər qaytarsa, düzəldilir
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = r;
    }

    @Override
    protected void removeElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}