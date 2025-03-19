package com.basejava.storage;

public class SqlStorageTest extends AbstractStorageTest {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/resumes";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Per3367176";

    public SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}