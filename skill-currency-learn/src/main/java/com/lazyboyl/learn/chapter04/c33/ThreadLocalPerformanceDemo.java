package com.lazyboyl.learn.chapter04.c33;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/19
 * 类描述：
 */
public class ThreadLocalPerformanceDemo {

    static ExecutorService es = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private static final int GEN_COUNT = 10000;
    public static Random random = new Random(123);

    /**
     * 定义一个由ThreadLocal封装的Random对象
     */
    public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long> {

        private int mode = 0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom() {
            if (mode == 0) {
                return random;
            } else if (mode == 1) {
                return tRnd.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b = System.currentTimeMillis();
            for (int i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.println("耗时为：" + (e - b));
            return e - b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] future = new Future[10];
        for (int i = 0; i < 10; i++) {
            future[i] = es.submit(new RndTask(0));
        }
        long totalTime = 0;
        for (int i = 0; i < 10; i++) {
            totalTime += future[i].get();
        }
        System.out.println("多个线程访问同一个random实例的耗时：" + totalTime);
        for (int i = 0; i < 10; i++) {
            future[i] = es.submit(new RndTask(1));
        }
        totalTime = 0;
        for (int i = 0; i < 10; i++) {
            totalTime += future[i].get();
        }
        System.out.println("多个线程访问ThreadLocal包装的实例的耗时：" + totalTime);
        es.shutdown();
    }


}
