package jvm.reference;

import java.lang.ref.SoftReference;


public class SoftReferenceDemo {
    public static void main(String[] args) {
        softRef_Memory_Enough();
        System.out.println("\n-------Not Enough------");
        softRef_Memory_NotEnough();
    }


    /*
    内存够用的时候就保留，不够用就回收。
    */
    private static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());
        System.out.println("===========");
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softReference.get());
    }

    /**
     * 配置小内存,产生大对象,故意OOM,查看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    private static void softRef_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());//内存足够不回收
        System.out.println("===========");
        o1 = null;
        //System.gc();//会自动 GC
        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());//内存不足 回收
        }
    }
}
