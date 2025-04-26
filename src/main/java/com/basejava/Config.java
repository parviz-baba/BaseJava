package com.basejava;

import com.basejava.storage.SqlStorage;
import com.basejava.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getHomeDir(), "resumes.properties");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("resumes.properties")) {
            if (is == null) {
                throw new IllegalStateException("resumes.properties not found in classpath");
            }
            Properties props = new Properties();
            props.load(is);

            storageDir = new File(props.getProperty("storage.dir"));
            if (!storageDir.isDirectory()) {
                throw new IllegalStateException(storageDir + " is not a valid directory");
            }

            storage = new SqlStorage(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (IOException e) {
            throw new IllegalStateException("Could not load resumes.properties from classpath", e);
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
}