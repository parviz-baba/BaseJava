package com.basejava.storage;

import com.basejava.storage.strategy.ObjectStreamStrategy;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    FileStorageTest() {
        super(new FileStorage(new File("D:/projects/storage"), new ObjectStreamStrategy()));
    }
}