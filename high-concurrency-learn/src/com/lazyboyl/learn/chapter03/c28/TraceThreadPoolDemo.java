package com.lazyboyl.learn.chapter03.c28;

import com.lazyboyl.learn.chapter03.c24.RejectThreadDemo;

import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述：
 */
public class TraceThreadPoolDemo {

    public static class MyTask implements Runnable {

        private int a;

        private int b;

        public MyTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我休眠了一毫秒再执行任务！" + a / b);
        }

    }


    /**
     * 如果当前例子使用的是ThreadPoolExecutor那么出错的时候只能定位到32行的错误，无法定位到49行这个调用的位置，在本例当中大家可以很方便的看到错误的真正调用位置，但是你再开发中
     * 我们的业务代码肯定不只是这么一点点的，那到时候要找具体的调用位置可能会找死我们，因此这就是我们的TraceThreadPool的真正的用处。
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new TraceThreadPool(5, 5, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(), (runnable, executor) -> {
            System.out.println(runnable.toString() + "---" + executor.getActiveCount());
        });
        for (int i = 0; i < 100; i++) {
            es.execute(new MyTask(100, i));
            Thread.sleep(10);
        }
    }

}
