package thread.lock.deadLock;


/*
 * 死锁是指两个或者两个以上的进程在执行过程中，因抢夺资源而造成的一种互相等待的现象，
 * 若无外力干涉它们将都无法推进下去，如果系统资源充足，进程的资源请求都能够得到满足，
 * 死锁出现的可能性也就很低，否则就会因争夺有限的资源而陷入死锁。
 *
 * linux ps -ef|grep xxxx    ls -l查看当前进程的命令
 * windows下的java运行程序，也有类似ps的查看进程的命令，但是目前我们需要查看的只是java
 * jps = java ps
 *
 * 如何证明产生死锁?
 * ps -l
 * jstack 33446
 * ==> Found 1 deadlock.
 *
 * */


public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "ThreadA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadB").start();
    }
}

class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有11：" + lockA + "\t尝试获取：" + lockB);
            try {
                //TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有22：" + lockB + "\t尝试获取：" + lockA);
            }
        }
    }
}
