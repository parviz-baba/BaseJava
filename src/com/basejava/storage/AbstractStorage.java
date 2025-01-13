package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public int size() {
        return doSize();
    }

    @Override
    public void save(Resume r) {
        doSave(r);
    }

    @Override
    public void delete(String uuid) {
        doDelete(uuid);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(uuid);
    }

    @Override
    public Resume[] getAll() {
        return doGetAll();
    }

    @Override
    public void clear() {
        doClear();
    }

    @Override
    public void update(Resume r) {
        doUpdate(r);
    }

    protected abstract int doSize();

    protected abstract void doSave(Resume r);

    protected abstract void doDelete(String uuid);

    protected abstract Resume doGet(String uuid);

    protected abstract Resume[] doGetAll();

    protected abstract void doClear();

    protected abstract void doUpdate(Resume r);
}




