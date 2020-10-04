package myPractice;

import java.sql.Array;
import java.util.Arrays;

public class GetkthOdd {

    public static void main(String[] args) {
        int[] arr = new int[]{99, 5, 2, 7, 1, 3, 4, 8, 0, 6, 9, 33};
        System.out.println(getKthOdd(arr, 1));
    }

    /**
     * 时间复杂度 O(nlogn)
     *
     * @param arr the arr
     * @param k   kth smallest odd ,start with 1
     */

    public static int getKthOdd(int[] arr, int k) {

        //1.先求出有多少个奇数
        int oddCount = 0;
        for (int i : arr) {
            if (i % 2 != 0) oddCount++;
        }

        if (k < 1 || k > oddCount) {
            return -1;
        }

        //2.把奇数放入数组,无序
        int[] oddArr = new int[oddCount];
        int index = 0;

        for (int i : arr) {
            if (i % 2 != 0) oddArr[index++] = i;
        }

        //排序:使用快速排序,时间复杂度 O(nlogn)
        System.out.println("奇数个数为:" + oddCount);
        //Arrays.sort(oddArr);
        quickSort(oddArr, 0, oddArr.length - 1);
        System.out.println(Arrays.toString(oddArr));

        return oddArr[k - 1];
    }

    private static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }
        int left = leftIndex;
        int right = rightIndex;
        int key = arr[left];
        while (left < right) {
            while (right > left && arr[right] >= key) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= key) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = key;
        quickSort(arr, leftIndex, left - 1);
        quickSort(arr, right + 1, rightIndex);
    }


}
