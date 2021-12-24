package juc;

import org.junit.Test;

import java.util.concurrent.*;


public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                3L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                //new ThreadPoolExecutor.CallerRunsPolicy()
                //new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()
                            + "\t 号业务员办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }


    @Test
    public void threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);//银行网点3个窗口
        //ExecutorService executorService = Executors.newSingleThreadExecutor();//银行网点1个窗口
        //ExecutorService executorService = Executors.newCachedThreadPool();//银行网点可扩展窗口

        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 号业务员办理业务 " + finalI);
                try {
                    Thread.sleep((int) (Math.random() * 10 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//            System.out.println("执行中...");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
//        threadPoolExecutor.shutdown();
//        while (threadPoolExecutor.getActiveCount() != 0) {
//            System.out.println("执行中...");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


    }


}
