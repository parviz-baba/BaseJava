package com.basejava;

import com.basejava.storage.SqlStorage;
import com.basejava.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();

    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("resumes.properties")) {
            if (is == null) {
                throw new IllegalStateException("resumes.properties faylı tapılmadı");
            }
            Properties props = new Properties();
            props.load(is);

            storage = new SqlStorage(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );

        } catch (IOException e) {
            throw new IllegalStateException("resumes.properties yüklənə bilmədi", e);
        }
    }

    public Storage getStorage() {
        return storage;
    }
}
