package com.basejava.storage;

import com.basejava.storage.strategy.XmlStreamSerializer;

import java.nio.file.Paths;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR.getAbsolutePath()), new XmlStreamSerializer()));
    }
}