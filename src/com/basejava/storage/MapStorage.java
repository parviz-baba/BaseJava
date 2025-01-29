package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> uuidStorage = new HashMap<>();
    private final EmailStorage emailStorage = new EmailStorage();

    @Override
    public int size() {
        return uuidStorage.size();
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        uuidStorage.put(r.getUuid(), r);
        emailStorage.addEmail(r.getEmail(), r.getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        String uuid = (String) searchKey;
        Resume resume = uuidStorage.remove(uuid);
        if (resume != null) {
            emailStorage.removeEmail(resume.getEmail());
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return uuidStorage.get((String) searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        return uuidStorage.values().stream()
                .sorted(Comparator.comparing(Resume::getFullName)
                        .thenComparing(Resume::getUuid))
                .toList();
    }

    @Override
    public void clear() {
        uuidStorage.clear();
        emailStorage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        String uuid = (String) searchKey;
        if (uuidStorage.containsKey(uuid)) {
            Resume oldResume = uuidStorage.get(uuid);
            emailStorage.removeEmail(oldResume.getEmail());
            uuidStorage.put(uuid, r);
            emailStorage.addEmail(r.getEmail(), uuid);
        }
    }

    @Override
    protected Object getSearchKey(String identifier) {
        if (uuidStorage.containsKey(identifier)) {
            return identifier;
        }
        String uuid = emailStorage.getUuidByEmail(identifier);
        return uuid != null ? uuid : null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return uuidStorage.containsKey(searchKey);
    }

    public static class EmailStorage {
        private final Map<String, String> emailToUuidMap = new HashMap<>();

        public void addEmail(String email, String uuid) {
            emailToUuidMap.put(email, uuid);
        }

        public String getUuidByEmail(String email) {
            return emailToUuidMap.get(email);
        }

        public void removeEmail(String email) {
            emailToUuidMap.remove(email);
        }

        public void clear() {
            emailToUuidMap.clear();
        }
    }
}