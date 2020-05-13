package com.lazyboyl.learn.chapter03.c11;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： tryLock的使用
 */
public class TimeLock {

    static ReentrantLock lock1 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                // 5秒尝试获取锁
                if (lock1.tryLock(5, TimeUnit.SECONDS)) {
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
                Long start = System.currentTimeMillis();
                // 5秒尝试获取锁
                if (lock1.tryLock(5, TimeUnit.SECONDS)) {
                    System.out.println("T2我尝试获取锁，获取锁成功！");
                } else {
                    System.out.println("T2我尝试获取锁，获取锁失败！");
                }
                Long end = System.currentTimeMillis();
                // 此处会打印我们的耗时，耗时时间为： 若t1的阻塞时间小于t2的尝试获取的时间则此处为： t1的阻塞时间 - 100毫秒 反之则为 t2的尝试获取时间
                System.out.println("操作耗时：" + (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
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
