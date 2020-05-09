package com.lazyboyl.learn.chapter03.c11;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 1.5版本之前重入锁用于替代synchronized关键字，到了现在的1.8的版本synchronized和重入锁的性能相差不大了
 * 重入锁可以手动的控制加锁和放锁的节点，因此灵活性上会比synchronized好，但是加了锁一定要记得释放锁
 */
public class ReeterLock {
    static ReentrantLock lock = new ReentrantLock();
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (int k = 0; k < 10000; k++) {
                    i++;
                }
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                for (int k = 0; k < 10000; k++) {
                    i++;
                }
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终的值为：" + i);
    }
}
