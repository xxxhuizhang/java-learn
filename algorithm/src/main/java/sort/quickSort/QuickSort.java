package sort.quickSort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


/*
快速排序（Quicksort）是对冒泡排序的一种改进。
基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，
其中一部分的所有数据都比另外一部分的所有数据都要小，
然后再按此方法对这两部分数据分别进行快速排序，
整个排序过程可以递归进行，以此达到整个数据变成有序序列

https://www.cnblogs.com/luomeng/p/10587492.html

* 
*/


public class QuickSort {

	public static void main(String[] args) {
		// int[] arr = { -9, 78, 0, 23, -567, 70, -1, 900, 4561 };
		int[] arr = { 4, 6, 7, 1, 0, 9, 8, 2, 3, 5};

		// 测试快排的执行速度
		// 创建要给80000个的随机的数组
//		int[] arr = new int[8000000];
//		for (int i = 0; i < 8000000; i++) {
//			arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//		}

		System.out.println("排序前");
		Date data1 = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1Str = simpleDateFormat.format(data1);
		System.out.println("排序前的时间是=" + date1Str);

		quickSort(arr, 0, arr.length - 1);

		Date data2 = new Date();
		String date2Str = simpleDateFormat.format(data2);
		System.out.println("排序前的时间是=" + date2Str);
		System.out.println("arr=" + Arrays.toString(arr));
	}

	private static void quickSort(int[] arr, int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex) {
			return;
		}

		int left = leftIndex;
		int right = rightIndex;
		int key = arr[left];// 待排序的第一个元素作为基准值

		// 从左右两边交替扫描，直到left = right
		while (left < right) {
			// 从右往左扫描，找到第一个比基准值小的元素
			while (right > left && arr[right] >= key) {
				right--;
			}
			// 找到这种元素将arr[right]放入arr[left]中
			arr[left] = arr[right];
			// 从左往右扫描，找到第一个比基准值大的元素
			while (left < right && arr[left] <= key) {
				left++;
			}
			// 找到这种元素将arr[left]放入arr[right]中
			arr[right] = arr[left];
		}
		// 基准值归位
		arr[left] = key;
		// 对基准值左边的元素进行递归排序
		quickSort(arr, leftIndex, left - 1);
		// 对基准值右边的元素进行递归排序。
		quickSort(arr, right + 1, rightIndex);
	}

	// 韩老师写的
	public static void quickSort2(int[] arr, int left, int right) {
		int l = left; // 左下标
		int r = right; // 右下标
		// pivot 中轴值
		int pivot = arr[(left + right) / 2];
		int temp = 0; // 临时变量，作为交换时使用
		// while循环的目的是让比pivot 值小放到左边
		// 比pivot 值大放到右边
		while (l < r) {
			// 在pivot的左边一直找,找到大于等于pivot值,才退出
			while (arr[l] < pivot) {
				l += 1;
			}
			// 在pivot的右边一直找,找到小于等于pivot值,才退出
			while (arr[r] > pivot) {
				r -= 1;
			}
			// 如果l >= r说明pivot 的左右两的值，已经按照左边全部是
			// 小于等于pivot值，右边全部是大于等于pivot值
			if (l >= r) {
				break;
			}

			// 交换
			temp = arr[l];
			arr[l] = arr[r];
			arr[r] = temp;

			// 如果交换完后，发现这个arr[l] == pivot值 相等 r--， 前移
			if (arr[l] == pivot) {
				r -= 1;
			}
			// 如果交换完后，发现这个arr[r] == pivot值 相等 l++， 后移
			if (arr[r] == pivot) {
				l += 1;
			}
		}

		// 如果 l == r, 必须l++, r--, 否则为出现栈溢出
		if (l == r) {
			l += 1;
			r -= 1;
		}
		// 向左递归
		if (left < r) {
			quickSort(arr, left, r);
		}
		// 向右递归
		if (right > l) {
			quickSort(arr, l, right);
		}

	}

}
