package com.basejava.util;

import java.io.IOException;
import java.util.stream.Stream;

public class CollectionUtils {
    public static <T> void writeWithException(Stream<T> stream, ThrowingConsumer<T> action) throws IOException {
        for (T item : (Iterable<T>) stream::iterator) {
            action.accept(item);
        }
    }
}