package com.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.basejava.util.CollectionUtils.writeWithException;

public class DirectoryPrinter {
    public static void main(String[] args) throws IOException {
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
            writeWithException(Arrays.stream(list), System.out::println);
        }
        System.out.println();
        printDirectory(dir, 0);
    }

    private static void printDirectory(File dir, int level) throws IOException {
        if (!dir.exists()) {
            System.err.println("Directory does not exist: " + dir.getAbsolutePath());
            return;
        }
        String indent = String.join("", Collections.nCopies(level * 2, " "));
        System.out.println(indent + "📁 " + dir.getName() + " (" + dir.getAbsolutePath() + ")");
        File[] files = dir.listFiles();
        if (files != null) {
            writeWithException(Stream.of(files), file -> {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    System.out.println(indent + "  ©️ " + file.getName());
                }
            });
        } else {
            System.err.println("Error reading contents of directory: " + dir.getAbsolutePath());
        }
    }
}