package jvm;

public class HelloGC {


    public static void main(String[] args) throws InterruptedException {
//        int res = oneAddone(1,1);
//        System.out.println(res);
        showGC();
//        alwaysSleep();
//        getMemory();
    }


    public static void getMemory() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("TotalMemory(-Xms)=" + totalMemory + "字节" + (totalMemory / 1024 / 1024) + "M");
        System.out.println("MaxMemory(-Xmx)=" + maxMemory + "字节" + (maxMemory / 1024 / 1024) + "M");
    }

    public static int oneAddone(int x, int y) {
        int res = x + y;
        System.out.println(res);
        return res;
    }

    public static void alwaysSleep() throws InterruptedException {
        System.out.println("-------alwaysSleep-------");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void showGC() {
        //-Xms10m -Xmx10m -XX:+PrintGCDetails
        //-Xms128m -Xmx4096m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+UseSerialGC -XX:+PrintGCDetails
        //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:SurvivorRatio=4 (UseSerialGC设置之后才生效)
        //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:NewRatio=4

        byte[] bytes = new byte[50 * 1024 * 1024];
    }
}
