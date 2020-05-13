package com.lazyboyl.learn.chapter03.c45;

import com.lazyboyl.learn.chapter03.c41.JMHSample_01_Helloword;
import org.openjdk.jmh.annotations.*;
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
public class JmhStateDemo {

    @State(Scope.Benchmark)
    public static class BenchmarkSate {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class BenchmarkThread {
        volatile double x = Math.PI;
    }

    @Benchmark
    public void unShared(BenchmarkSate state) {
        state.x++;
    }

    @Benchmark
    public void shared(BenchmarkThread state) {
        state.x++;
    }

    public static void main(String[] args) throws RunnerException {
        /**
         * include：需要测试的类
         * forks： 使用的进程数
         * warmupIterations： 预热的次数
         */
        Options options = new OptionsBuilder().include(JmhStateDemo.class.getSimpleName()).warmupIterations(10).forks(1).build();
        new Runner(options).run();
    }

}
