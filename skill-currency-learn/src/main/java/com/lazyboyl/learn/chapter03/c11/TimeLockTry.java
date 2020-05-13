package com.lazyboyl.learn.chapter03.c11;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： 通过try-lock可以解决两个锁同步竞争而导致死锁的问题
 */
public class TimeLockTry {

    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    if (lock1.tryLock()) {
                        if (lock2.tryLock()) {
                            System.out.println("t1做了一些操作！");
                            break;
                        }
                    }
                } finally {
                    // 判断当前的lock1是否已经lock了
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                while (true) {
                    if (lock2.tryLock()) {
                        if (lock1.tryLock()) {
                            System.out.println("t2做了一些操作！");
                            break;
                        }
                    }
                }
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t1.interrupt();
    }

}
