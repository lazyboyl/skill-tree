package com.lazyboyl.learn.chapter02.c41;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 引入了现场组的概念来管理线程
 */
public class ThreadGroupName implements Runnable {

    public static void main(String [] args){
        ThreadGroup tg = new ThreadGroup("printThread");
        Thread t1 = new Thread(tg, new ThreadGroupName(),"t1");
        Thread t2 = new Thread(tg, new ThreadGroupName(),"t2");
        t1.start();
        t2.start();
        System.out.println("活动线程数为："+tg.activeCount() );
        // 线程组中的线程信息
        tg.list();
    }

    @Override
    public void run() {
        while (true){
            System.out.println("当前线程的名称为：" + Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
