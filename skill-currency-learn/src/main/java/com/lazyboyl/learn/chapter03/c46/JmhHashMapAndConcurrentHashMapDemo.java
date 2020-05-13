package com.lazyboyl.learn.chapter03.c46;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author linzf
 * @since 2020/5/13
 * 类描述： 测试HashMap和ConcurrentHashMap以及HashMap的同步的模型
 * Mode表示测量方式和角度
 * AverageTime： 调用的平均时间，表示每次调用所需要的时间
 * Throughput： 整体吞吐量，表示一秒的可以执行多少次调用
 * SampleTime： 随机取样，最后输出结果的分部，比如百分99的调用在xx毫秒内完成,百分99.99的调用在xxx毫秒内完成
 * SingleShotTime： 用于测试冷启动的性能，默认迭代一次为一秒
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class JmhHashMapAndConcurrentHashMapDemo {
    /**
     * 线程不安全的mao
     */
    private static Map<String, String> hashMap = new HashMap<>();
    /**
     * 线程安全的map
     */
    private static Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 将线程不安全的map转换为安全的map
     */
    private static Map<String, String> safeHashMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * 在进行测试之前先进行数据的初始化
     */
    @Setup
    public void setup() {
        for (int i = 0; i < 10000; i++) {
            hashMap.put(i + "", i + "");
            concurrentHashMap.put(i + "", i + "");
            safeHashMap.put(i + "", i + "");
        }
    }

    @Benchmark
    public void getHashMap() {
        hashMap.get("48");
    }

    @Benchmark
    public void getConcurrentHashMap() {
        concurrentHashMap.get("48");
    }

    @Benchmark
    public void getSafeHashMap() {
        safeHashMap.get("48");
    }


    @Benchmark
    public void getHashMapSize() {
        hashMap.size();
    }

    @Benchmark
    public void getConcurrentHashMapSize() {
        concurrentHashMap.size();
    }

    @Benchmark
    public void getSafeHashMapSize() {
        safeHashMap.size();
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(JmhHashMapAndConcurrentHashMapDemo.class.getSimpleName()).forks(1).build();
        new Runner(options).run();
    }


}
