package thread.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
1 验证volatile的可见性
    1.1 加入int number=0，number变量之前根本没有添加volatile关键字修饰,没有可见性
    1.2 添加了volatile，可以解决可见性问题
2 验证volatile不保证原子性

    2.1 原子性是不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者分割。
    需要整体完成，要么同时成功，要么同时失败。

    2.2 volatile不可以保证原子性演示

    2.3 如何解决原子性
        *加sync
        *使用我们的JUC下AtomicInteger

* */

class MyData {

    //    int number = 0;
    volatile int number = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void setTo60() {
        this.number = 60;
    }

    //此时number前面已经加了volatile，但是不保证原子性
    public void addPlusPlus() {
        number++;
    }

    public void addAtomic() {
        atomicInteger.getAndIncrement();
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        volatileVisibilityDemo();
        atomicDemo();
    }

    private static void atomicDemo() {
        System.out.println("原子性测试");
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t int type finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type finally number value: " + myData.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其它线程主物理内存的值已被修改
    private static void volatileVisibilityDemo() {
        System.out.println("可见性测试");
        MyData myData = new MyData();//资源类
        //启动一个线程操作共享数据
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setTo60();
                System.out.println(Thread.currentThread().getName() + "\t update number value: " + myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        while (myData.number == 0) { //不可见会卡到这
            //main线程持有共享数据的拷贝，一直为0
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over. main get number value: " + myData.number);
    }
}


