package com.lazyboyl.learn.chapter02.c02;

/**
 * @author linzf
 * @since 2020/5/7
 * 类描述： stop方法导致的数据的不一致
 */
public class StopThreadUnsafe {

    public static User u = new User();

    public static void main(String [] args){
        new Thread(new ReadObjectThread()).start();
        while(true){
            Thread t = new Thread(new ChangeObjectThread());
            t.start();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.stop();
        }
    }

    /**
     * 实现改变对象的值
     */
    public static class ChangeObjectThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    int v = (int) System.currentTimeMillis() / 1000;
                    u.setId(v);
                    // 此处设置休眠100毫秒，就是为了保证后面我们的主入口方法可以
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    u.setName(v + "");
                    // 让出当前的线程
                    Thread.yield();
                }
            }
        }
    }


    public static class ReadObjectThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                   if(u.getId() != Integer.parseInt(u.getName())){
                       System.out.println(u.getId() + "*-----*" + u.getName());
                   }
                }
                // 让出当前的线程
                Thread.yield();
            }
        }
    }

    public static class User {
        private int id;

        private String name;

        public User() {
            this.id = 0;
            this.name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
