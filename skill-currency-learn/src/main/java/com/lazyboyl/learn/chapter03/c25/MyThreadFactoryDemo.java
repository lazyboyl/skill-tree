package com.lazyboyl.learn.chapter03.c25;

import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述： 重写生成线程的工程方法，里面可以做自己独特的设置
 */
public class MyThreadFactoryDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我休眠了一毫秒再执行任务！" + Thread.currentThread().getName());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        t.setName("create" + r);
                        return t;
                    }
                }, (runnable, executor) -> {
            System.out.println("---" + runnable.toString());
        });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.execute(myTask);
            Thread.sleep(10);
        }
    }

}
