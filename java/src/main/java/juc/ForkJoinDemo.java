package juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


class MyTask extends RecursiveTask<Integer> {
    public static final int ADJAST_VALUE = 10;
    private int begin;
    private int end;
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= ADJAST_VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            int mid = (begin + end) / 2;
            MyTask myTask1 = new MyTask(begin, mid);
            MyTask myTask2 = new MyTask(mid + 1, end);
            myTask1.fork();
            myTask2.fork();

            result = myTask1.join() + myTask2.join();
        }

        return result;
    }
}


/**
 * 分支合并例子
 * ForkJoinPool
 * ForkJoinTask
 * RecursiveTask
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask forkJoinTask = forkJoinPool.submit(myTask);
//        ForkJoinTask forkJoinTask = forkJoinPool.invoke(myTask);
        System.out.println(forkJoinTask.get());
    }
}
