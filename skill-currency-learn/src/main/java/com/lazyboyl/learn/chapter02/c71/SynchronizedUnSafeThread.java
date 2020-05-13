package com.lazyboyl.learn.chapter02.c71;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述：
 */
public class SynchronizedUnSafeThread implements Runnable {

    volatile static int i = 0;

    @Override
    public void run() {
        for (int k = 0; k < 1000000; k++) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SynchronizedUnSafeThread());
        Thread t2 = new Thread(new SynchronizedUnSafeThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终的值为：" + i);
    }
}
