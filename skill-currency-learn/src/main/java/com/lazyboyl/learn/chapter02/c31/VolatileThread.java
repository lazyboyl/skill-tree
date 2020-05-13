package com.lazyboyl.learn.chapter02.c31;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： volatile原子的详解
 */
public class VolatileThread {

    public volatile static int i;

    /**
     *  volatile可以保证原子性，但是不能保证多线程操作同一个数据的原子性
     */
    public static void main(String [] args) throws InterruptedException {
        Thread [] t = new Thread[10];
        for(int j=0;j<10;j++){
            t[j] = new Thread(()->{
                for(int k=0;k<10000;k++){
                    i++;
                }
            });
            t[j].start();
        }
        for(int j=0;j<10;j++){
            t[j].join();
        }
        System.out.println("结果为：" + i);
    }

}
