package com.lazyboyl.learn.chapter03.c24;

import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述：
 */
public class RejectThreadDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我休眠了一毫秒再执行任务！");
        }

    }


    /**
     * ThreadPoolExecutor的各个参数的意义
     * corePoolSize： 初始化的线程数
     * maximumPoolSize： 最大线程数
     * keepAliveTime： 当前线程超过corePoolSize多余线程的存活时间
     * unit： 存活时间的单位秒或者毫秒或者天等
     * workQueue： 任务队列，当前被提交但未执行的任务
     * threadFactory： 线程工厂，直接使用默认的线程工厂即可
     * handler： 任务被拒绝以后的操作
     * <p>
     * workQueue主要有以下的几种实现方式分别是【SynchronousQueue和ArrayBlockingQueue在线程数不够的时候都会去尝试创建新的线程】：
     * SynchronousQueue：直接提交的队列，一有任务就立马提交给线程执行，因此若是最大线程设置的不够大，则任务被抛弃的概率就超级大。
     * ArrayBlockingQueue：有界的任务队列，当任务数小于有界队列的长度的时候不会先去创建线程，假定你现在设置有界队列为10，然后每秒的线程是9，每秒可以执行完成的线程也是9，
     * 那这时候我们的线程池只需要最小的线程数就可以执行完成了，而无需再创建线程。
     * LinkedBlockingQueue： 无界任务队列,在设置无界队列的时候new LinkedBlockingQueue的时候不要设置大小，否则就会变成一个有界的任务队列了，使用无界任务队列将会不断的扩展任务队列
     * 直到耗尽系统的性能为止。
     * PriorityBlockingQueue：优先任务队列是带有优先级的执行的任务队列，他是一个无界的任务队列，他会根据任务的优先级来执行任务。使用场景，抢购的时候，我们希望高VIP的用户可以更容易的抢到商品，而
     * 低VIP的用户更不容易抢到商品。
     */
    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                Executors.defaultThreadFactory(), (runnable, executor) -> {
            System.out.println(runnable.toString() + "---" + executor.getActiveCount());
        });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.execute(myTask);
            Thread.sleep(10);
        }
    }

}
