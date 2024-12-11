package com.basejava.lesson_1.storage;

import com.basejava.lesson_1.model.Resume;

public interface Storage {
    int size();
    void save(Resume r);
    void delete(String uuid);
    Resume get(String uuid);
    Resume[] getAll();
    void clear();
}