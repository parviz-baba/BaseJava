package com.basejava.storage;

import com.basejava.storage.strategy.ObjectStreamSerializer;

import java.nio.file.Paths;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new ObjectStreamSerializer()));
    }
}