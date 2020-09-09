package practice;

import java.util.Arrays;

public class InsertSort {

	public static void main(String[] args) {

		int[] arr = { 101, 34, 119, 1, -1, 89 };
		int[] sortedArr = insertSort(arr);
		System.out.println(Arrays.toString(sortedArr));

	}

	public static int[] insertSort(int[] arr) {

		int insertIndex = 0;
		int insertVal = 0;

		for (int i = 1; i < arr.length; i++) {
			insertIndex = i - 1;
			insertVal = arr[i];

			while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
				arr[insertIndex + 1] = arr[insertIndex];
				insertIndex--;
			}

			if (insertIndex != i - 1) {
				arr[insertIndex +1] = insertVal;
			}

		}

		return arr;
	}

}
