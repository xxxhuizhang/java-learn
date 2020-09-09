package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（也就是递归锁）：指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个
 * 它已经拥有的锁所有同步着的代码块。
 * <p>
 * ReentrantLock/Synchronized就是典型的可重入锁
 * <p>
 * Synchronized可重入锁,独占锁,非公平锁
 * ReentrantLock 可重入锁,独占锁,非公平锁或公平锁
 */

public class ReentrantLockDemo {

    public static void main(String[] args) {

        Phone phone = new Phone();

        //Synchronized就是典型的可重入锁
//        syncTest(phone);
//        System.out.println();


        //ReentrantLock就是典型的可重入锁
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();

    }

    private static void syncTest(Phone phone) {

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

class Phone implements Runnable {
    //Synchronized TEST

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getId() + "\t" + "sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t" + "sendEmail()");
    }

    //Reentrant TEST

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t" + "get()");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t" + "set()");
        } finally {
            lock.unlock();
        }
    }
}
