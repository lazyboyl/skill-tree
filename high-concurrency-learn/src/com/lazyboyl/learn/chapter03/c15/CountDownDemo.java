package com.lazyboyl.learn.chapter03.c15;

import java.util.concurrent.CountDownLatch;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： CountDownLatch的使用模拟场景，比如我们有一个计算是依赖于另外两个子线程的结果，而另外两个线程需要去网络上去调用，因此我们这时候就可以使用
 * CountDownLatch来实现了。
 */
public class CountDownDemo {

    static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("线程完成操作");
                // 这中间可能有许多各种各样的操作
                // 表示当前线程已经执行完成了标记一次
                countDownLatch.countDown();
            }).start();
        }
        // 在此处等待上面的10个线程都跑好了再往下执行
        countDownLatch.await();
        System.out.println("我最后被执行了！");
    }

}
