package com.basejava.storage.strategy;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.io.*;

public class ObjectStreamStrategy implements SerializationStrategy {
    @Override
    public void write(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error reading resume", null, e);
        }
    }
}
