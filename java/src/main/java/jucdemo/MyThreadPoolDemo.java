package jucdemo;

import java.util.concurrent.*;

/**
 * 线程池例子
 * <p>
 * Arrays
 * Collections
 */
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
            for (int i = 1; i <=10 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()
                            +"\t 号业务员办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

    private static void threadPool() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);//银行网点3个窗口
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();//银行网点1个窗口
        ExecutorService threadPool3 = Executors.newCachedThreadPool();//银行网点可扩展窗口

        try {
            for (int i = 1; i <=30 ; i++) {
                threadPool3.execute(()->{
                    System.out.println(Thread.currentThread().getName()
                            +"\t 号业务员办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool3.shutdown();
        }
    }


}
