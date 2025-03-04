package com.basejava.storage;

import com.basejava.storage.strategy.JsonStreamSerializer;

import java.nio.file.Paths;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new JsonStreamSerializer()));
    }
}