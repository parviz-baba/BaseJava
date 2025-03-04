package com.basejava.storage;

import com.basejava.storage.strategy.DataStreamSerializer;

import java.nio.file.Paths;

public class DataStreamSerializerTest extends AbstractStorageTest {
    public DataStreamSerializerTest() {
        super(new PathStorage(Paths.get("storage_dir"), new DataStreamSerializer()));
    }
}