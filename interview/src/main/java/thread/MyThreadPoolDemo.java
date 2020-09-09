package thread;

import java.util.concurrent.*;

/**
 * 第四种使用Java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        System.out.println(MyThread.class.getClassLoader().getParent());

        System.out.println(Runtime.getRuntime().availableProcessors());//查看服务器CPU多少核

//        System.out.println("Fixed Thread Pool");
//        fixedThreadPool();
//        System.out.println("Single Thread Pool");
//        singleThreadPool();
//        System.out.println("Cached Thread Pool");
        cachedThreadPool();
//        System.out.println("Custom Thread Pool");
//        customThreadPool();
    }

    private static void customThreadPool() {
        ExecutorService threadPool =
                new ThreadPoolExecutor(2,
                        5,
                        1L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()
                );
        try {
            for (int i = 0; i < 8; i++) { //最好不要大于 5+3 个. 否则RejectedExecutionException
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void cachedThreadPool() {
        //不定量线程
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) { //Integer.MAX_VALUE
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
//Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void singleThreadPool() {
        //一池1个线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 9; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void fixedThreadPool() {
        //一池5个线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一般常用try-catch-finally
        //模拟10个用户来办理业务，每个用户就是一个线程
        try {
            for (int i = 0; i < 9; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
