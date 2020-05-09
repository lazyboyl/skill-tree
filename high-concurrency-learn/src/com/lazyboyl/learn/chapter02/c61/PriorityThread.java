package com.lazyboyl.learn.chapter02.c61;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 线程的优先级分为1到10级，级别越高则优先拿到线程
 */
public class PriorityThread {

    public static void main(String[] args) {
        Thread t2 = new Thread(new LowerPriority());
        t2.setPriority(Thread.MIN_PRIORITY);
        Thread t1 = new Thread(new HighPriority());
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.start();
        t1.start();
    }

    public static class LowerPriority implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (i < 100000) {
                i++;
                if (i >= 100000) {
                    System.out.println("低优先级的任务完成了！");
                    break;
                }
            }
        }
    }

    public static class HighPriority implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (i < 100000) {
                i++;
                if (i >= 100000) {
                    System.out.println("高优先级的任务完成了！");
                    break;
                }
            }
        }
    }

}
