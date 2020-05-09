package com.lazyboyl.learn.chapter02.c83;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linzf
 * @since 2020/5/9
 * 类描述：
 */
public class HashMapMultiThread {

    /**
     * 使用HashMap是线程不安全的因此最终的结果并不是我们想要的20000的大小,而是会得到一个小余20000的值
     */
    static Map<String, String> map = new HashMap<String, String>();

    /**
     *  ConcurrentHashMap是线程安全的因此我们最终可以得到一个20000的值
     */
//    static Map<String, String> map = new ConcurrentHashMap<String, String>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i ++) {
                map.put(i + "", i + "");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                map.put(i + "", i + "");
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("集合大小为：" + map.size());
    }

}
