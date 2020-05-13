package com.lazyboyl.learn.chapter03.c47;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author linzf
 * @since 2020/5/13
 * 类描述： 测试CopyOnWriteArrayList和
 * Mode表示测量方式和角度
 * AverageTime： 调用的平均时间，表示每次调用所需要的时间
 * Throughput： 整体吞吐量，表示一秒的可以执行多少次调用
 * SampleTime： 随机取样，最后输出结果的分部，比如百分99的调用在xx毫秒内完成,百分99.99的调用在xxx毫秒内完成
 * SingleShotTime： 用于测试冷启动的性能，默认迭代一次为一秒
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class JmhListTest {

    private static CopyOnWriteArrayList copySmall = new CopyOnWriteArrayList();
    private static CopyOnWriteArrayList copyBig = new CopyOnWriteArrayList();
    private static ConcurrentLinkedDeque linkSmall = new ConcurrentLinkedDeque();
    private static ConcurrentLinkedDeque linkBig = new ConcurrentLinkedDeque();

    /**
     * 在进行测试之前先进行数据的初始化
     */
    @Setup
    public void setup() {
        for (int i = 0; i < 10000; i++) {
            copyBig.add(i);
            linkBig.add(i);
        }
        for (int i = 0; i < 100; i++) {
            copySmall.add(i);
            linkSmall.add(i);
        }
    }

    @Benchmark
    public void copySmallGet(){
        copySmall.get(50);
    }

    @Benchmark
    public void linkBigGet(){
        linkBig.peek();
    }

    @Benchmark
    public void copyBigGet(){
        copyBig.get(50);
    }

    @Benchmark
    public void linkSmallGet(){
        linkSmall.peek();
    }

    @Benchmark
    public void linkSmallSize(){
        linkSmall.size();
    }

    @Benchmark
    public void copySmallSize(){
        copySmall.size();
    }

    @Benchmark
    public void linkBigSize(){
        linkBig.size();
    }

    @Benchmark
    public void copyBigSize(){
        copyBig.size();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(JmhListTest.class.getSimpleName()).forks(4).build();
        new Runner(options).run();
    }



}
