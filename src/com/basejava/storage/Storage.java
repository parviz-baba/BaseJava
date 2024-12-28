package com.basejava.storage;

import com.basejava.model.Resume;

public interface Storage {
    int size();
    void save(Resume r);
    void delete(String uuid);
    Resume get(String uuid);
    Resume[] getAll();
    void clear();
    void update(Resume r);
}