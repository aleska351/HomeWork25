package com.goncharenko;

import java.util.concurrent.*;

public class ScheduleExecutorServiceDemo {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> world =
                scheduler.scheduleWithFixedDelay(new Runnable() {
                    public void run() {
                        printWorld();
                    };
                }, 0, 10, TimeUnit.SECONDS);
        ScheduledFuture<?> hello =
                scheduler.scheduleWithFixedDelay(new Runnable() {
                    public void run() {
                        printHello();
                    }
                }, 0, 10, TimeUnit.SECONDS);
//
        scheduler.schedule(() -> {
            hello.cancel(true);
        }, 60, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            world.cancel(true);
        }, 60, TimeUnit.SECONDS);
//
        try {
            scheduler.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        scheduler.shutdown();
    }

    private static void printHello() {
        System.out.print("Hello ");
    }
    private static void printWorld() {
        System.out.print("World ");
    }
}