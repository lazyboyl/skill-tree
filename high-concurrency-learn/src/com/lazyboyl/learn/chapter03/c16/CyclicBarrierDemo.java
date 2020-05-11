package com.lazyboyl.learn.chapter03.c16;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author linzf
 * @since 2020/5/11
 * 类描述： CyclicBarrier是countdown的升级版，并且CyclicBarrier可以循环使用，他表示只要凑够了设置好的n个线程
 * 就可以开始相应的操作，同时在满足的时候还会有一个回调函数。
 */
public class CyclicBarrierDemo {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
        System.out.println("你们已经凑齐十个线程了，那我就可以带你们干活了！");
    });

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("现在可以开始干活了小伙子！");
            }).start();
        }
    }

}
