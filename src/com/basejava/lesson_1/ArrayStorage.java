package com.basejava.lesson_1;

import java.util.Arrays;

public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Переполнение хранилища. Невозможно сохранить резюме: " + r.getUuid());
            return;
        }
        if (findIndex(r.getUuid()) != -1) {
            System.out.println("Резюме уже существует: " + r.getUuid());
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Резюме не найдено: " + uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("Резюме не найдено: " + uuid);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
            if (index != -1) {
                storage[index] = resume;
                return;
            }
        System.out.println("Резюме не найдено: " + resume.getUuid());
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}