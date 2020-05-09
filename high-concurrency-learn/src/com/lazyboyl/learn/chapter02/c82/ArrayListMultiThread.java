package com.lazyboyl.learn.chapter02.c82;

import java.util.List;
import java.util.Vector;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述：
 */
public class ArrayListMultiThread {
    // 若使用这个不安全的线程ArrayList将会导致最终返回的不是我们想要的结果
    //    static List<Integer> list = new ArrayList<>();
    /**
     * Vector是线程安全的因此可以获取我们正常返回的数组长度为200000
     */
    static List<Integer> list = new Vector<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("当前数组的大小为：" + list.size());
    }


}
