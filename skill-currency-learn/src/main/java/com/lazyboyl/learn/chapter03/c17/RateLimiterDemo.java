package com.lazyboyl.learn.chapter03.c17;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class RateLimiterDemo {

    /**
     * 限速器每秒只允许两个线程执行
     */
    static RateLimiter rateLimiter = RateLimiter.create(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            // 模拟每秒进来50个请求
            // acquire会是的进入的的请求等待，直到有一个令牌获取到请求位置
            rateLimiter.acquire();
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "-----");
            },"请求" + i).start();
        }
    }

}
