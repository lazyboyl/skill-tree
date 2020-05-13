package com.lazyboyl.learn.chapter03.c41;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author linzf
 * @since 2020/5/13
 * 类描述： 第一个JMH的测试类
 * Mode表示测量方式和角度
 * AverageTime： 调用的平均时间，表示每次调用所需要的时间
 * Throughput： 整体吞吐量，表示一秒的可以执行多少次调用
 * SampleTime： 随机取样，最后输出结果的分部，比如百分99的调用在xx毫秒内完成,百分99.99的调用在xxx毫秒内完成
 * SingleShotTime： 用于测试冷启动的性能，默认迭代一次为一秒
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class JMHSample_01_Helloword {

    @Benchmark
    public void helloWord() throws InterruptedException {
        System.out.println("执行一次");
        Thread.sleep(100);
    }

    public static void main(String[] args) throws RunnerException {
        /**
         * include：需要测试的类
         * forks： 使用的进程数
         * warmupIterations： 预热的次数
         */
        Options options = new OptionsBuilder().include(JMHSample_01_Helloword.class.getSimpleName()).warmupIterations(10).forks(1).build();
        new Runner(options).run();
    }

}
