package com.basejava.storage;

import com.basejava.storage.strategy.DataStreamSerializer;

import java.nio.file.Paths;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new DataStreamSerializer()));
    }
}