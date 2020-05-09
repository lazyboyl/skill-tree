package com.lazyboyl.learn.chapter02.c23;

/**
 * @author linzf
 * @since 2020/5/7
 * 类描述：
 */
public class InterruptedThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true){
                // 判断当前线程是否已经被终端了
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("当前线程已经被中断了！");
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // 当我们在sleep的时候线程被中断这时候线程会抛出异常
                    e.printStackTrace();
                    // 因为异常已经被我们捕获了，所以我们需要再次设置中断要不会一直正常的循环下去
                    Thread.currentThread().interrupt();
                    System.out.println("中断当前线程了！");
                }
                System.out.println("异常中断了，我还会执行吗？");
                Thread.yield();
            }
        });
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }

}
