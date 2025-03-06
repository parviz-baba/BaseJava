package com.basejava;

import java.io.IOException;
import java.util.Arrays;

import static com.basejava.util.CollectionUtils.writeWithException;

public class MainString {
    public static void main(String[] args) throws IOException {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder sb = new StringBuilder();

        writeWithException(Arrays.stream(strArray), System.out::println);

        System.out.println(sb);
        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1.equals(str2));
    }
}