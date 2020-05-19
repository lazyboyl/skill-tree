package com.lazyboyl.learn.chapter04.c32;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author linzf
 * @since 2020/5/19
 * 类描述：
 */
public class ThreadLocalSafeDemo {

    static ThreadLocal<SimpleDateFormat> ts = new ThreadLocal<SimpleDateFormat>();

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (ts.get() == null) {
                ts.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            try {
                Date date = ts.get().parse("2020-05-19 12:12:" + i % 60);
                System.out.println(i + ":" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            es.execute(new ParseDate(i));
        }
    }

}
