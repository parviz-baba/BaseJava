package com.basejava.storage;

import com.basejava.storage.strategy.ObjectStreamStrategy;

import java.io.File;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    public ObjectStreamFileStorage(File directory) {
        super(directory, new ObjectStreamStrategy());
    }
}