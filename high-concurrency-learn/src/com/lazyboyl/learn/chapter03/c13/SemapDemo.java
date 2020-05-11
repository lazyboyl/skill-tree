package com.lazyboyl.learn.chapter03.c13;

import java.util.concurrent.Semaphore;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： 信号量就是一次允许多少个线程来进行执行
 */
public class SemapDemo {

    static Semaphore s = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                try {
                    s.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程名称为：" + Thread.currentThread().getName());
                // 此处加了一个睡眠2秒，这样就可以在控制套看到我们的输出是每过2秒才输出一次，这样就验证了我们的信号量的功能了
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                s.release();
            }).start();
        }
    }
}
