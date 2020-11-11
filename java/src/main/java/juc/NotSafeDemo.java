package juc;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 请举例说明集合类是不安全的
 */
public class NotSafeDemo {
    public static void main(String[] args) {
       Map<String,String> map = new ConcurrentHashMap();
        for (int i = 1; i <=30 ; i++) {
            new Thread(()->{
                map.put(UUID.randomUUID().toString().substring(0,8),Thread.currentThread().getName());
                System.out.println(map);

            },String.valueOf(i)).start();
        }
    }

    private static void nosafeSet() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <=30 ; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);

            },String.valueOf(i)).start();
        }
    }

    private static void nosafeList() {
        //        List<String> list = Arrays.asList("a","b","c");
//        list.forEach(System.out::println);
        List<String> list = new CopyOnWriteArrayList();

        //Collections.synchronizedList(new ArrayList<>());
        //new Vector<>();
        //new ArrayList<>();

        for (int i = 1; i <=30 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);

            },String.valueOf(i)).start();
        }
    }


}
