package com.lazyboyl.learn.chapter03.c11;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 重入锁提供了中断的能力
 */
public class InterruptLock {

    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();

    /**
     * 启动了t1和t2两个线程，在两个线程中分别给lock1和lock2都上锁这样就造成了死锁的出现，接着我们用interrupt打断t1的线程，
     * 这时候t2拿到了锁，就可以正常执行我们的业务会打印【t2做了一些操作！】，而t1只是单纯的退出线程。
     * 这则说明了使用interrupt我们可以打断死锁的情况，而传统的synchronized是无法实现这种需求场景的
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                lock1.lockInterruptibly();
                Thread.sleep(100);
                lock2.lockInterruptibly();
                System.out.println("t1做了一些操作！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                // 判断当前的lock1是否已经lock了
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
            }
            System.out.println("t1退出了程序！");
        });
        Thread t2 = new Thread(()->{
            try {
                lock2.lockInterruptibly();
                Thread.sleep(100);
                lock1.lockInterruptibly();
                System.out.println("t2做了一些操作！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
            }
            System.out.println("t2退出了程序！");
        });
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t1.interrupt();
    }

}
