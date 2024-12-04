package com.basejava.lesson_1;

public class MainTestArrayStorage {
    public static void main(String[] args) {
        ArrayStorage storage = new ArrayStorage();
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        storage.save(r1);
        storage.save(r2);
        assert storage.size() == 2 : "Ошибка в методе save()";
        assert storage.get("uuid1").equals(r1) : "Ошибка в методе fetch()";
        Resume updatedResume = new Resume("uuid2");
        storage.update(updatedResume);
        assert storage.get("uuid2").equals(updatedResume) : "Ошибка в методе update()";
        storage.delete("uuid1");
        assert storage.size() == 1 : "Ошибка в методе delete()";
        storage.clear();
        assert storage.size() == 0 : "Ошибка в методе clear()";
        System.out.println("Все тесты пройдены.");
    }
}