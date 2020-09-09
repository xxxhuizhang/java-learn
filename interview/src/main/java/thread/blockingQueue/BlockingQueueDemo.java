package thread.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/*
 * ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序
 * LinkedBlockingQueue:是一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量高于ArrayBlockingQueue
 * SynchronousQueue：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移出操作，否则插入操作一直处于
 * 阻塞状态，吞吐量通常要高
 *
 * 2.阻塞队列
 *   2.1阻塞队列有没有好的一面
 *   2.2不得不阻塞，你如何管理
 *
 * */


public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
//        addAndRemove(blockingQueue);
//        offerAndPoll(blockingQueue);
//        putAndTake(blockingQueue);
//        outOfTime(blockingQueue);
    }

    private static void outOfTime(BlockingQueue<String> blockingQueue) throws InterruptedException {
        // Inserts the specified element into this queue, waiting up to the
        // specified wait time if necessary for space to become available.
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
    }

    private static void putAndTake(BlockingQueue<String> blockingQueue) throws InterruptedException {
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("d");
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        blockingQueue.put("d");
        System.out.println(blockingQueue.take());
    }

    private static void offerAndPoll(BlockingQueue<String> blockingQueue) {
        System.out.println(blockingQueue.offer("a")); //true
        System.out.println(blockingQueue.offer("b"));//true
        System.out.println(blockingQueue.offer("c"));//true
        System.out.println(blockingQueue.offer("e"));//false
        System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//c
        System.out.println(blockingQueue.poll());//null
    }

    private static void addAndRemove(BlockingQueue<String> blockingQueue) {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //System.out.println(blockingQueue.add("e")); //java.lang.IllegalStateException: Queue full
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //System.out.println(blockingQueue.remove()); //java.util.NoSuchElementException

    }
}
