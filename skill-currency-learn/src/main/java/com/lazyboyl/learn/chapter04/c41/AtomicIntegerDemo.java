package com.lazyboyl.learn.chapter04.c41;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linzf
 * @since 2020/5/19
 * 类描述：
 */
public class AtomicIntegerDemo {

    public static AtomicInteger ai = new AtomicInteger();

    public static class AddAtomicInteger implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                ai.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i <10; i++) {
            es.submit(new AddAtomicInteger());
        }
        Thread.sleep(1000);
        System.out.println("当前的值为：" + ai.get());
    }

}
