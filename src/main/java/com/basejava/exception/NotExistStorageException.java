package com.basejava.exception;

public class NotExistStorageException extends com.basejava.exception.StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
