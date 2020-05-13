package com.lazyboyl.learn.chapter03.c26;

import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述：
 */
public class ExtThreadPoolDemo {

    public static class MyTask implements Runnable {

        private String name;

        public MyTask(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我休眠了一毫秒再执行任务！" + Thread.currentThread().getName());
        }

    }

    /**
     * ThreadPoolExecutor线程池我们可以通过扩展beforeExecute、afterExecute、terminated用于我们在执行线程中的一些操作，我们可以监控线程的整个的生命过程，我们可以方便的调试
     * 我们的多线程的程序。
     */
    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask("扩展线程");
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(), (runnable, executor) -> {
            System.out.println("---" + runnable.toString());
        }){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                if(r instanceof MyTask){
                    System.out.println("准备执行" + ((MyTask) r).getName());
                }
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if(r instanceof MyTask){
                    System.out.println("完成执行" + ((MyTask) r).getName());
                }
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                super.terminated();
            }
        };
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i>10){
                // 给线程池发送一个关闭的信号，等里面的线程全部执行完成以后再关闭线程
                es.shutdown();
                break;
            } else {
                es.execute(myTask);
                Thread.sleep(10);
            }
        }
    }

}
