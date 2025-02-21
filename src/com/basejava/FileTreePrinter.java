package com.basejava;

import java.io.File;

public class FileTreePrinter {
    public static void main(String[] args) {
        File rootDir = new File("D:\\git\\BaseJava\\src");
        if (rootDir.exists() && rootDir.isDirectory()) {
            printDirectory(rootDir, 0);
        } else {
            System.out.println("The folder is not found!");
        }
    }

    private static void printDirectory(File dir, int level) {
        String indent = " ".repeat(level * 2);
        System.out.println(indent + "📁" + dir.getName() + "/");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    System.out.println(indent + "  " + "©️" +file.getName());
                }
            }
        }
    }
}