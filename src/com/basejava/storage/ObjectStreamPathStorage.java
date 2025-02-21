package com.basejava.storage;

import com.basejava.storage.strategy.ObjectStreamStrategy;

import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(Path directory) {
        super(directory, new ObjectStreamStrategy());
    }
}