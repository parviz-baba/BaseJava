package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static com.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> listStorage = new ArrayList<>();

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (listStorage.size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        listStorage.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(listStorage);
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        listStorage.set(searchKey, r);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return listStorage.stream()
                .filter(r -> r.getUuid().equals(uuid))
                .findFirst()
                .map(listStorage::indexOf)
                .orElse(-1);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}