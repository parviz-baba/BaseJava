package com.basejava.lesson_1;

import java.util.Arrays;

public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
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
            for (int j = index; j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
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
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
                return;
            }
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