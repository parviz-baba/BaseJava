package com.basejava.exception;

public class ExistStorageException extends com.basejava.exception.StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}