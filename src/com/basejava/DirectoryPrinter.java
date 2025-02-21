package com.basejava;

import java.io.File;
import java.io.IOException;

public class DirectoryPrinter {

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println("Canonical Path: " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file path", e);
        }

        File dir = new File("./src/com/basejava");
        System.out.println("Is directory: " + dir.isDirectory());

        String[] list = dir.list();
        if (list != null) {
            System.out.println("\nContents of directory:");
            for (String name : list) {
                System.out.println(name);
            }
        }
        System.out.println();
        printDirectory(dir, 0);
    }

    private static void printDirectory(File dir, int level) {
        if (!dir.exists()) {
            System.err.println("Directory does not exist: " + dir.getAbsolutePath());
            return;
        }

        String indent = " ".repeat(level * 2);
        System.out.println(indent + "📁 " + dir.getName() + " (" + dir.getAbsolutePath() + ")");

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    System.out.println(indent + "  ©️ " + file.getName());
                }
            }
        } else {
            System.err.println("Error reading contents of directory: " + dir.getAbsolutePath());
        }
    }
}