package code.chat.utils;

import lombok.SneakyThrows;


public class Threads {

   private volatile int count1 = 0;
   private volatile int count2 = 0;
   private final Lock lock = new Lock();

   void increment() {
       try {
           lock.lock();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       count1++;
       lock.unlock();
   }
   void increment1() {
       synchronized (this) {
           count2++;
       }
       lock.unlock();
   }

    public void doContainer() {
        Thread tread = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                for(int t = 0 ; t < 1_000_000; t++){
                    increment();
                    System.out.println("first sleep");

                }
                System.out.println(count1 + "threed 1");
                System.out.println(count2 + "threed 2");
            }
        });
        Thread tread2 = new Thread(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                for(int t = 0 ; t < 1_000_000; t++) {
                    increment();
                }
                System.out.println(count1 + " threed 2");
                System.out.println(count2 + " threed 2");
            }
        });
        tread.start();
        tread2.start();
    }
}

class Lock {
    private boolean isLocked = false;

    void lock() throws InterruptedException {
        synchronized (this) {
            while(isLocked) {
                wait();
            }
            isLocked = true;
        }
    }

    void unlock() {
        synchronized (this) {
            isLocked = false;
            notify();
        }
    }
}

class Lock2 {
    private boolean isLocked = false;

    void lock() throws InterruptedException {
        synchronized (this) {
            while(isLocked) {
                wait();
            }
            isLocked = true;
        }
    }

    void unlock() {
        synchronized (this) {
            isLocked = false;
            notify();
        }
    }
}
