package com.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            throw new IllegalStateException();
        });

        thread0.start();

        new Thread(() ->
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())
        ).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Final Counter: " + mainConcurrency.counter);
        createDeadlock();
    }

    private synchronized void increment() {
        counter++;
    }

    private static void createDeadlock() {
        final String lock1 = "lock1";
        final String lock2 = "lock2";

        Thread thread1 = new Thread(() -> lockResources(lock1, lock2));
        Thread thread2 = new Thread(() -> lockResources(lock2, lock1));

        thread1.start();
        thread2.start();
    }

    private static void lockResources(String firstLock, String secondLock) {
        System.out.println(Thread.currentThread().getName() + " Waiting for " + firstLock);
        synchronized (firstLock) {
            System.out.println(Thread.currentThread().getName() + " Holding " + firstLock);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " Waiting for " + secondLock);
            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName() + " Holding " + secondLock);
            }
        }
    }
}