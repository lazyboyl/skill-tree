package com.lazyboyl.learn.chapter03.c22;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class ThreadPoolFixedRateDemo {

    public static void main(String[] args) {
        // 返回一个ScheduledExecutorService的对象
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        // 定时任务每过两秒执行一次，执行时间为一秒
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "--" + (System.currentTimeMillis() / 1000));
//        }, 0, 2, TimeUnit.SECONDS);

        // 定时任务每过两秒执行一次，执行时间为五秒，这样会发现调用的间隔变为5秒
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--" + (System.currentTimeMillis() / 1000));
        }, 0, 2, TimeUnit.SECONDS);
    }


}
