package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class BubbleSort {

    public static void main(String[] args) {
//		int arr[] = {3, 9, -1, 10, 20};
//
//		System.out.println("排序前");
//		System.out.println(Arrays.toString(arr));

        //为了容量理解，我们把冒泡排序的演变过程，给大家展示

        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); //生成一个[0, 8000000) 数
        }

        System.out.println("排序前:" + Arrays.toString(arr));
        long before = System.currentTimeMillis();

        bubbleSort(arr);

        System.out.println("排序的时间是=" + (System.currentTimeMillis() - before));
        System.out.println("排序后=" + Arrays.toString(arr));

    }

    // 将前面额冒泡排序算法，封装成一个方法
    public static void bubbleSort(int[] arr) {
        // 冒泡排序 的时间复杂度 O(n^2), 自己写出
        int temp = 0; // 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            //System.out.println("第" + (i + 1) + "趟排序后的数组");
            //System.out.println(Arrays.toString(arr));

            if (!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false; // 重置flag!!!, 进行下次判断
            }
        }
    }

}
