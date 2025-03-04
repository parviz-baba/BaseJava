package com.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectory(dir, 0);
    }

    // TODO: make pretty output
    private static void printDirectory(File dir, int level) {
        String indent = String.join("", Collections.nCopies(level * 2, " "));
        System.out.println(indent + "📁" + dir.getName() + "/");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    System.out.println(indent + "  " + "©️" + file.getName());
                }
            }
        }
    }
}