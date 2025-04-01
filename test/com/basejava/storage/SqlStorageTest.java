package com.basejava.storage;

import com.basejava.config.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super((SqlStorage) Config.getInstance().getStorage());
    }
}
