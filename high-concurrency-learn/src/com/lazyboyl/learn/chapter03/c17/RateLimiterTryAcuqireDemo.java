package com.lazyboyl.learn.chapter03.c17;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class RateLimiterTryAcuqireDemo {

    /**
     * 限速器每秒只允许两个线程执行
     */
    static RateLimiter rateLimiter = RateLimiter.create(2);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            // 模拟每秒进来十个求情
            Thread.sleep(100);
            // tryAcquire会去尝试请求，如果有令牌就执行，没有拿到令牌就直接丢弃当前请求
            if(rateLimiter.tryAcquire()){
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + "-----");
                },"请求" + i).start();
            }
        }
    }

}
