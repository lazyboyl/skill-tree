package com.lazyboyl.learn.chapter02.c84;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： 不能使用Integer Long 这些包装类型作为锁，因为他们的本质里面实际上是重新new了一个对象
 */
public class BadLockIntegerThread {


    static Integer i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            synchronized (i){
                for(int j=0;j<100000;j++){
                    i++;
                }
            }
        });
        Thread t2 = new Thread(()->{
            synchronized (i){
                for(int j=0;j<100000;j++){
                    i++;
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("当前线程操作好以后的值为：" + i);
    }

}
