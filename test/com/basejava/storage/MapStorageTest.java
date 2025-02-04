package com.basejava.storage;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapUuidStorage());
    }
}