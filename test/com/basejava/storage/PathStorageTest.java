package com.basejava.storage;

import com.basejava.storage.strategy.ObjectStreamStrategy;

import java.nio.file.Path;

class PathStorageTest extends AbstractStorageTest {
    PathStorageTest() {
        super(new PathStorage(Path.of("D:/projects/storage"), new ObjectStreamStrategy()));
    }
}