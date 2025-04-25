package com.basejava;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTask_Lesson12 {

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream()
                .filter(n -> (sum % 2 == 0) == (n % 2 != 0))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        System.out.println("minValue: " + minValue(values));

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("oddOrEven: " + oddOrEven(list1));

        List<Integer> list2 = Arrays.asList(2, 4, 6, 8);
        System.out.println("oddOrEven: " + oddOrEven(list2));
    }
}