package com.basejava.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties props = new Properties();

    private Config() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                throw new IOException("db.properties file not found");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database config", e);
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}