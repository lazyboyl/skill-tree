package com.lazyboyl.learn.chapter03.c29;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author linzf
 * @since 2020/5/12
 * 类描述：
 */
public class CsvTask extends RecursiveTask<StringBuilder> {
    // 设置阈值为10000
    private static final int THRESHOLD = 1000;

    private int start;
    private int end;

    public CsvTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected StringBuilder compute() {
        StringBuilder sb = new StringBuilder();
        // 判断当前的任务是否需要进行分解
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i < end; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sb.append(ForkJoinDemo.userList.get(i).getUserName()).append(",");
            }
        } else {
            // 分成100个小任务
            int step = (start + end) / 100;
            List<CsvTask> csvTaskList = new ArrayList<>();
            int pos = start;
            // 分成100个小任务来进行调用
            for (int i = 0; i < 100; i++) {
                int lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                CsvTask csvTask = new CsvTask(pos, lastOne);
                pos = pos + step + 1;
                csvTaskList.add(csvTask);
                // 等于重新调用了compute()方法
                csvTask.fork();
            }
            for (CsvTask csvTask : csvTaskList) {
                // csvTask.join 等待线程执行完成
                sb.append(csvTask.join());
            }
        }
        return sb;
    }
}
