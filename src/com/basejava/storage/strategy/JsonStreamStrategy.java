package com.basejava.storage.strategy;

import com.basejava.model.Resume;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JsonStreamStrategy implements SerializationStrategy {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void write(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os)) {
            GSON.toJson(r, writer);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return GSON.fromJson(reader, Resume.class);
        }
    }
}