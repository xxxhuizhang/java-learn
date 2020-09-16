package kmp;

import java.util.Arrays;

/**
 * 1.KMP是一个解决模式串在文本串是否出现过，如果出现过，最早出现的位置的经典算法
 * 2.Knuth-Morris-Pratt 字符串查找算法，简称为 “KMP算法”，
 * 常用于在一个文本串S内查找一个模式串P的出现位置，
 * 这个算法由Donald Knuth、Vaughan Pratt、James H. Morris三人于1977年联合发表，故取这3人的姓氏命名此算法.
 * 3.KMP方法算法就利用之前判断过信息，通过一个next数组，保存模式串中前后最长公共子序列的长度，
 * 每次回溯时，通过next数组找到，前面匹配过的位置，省去了大量的计算时间
 */

public class KMPAlgorithm {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        // String str2 = "BBC";

        //部分匹配值表
        int[] next = kmpNext("ABCDABD"); // [0,0,0,0,1,2,0]
        System.out.println("next=" + Arrays.toString(next));

        int index = kmpSearch(str1, str2, next);
        System.out.println("index=" + index); // 15了

    }

    // 写出我们的kmp搜索算法

    /**
     * @param str1 源字符串
     * @param str2 子串
     * @param next 部分匹配表, 是子串对应的部分匹配表
     * @return 如果是-1就是没有匹配到，否则返回第一个匹配的位置
     */
    public static int kmpSearch(String str1, String str2, int[] next) {
        // 遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {
            // 需要处理 str1.charAt(i) ！= str2.charAt(j), 去调整j的大小
            // KMP算法核心点, 可以验证...
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {// 找到了 // j = 3 i
                return i - j + 1;
            }
        }
        return -1;
    }

    // 获取到一个字符串(子串) 的部分匹配值表
    public static int[] kmpNext(String dest) {// AAAB [0,1,2,0]
        // 创建一个next 数组保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0; // 如果字符串是长度为1 部分匹配值就是0
        for (int i = 1, j = 0; i < dest.length(); i++) {
            // 当dest.charAt(i) != dest.charAt(j) ，我们需要从next[j-1]获取新的j
            // 直到我们发现 有 dest.charAt(i) == dest.charAt(j)成立才退出
            // 这时kmp算法的核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            // 当dest.charAt(i) == dest.charAt(j) 满足时，部分匹配值就是+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

}
