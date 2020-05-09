package com.lazyboyl.learn.chapter02.c51;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述：
 */
public class DaemoThread implements Runnable {


    @Override
    public void run() {
        while(true){
            System.out.println("我是一个守护线程，我的主线程关闭我也关闭了！");
        }
    }

    public static void main(String [] args) throws InterruptedException {
        Thread t1 = new Thread(new DaemoThread());
        // 守护线程的设置必须要在start之前进行设置
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(2000);
    }
}
