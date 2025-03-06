package com.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;

import static com.basejava.util.CollectionUtils.writeWithException;

public class FileTreePrinter {
    public static void main(String[] args) throws IOException {
        File rootDir = new File("D:\\git\\BaseJava\\src");
        if (rootDir.exists() && rootDir.isDirectory()) {
            printDirectory(rootDir, 0);
        } else {
            System.out.println("The folder is not found!");
        }
    }

    private static void printDirectory(File dir, int level) throws IOException {
        String indent = String.join("", Collections.nCopies(level * 2, " "));
        System.out.println(indent + "📁" + dir.getName() + "/");
        File[] files = dir.listFiles();
        if (files != null) {
            writeWithException(Stream.of(files), file -> {
                if (file.isDirectory()) {
                    printDirectory(file, level + 1);
                } else {
                    System.out.println(indent + "  ©️ " + file.getName());
                }
            });
        }
    }
}