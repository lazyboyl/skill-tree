package com.lazyboyl.learn.chapter02.c01;

/**
 * @author linzf
 * @since 2020/5/7
 * 类描述：
 */
public class CreateThread03 implements Runnable {

    public static void main(String [] args){
        new Thread(new CreateThread03()).start();
    }

    @Override
    public void run() {
        System.out.print("我是runnable来实现的");
    }
}
