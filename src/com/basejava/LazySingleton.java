package com.basejava;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
//        if (INSTANCE == null) {
//            synchronized (com.basejava.LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new com.basejava.LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}