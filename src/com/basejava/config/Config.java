package com.basejava.config;

import com.basejava.storage.SqlStorage;
import com.basejava.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties props = new Properties();
    private final Storage storage;

    private Config() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new IOException("db.properties file not found");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database config", e);
        }
        String dbUrl = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");
        storage = new SqlStorage(dbUrl, dbUser, dbPassword);
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    public Storage getStorage() {
        return storage;
    }
}