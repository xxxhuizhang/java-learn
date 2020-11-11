package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class MyThread implements Runnable {

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName() + "---Callable.call()");
        return 200;
    }
}

class MyThread3 extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("MyThread3 running");
    }
}


/**
 *
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //new  MyThread3().start();
        //FutureTask(Callable<V> callable)
        //FutureTask<Integer> ft = new FutureTask<Integer>(new MyThread2());
        //FutureTask<Integer> ft2 = new FutureTask<Integer>(new MyThread2());

        /*
        FutureTask<Integer> ft = new FutureTask<Integer>(() -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + "---Callable.call()");
            return 1024;
        });

        new Thread(ft, "zhang3").start();
        // new Thread(ft2,"li4").start();
        System.out.println(Thread.currentThread().getName());
        while (!ft.isDone()) {
            System.out.println("-----wait");
        }
        System.out.println(ft.get());
        //System.out.println(ft2.get());

         */
    }
}
