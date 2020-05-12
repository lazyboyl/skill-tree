package com.lazyboyl.learn.chapter03.c22;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述：
 */
public class ThreadPoolFixedDelayDemo {

    public static void main(String[] args) {
        // 返回一个ScheduledExecutorService的对象
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        // 延迟多少秒以后定时执行，相比于scheduleAtFixedRate的差别是，他需要在执行完成runnable里面的代码以后，然后再延迟2秒再执行，等于此处两次定时任务的执行间隔是7秒
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--" + (System.currentTimeMillis() / 1000));
        }, 0, 2, TimeUnit.SECONDS);
    }


}
