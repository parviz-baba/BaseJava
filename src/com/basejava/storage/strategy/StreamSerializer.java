package com.basejava.storage.strategy;

import com.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void write(Resume r, OutputStream os) throws IOException;
    Resume read(InputStream is) throws IOException;
}