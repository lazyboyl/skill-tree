package com.lazyboyl.learn.chapter02.c21;

/**
 * @author linzf
 * @since 2020/5/7
 * 类描述：
 */
public class CreateThread01 {

    public static void main(String [] args){
        Thread t1 = new Thread(()->{
            System.out.print("hello thread");
        });
        t1.start();
    }

}
