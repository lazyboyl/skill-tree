package com.lazyboyl.learn.chapter03.c21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class ThreadPoolCacheDemo {

    public static void main(String[] args) {
        // 创建一个可变长度的线程池
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "----" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
