package com.basejava.storage;

import com.basejava.model.Resume;

import java.io.IOException;
import java.util.List;

public interface Storage {
    int size();
    void save(Resume r);
    void delete(String uuid);
    Resume get(String uuid);
    List<Resume> getAllSorted() throws IOException;
    void clear() throws IOException;
    void update(Resume r);
}