package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.List;

public interface Storage {
    int size();
    void save(Resume r);
    void delete(String uuid);
    Resume get(String uuid);
    List<Resume> getAllSorted();
    void clear();
    void update(Resume r);
}