package jucdemo;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {

        //同步，异步，异步回调
        //MQ消息中间件
        //同步

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("CompletableFuture.runAsync");
        });
        future1.get();

        //异步回调
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture.supplyAsync");
            int a = 10 / 0;
            return 1024;
        });

        future2.whenComplete(
                (t, u) -> {
                    System.out.println("*****t=" + t);
                    System.out.println("*****u=" + u);
                }
        ).exceptionally(
                f -> {
                    System.out.println(f.getMessage());
                    return 444;
                }
        );

    }
}
