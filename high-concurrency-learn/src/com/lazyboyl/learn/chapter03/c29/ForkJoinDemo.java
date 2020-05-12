package com.lazyboyl.learn.chapter03.c29;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述： 模拟处理从数据库获取的1000万条用户的数据，然后分批组装成csv的格式的数据
 */
public class ForkJoinDemo {

    public static List<UserInfo> userList = new ArrayList<>(50000);

    /**
     * 执行以下的程序可以发现就3秒左右就完成了，这就说明了我们的ForkJoin的妙用
     */
    public static void main(String[] args) {
        System.out.println("开始：" + System.currentTimeMillis() / 1000);
        UserInfo userInfo = null;
        Random r = new Random();
        for (int i = 0; i < 50000; i++) {
            userInfo = new UserInfo();
            userInfo.setUserName(r.nextInt(50000) + "测试" + i);
            userList.add(userInfo);
        }
        System.out.println("结束：" + System.currentTimeMillis() / 1000);
        ForkJoinPool task = new ForkJoinPool();
        CsvTask csvTask = new CsvTask(0, 50000);
        System.out.println("开始生成数据：" + System.currentTimeMillis() / 1000);
        ForkJoinTask<StringBuilder> csvForkJoinTask = task.submit(csvTask);
        try {
            StringBuilder result = csvForkJoinTask.get();
//            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("完成数据的生成：" + System.currentTimeMillis() / 1000);
    }

    /**
     * 传统的方式生成数据,假定我们在处理数据的时候需要花费1毫秒，那么以下的程序执行完成需要50秒以上的时间
     *
     * @param args
     */
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("开始：" + System.currentTimeMillis() / 1000);
//        UserInfo userInfo = null;
//        for (int i = 0; i < 50000; i++) {
//            userInfo = new UserInfo();
//            userInfo.setUserName("测试" + i);
//            userList.add(userInfo);
//        }
//        System.out.println("结束：" + System.currentTimeMillis() / 1000);
//        System.out.println("开始生成数据：" + System.currentTimeMillis() / 1000);
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < 50000; i++) {
//            result.append(userList.get(i).getUserName()).append(",");
//            Thread.sleep(1);
//        }
//        System.out.println("完成数据的生成：" + System.currentTimeMillis() / 1000);
//
//    }

}
