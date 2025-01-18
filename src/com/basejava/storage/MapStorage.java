package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

import static com.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> mapStrorage = new HashMap<>();

    @Override
    public int size() {
        return mapStrorage.size();
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (mapStrorage.size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        mapStrorage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStrorage.remove((String) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStrorage.get((String) searchKey);
    }

    @Override
    public Resume[] getAll() {
        return mapStrorage.values().stream().sorted().toArray(Resume[]::new);
    }

    @Override
    public void clear() {
        mapStrorage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapStrorage.put((String) searchKey, r);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapStrorage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStrorage.containsKey((String) searchKey);
    }
}
