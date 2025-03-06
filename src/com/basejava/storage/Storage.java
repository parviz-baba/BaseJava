package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.List;

public interface Storage {
    void clear();
    void update(Resume r);
    void save(Resume r);
    Resume get(String uuid);
    void delete(String uuid);
    List<Resume> getAllSorted();
    int size();
}