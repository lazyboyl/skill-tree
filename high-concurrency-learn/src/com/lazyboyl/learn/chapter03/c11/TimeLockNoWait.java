package com.lazyboyl.learn.chapter03.c11;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： tryLock的使用不阻塞等待的方式
 */
public class TimeLockNoWait {

    static ReentrantLock lock1 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                // 5秒尝试获取锁
                if (lock1.tryLock()) {
                    // 休眠6秒，导致t2线程获取不到锁
                    Thread.sleep(6000);
                    // 休眠2秒, t2线程在阻塞2秒以后获取到锁
                    //  Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                // 尝试，无法获取锁就立即失败
                if (lock1.tryLock()) {
                    System.out.println("T2我尝试获取锁，获取锁成功！");
                } else {
                    System.out.println("T2我尝试获取锁，获取锁失败！");
                }
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
            }
        });
        t1.start();
        // 这里必须要加上sleep，因为你不加sleep可能会t2先执行，而导致我们无法获取我们想要的结果
        Thread.sleep(100);
        t2.start();
    }


}
