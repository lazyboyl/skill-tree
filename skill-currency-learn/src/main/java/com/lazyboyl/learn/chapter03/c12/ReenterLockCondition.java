package com.lazyboyl.learn.chapter03.c12;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class ReenterLockCondition {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            System.out.println("我在这里进行等待！");
            try {
                condition.await();
                System.out.println("我再这里被唤醒了！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        });
        t1.start();
        Thread.sleep(1000);
        // 这里必须要加上重新给lock加上锁，因为一旦下面线程被唤醒，那么它会尝试去获取当前线程的重入锁，因此此处如果没有加上此段的话会导致线程唤醒以后一直卡在哪里无法正常执行
        try{
            lock.tryLock();
            condition.signal();
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

}
