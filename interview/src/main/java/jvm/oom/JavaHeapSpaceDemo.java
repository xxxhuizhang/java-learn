package jvm.oom;

import java.util.Random;

//-Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
//Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
public class JavaHeapSpaceDemo {
    
    public static void main(String[] args) {
//        byte[] bytes = new byte[6 * 1024 * 1024];
        outOfMemoryError();
    }

    public static void outOfMemoryError() {
        String str = "adf";
        while (true) {
            str += str + new Random().nextInt(1111111) + new Random().nextInt(222222);
            str.intern();
        }
    }
}

/*

imessage@huihui ~ % java -XX:+PrintCommandLineFlags -version
-XX:InitialHeapSize=268435456 -XX:MaxHeapSize=4294967296 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
java version "1.8.0_77"
Java(TM) SE Runtime Environment (build 1.8.0_77-b03)
Java HotSpot(TM) 64-Bit Server VM (build 25.77-b03, mixed mode)

[GC (Allocation Failure) [PSYoungGen: 1996K->505K(2560K)] 1996K->593K(9728K), 0.0009662 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 2393K->483K(2560K)] 2481K->951K(9728K), 0.0008273 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 2486K->400K(2560K)] 2953K->2329K(9728K), 0.0013126 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 1973K->432K(2560K)] 5852K->4310K(9728K), 0.0009892 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 1520K->432K(2560K)] 5399K->5292K(9728K), 0.0011428 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 1461K->0K(2560K)] [ParOldGen: 6809K->3308K(7168K)] 8270K->3308K(9728K), [Metaspace: 3086K->3086K(1056768K)], 0.0040302 secs] [Times: user=0.01 sys=0.01, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] 3308K->3308K(8704K), 0.0004098 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(1536K)] [ParOldGen: 3308K->3289K(7168K)] 3308K->3289K(8704K), [Metaspace: 3086K->3086K(1056768K)], 0.0046218 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
Heap
 PSYoungGen      total 1536K, used 75K [0x00000007bfd00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 1024K, 7% used [0x00000007bfd00000,0x00000007bfd12d68,0x00000007bfe00000)
  from space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
  to   space 1024K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007bff00000)
 ParOldGen       total 7168K, used 3289K [0x00000007bf600000, 0x00000007bfd00000, 0x00000007bfd00000)
  object space 7168K, 45% used [0x00000007bf600000,0x00000007bf9364c0,0x00000007bfd00000)
 Metaspace       used 3123K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 342K, capacity 388K, committed 512K, reserved 1048576K


 */
