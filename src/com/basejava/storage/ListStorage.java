package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public int doSize() {
        return storage.size();
    }

    @Override
    protected void doSave(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
        if (storage.size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        storage.add(r);
    }

    @Override
    protected void doDelete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        storage.remove((int) searchKey);
    }

    @Override
    protected Resume doGet(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get((int) searchKey);
    }

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r) {
        // getSearchKey funksionallığı
        Object searchKey = getSearchKey(r.getUuid());
        if (!isExist(searchKey)) { // Mövcudluq yoxlanılır
            throw new NotExistStorageException(r.getUuid());
        }
        storage.set((int) searchKey, r); // Resume yenilənir
    }

    private Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    private boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}