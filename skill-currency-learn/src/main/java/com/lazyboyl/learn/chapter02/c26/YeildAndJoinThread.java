package com.lazyboyl.learn.chapter02.c26;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述： yeild和join方法的验证
 */
public class YeildAndJoinThread {

    public static volatile int i = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (i < 10000) {
                i++;
            }
        });
        t1.start();
        // 若不加上t1.join将会导致我们的程序立马结束，加上join则会等到线程立马的代码执行结束了再往下执行
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===>" + i);
    }

}
