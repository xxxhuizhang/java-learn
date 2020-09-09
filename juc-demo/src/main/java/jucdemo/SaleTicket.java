package jucdemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket {
    private int number = 30;
    private Lock lock = new ReentrantLock();

    /* public synchronized void sale(){
         synchronized(this){
         }
         if(number>0){
             System.out.println(Thread.currentThread().getName()
             +"\t 卖出"+number--+"号票\t还剩"+number
             );
         }
     }*/
    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()
                        + "\t 卖出" + number-- + "号票\t还剩" + number
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }


}


public class SaleTicket {

    /**
     * 卖票程序复习线程知识：三个售票员  卖出  30张票
     *
     * @param args
     * @throws Exception 线程 操作 资源类,高内聚低耦合
     */
    public static void main(String[] args) throws Exception {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++) ticket.sale();
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) ticket.sale();
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) ticket.sale();
        }, "CC").start();
       /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "BB").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "CC").start();
*/
    }
}


/**
 * w  w   h
 * java.util.concurrent
 * java.util.concurrent.atomic
 * int   i++  100   AtomicInteger
 * <p>
 * java.util.concurrent.locks
 **/