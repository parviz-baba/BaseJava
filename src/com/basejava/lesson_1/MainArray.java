package com.basejava.lesson_1;

public class MainArray {
    public static void main(String[] args) {
        ArrayStorage storage = new ArrayStorage();
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
        printAll(storage);
        System.out.println("Получение резюме с uuid1: " + storage.get("uuid1"));
        storage.delete("uuid2");
        System.out.println("После удаления uuid2:");
        printAll(storage);
        storage.clear();
        System.out.println("После очистки хранилища:");
        printAll(storage);
    }

    private static void printAll(ArrayStorage storage) {
        Resume[] all = storage.getAll();
        for (Resume r : all) {
            System.out.println(r);
        }
    }
}