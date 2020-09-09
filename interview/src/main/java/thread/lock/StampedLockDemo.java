package thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
 *https://baijiahao.baidu.com/s?id=1649614767637994792&wfr=spider&for=pc
 *
 * https://www.jianshu.com/p/21839c0d2835
 *
 * 比如synchronized不可中断等，ReentrantLock 未能读写分离实现，虽然ReentrantReadWriteLock能够读写分离了，
 * 但是对于其写锁想要获取的话，就必须没有任何其他读写锁存在才可以，这实现了悲观读取。
 * 而且如果读操作很多，写很少的情况下，线程有可能遭遇饥饿问题。
 *
 * 饥饿问题：ReentrantReadWriteLock实现了读写分离，想要获取读锁就必须确保当前没有其他任何读写锁了，
 * 但是一旦读操作比较多的时候，想要获取写锁就变得比较困难了，因为当前有可能会一直存在读锁。而无法获得写锁。
 *
 * tampedLock是Java 8新增的一个读写锁，它是对ReentrantReadWriteLock的改进。
 * StampedLock的同步状态包含了一个版本和模式，获取锁的方法返回一个stamp表示这个锁的状态；
 * 而这些方法的 "try" 版本返回一个特殊值0表示获取锁失败。锁释放和转换的方法需要stamp作为参数，
 * 如果stamp不符合锁的同步状态就会失败。StampedLock提供了三种模式的控制：
 *
 * 独占写模式。writeLock方法可能会在获取共享状态时阻塞，如果成功获取锁，返回一个stamp，
 * 它可以作为参数被用在unlockWrite方法中以释放写锁。tryWriteLock的超时与非超时版本都被提供使用。
 * 当写锁被获取，那么没有读锁能够被获取并且所有的乐观读锁验证都会失败。
 *
 * 悲观读模式。readLock方法可能会在获取共享状态时阻塞，如果成功获取锁，返回一个stamp，
 * 它可以作为参数被用在unlockRead方法中以释放读锁。tryReadLock的超时与非超时版本都被提供使用。
 *
 * 乐观读模式。tryOptimisticRead方法只有当写锁没有被获取时会返回一个非0的stamp。
 * 在获取这个stamp后直到调用validate方法这段时间，如果写锁没有被获取，
 * 那么validate方法将会返回true。这个模式可以被认为是读锁的一个弱化版本，
 * 因为它的状态可能随时被写锁破坏。这个乐观模式的主要是为一些很短的只读代码块的使用设计，
 * 它可以降低竞争并且提高吞吐量。但是，它的使用本质上是很脆弱的。
 * 乐观读的代码区域应当只读取共享数据并将它们储存在局部变量中以待后来使用，
 * 当然在使用前要先验证这些数据是否过期，这可以使用前面提到的validate方法。
 * 在乐观读模式下的数据读取可能是非常不一致的过程，
 * 因此只有当你对数据的表示很熟悉并且重复调用validate方法来检查数据的一致性时使用此模式。
 * 例如，当先读取一个对象或者数组引用，然后访问它的字段、元素或者方法之一时上面的步骤都是需要的。
 *
 * 这个类还提供了在三种模式之间转换的辅助方法。
 * 例如，tryConvertToWriteLock方法尝试"提升"一个模式，如果已经获取了读锁并且此时没有其他线程获取读锁，
 * 那么这个方法返回一个合法的写stamp。这些方法被设计来帮助减少以“重试为主”设计时发生的代码代码膨胀。
 *
 * 作者：shallowinggg
 * 链接：https://www.jianshu.com/p/21839c0d2835
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 *
 */

public class StampedLockDemo {


}


class Point {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    void move(double deltaX, double deltaY) { // an exclusively locked method
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() { // A read-only method
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    void moveIfAtOrigin(double newX, double newY) { // upgrade
        // Could instead start with optimistic, not read mode
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }
}