package com.basejava;

import java.io.IOException;

public class MainString {
    public static void main(String[] args) throws IOException {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder sb = new StringBuilder();

        for (String str : strArray) {
            System.out.println(str);
        }

        System.out.println(sb);
        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1.equals(str2));
    }
}