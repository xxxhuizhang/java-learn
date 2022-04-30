package jvm.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {


    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);
        System.out.println("原对象\t" + o1);
        System.out.println("弱引用\t" + weakReference.get());
        System.out.println("引用队列\t" + referenceQueue.poll());

        System.out.println("==========");
        o1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println("原对象\t" + o1);
        System.out.println("弱引用\t" + weakReference.get());
        System.out.println("引用队列(GC)\t" + referenceQueue.poll());

    }


}
