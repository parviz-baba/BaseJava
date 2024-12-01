package com.basejava.lesson_1;

public class Resume {
    private String uuid;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Resume{" +
               "uuid='" + uuid + '\'' +
               '}';
    }
}