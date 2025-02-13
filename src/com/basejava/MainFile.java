package com.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "D:/git/BaseJava";
        File rootDir = new File(filePath);
        printFiles(rootDir, "");
    }

    public static void printFiles(File dir, String indent) {
        if (dir.isDirectory()) {
            System.out.println(indent + "[DIR] " + dir.getName());
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    printFiles(file, indent + "  ");
                }
            }
        } else {
            System.out.println(indent + "[FILE] " + dir.getName());
        }
    }
}