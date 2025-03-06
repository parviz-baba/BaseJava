package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.strategy.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.basejava.util.CollectionUtils.writeWithException;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer strategy;

    protected PathStorage(Path directory, StreamSerializer strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            writeWithException(Files.list(directory), this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(path))) {
            strategy.write(r, os);
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
            return strategy.read(is);
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }
}