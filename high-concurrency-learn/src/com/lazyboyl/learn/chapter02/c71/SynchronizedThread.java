package com.lazyboyl.learn.chapter02.c71;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述：
 */
public class SynchronizedThread {

    volatile static int i = 0;
    static Object object = new Object();

    public static class SynchronizedSafeThread implements Runnable {
        @Override
        public void run() {
            // synchronized锁的升级方式先是偏向锁【首先认为这个对象只会被他自己调用，在这个对象上做一个标识】
            // 接着是自旋锁【另外一个线程也来获取这个锁，这时候发现已经有线程在使用这个对象，就在哪里自旋10次，如果还拿不到锁，不会阻塞其他线程】
            // 最后升级为重量级锁【这时候就会阻塞其他线程】
            synchronized (object) {
                for (int k = 0; k < 1000000; k++) {
                    i++;
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SynchronizedSafeThread());
        Thread t2 = new Thread(new SynchronizedSafeThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终的值为：" + i);
    }

}
