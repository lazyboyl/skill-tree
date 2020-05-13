package com.lazyboyl.learn.chapter03.c24;

import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述： 模拟各种优先级的用户抢商品的过程
 */
public class PriorityThreadDemo {

    public static class PriorityTask implements Runnable, Comparable<PriorityTask> {

        private Integer priority;

        public Integer getPriority() {
            return priority;
        }


        public PriorityTask(Integer priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            System.out.println("当前执行的任务的优先级是" + this.getPriority());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int compareTo(PriorityTask o) {
            if (this.getPriority() < o.priority) {
                return 1;
            } else if (this.getPriority() > o.priority) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * 通过下面的例子，我们可以很快的发现我们的线程在执行的时候首先执行的是高优先级的线程，低优先级的先靠边站，等到高优先级的执行完成以后才去执行低优先级的,这样就满足了抢某些商品的时候VIP用户更容易抢到的规则了。
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                Executors.defaultThreadFactory(), (runnable, executor) -> {
            System.out.println(runnable.toString() + "---" + executor.getActiveCount());
        });
        PriorityTask high = new PriorityTask(10);
        PriorityTask low = new PriorityTask(5);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i < 200) {
                es.execute(high);
            }
            es.execute(low);
            Thread.sleep(10);
        }
    }

}
