package com.goncharenko;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLockDemo {
    private static final Object lockOne = new Object();
    private static final Object lockTwo = new Object();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new SimpleTaskOne());
        executor.execute(new SimpleTaskTwo());
        executor.shutdown();
    }

    static class SimpleTaskOne implements Runnable {
        @Override
        public void run() {
            synchronized (lockOne) {
                System.out.println("#1 has acquired");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
                synchronized (lockTwo) {
                    System.out.println("#2 has acquired");

                }
            }
        }
    }

    static class SimpleTaskTwo implements Runnable {
        @Override
        public void run() {

            synchronized (lockTwo) {
                System.out.println("#2 has acquired");
                synchronized (lockOne) {
                    System.out.println("#1 has acquired");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}
