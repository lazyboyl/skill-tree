package com.lazyboyl.learn.chapter03.c14;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： 读写锁的互斥场景
 * 读读并行、读写互斥、写写互斥
 */
public class ReadAndWriteLockDemo {

    /**
     * 实例化读写锁对象
     */
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    /**
     * 获取读锁
     */
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    /**
     * 获取写锁
     */
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static int value = 10;

    public static void main(String[] args) {


        for (int i = 18; i < 20; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        writeLock.lock();
                        value = new Random().nextInt(100);
                    } finally {
                        // 此处休眠一秒用户验证我们的读和写是互斥的,通过我们的Thread.currentThread().getName()我们可以看到我们的两个线程是交替的去写数据，因此也验证了写写锁的互斥
                        System.out.println(Thread.currentThread().getName() + "我生成好随机的值了，但是我需要休眠1秒再释放锁！");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("我已经释放了，你现在可以开始读取数据了！");
                        writeLock.unlock();
                    }
                    // 此处需要休眠一秒，让读线程来读取数据，如果不设置一秒，将会导致我们的读线程读取的数据是不一致的，不利于验证我们想要的结果
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 18; i++) {
            new Thread(() -> {
                // 由于读和写之间是互斥的因此需要使用lock来实现他们的互斥关系
                while (true) {
                    try {
                        try {
                            readLock.lock();
                            System.out.println(Thread.currentThread().getName() + "读取到的值是：" + value);
                        } finally {
                            readLock.unlock();
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
