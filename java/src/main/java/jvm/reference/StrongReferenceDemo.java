package jvm.reference;

public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        o1 = null;
        System.gc();
        System.out.println(o2); //不会被回收
    }
}
