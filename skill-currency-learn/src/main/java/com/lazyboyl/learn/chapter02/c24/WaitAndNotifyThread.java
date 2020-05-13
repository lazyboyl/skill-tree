package com.lazyboyl.learn.chapter02.c24;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 验证wait和notify
 */
public class WaitAndNotifyThread {

    public static Object object = new Object();

    public static void  main(String [] args){
        new Thread(new T1()).start();
        new Thread(new T2()).start();
    }

    public static class T1 implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                System.out.println("我是第一个执行的！");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    System.out.println("我被重新唤醒了！");
                    e.printStackTrace();
                }
                System.out.println("我是T1的唤醒以后执行了！");
            }
        }
    }


    public static class T2 implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                System.out.println("我是第二个执行的开始！");
                object.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是第二个执行的结束！");
            }
        }
    }

}
