package com.itzhai.algorithm.datastructures.suffixarray;

import java.util.Arrays;

/**
 * 后缀数组-倍增算法实现
 *
 * [Java后缀数组-求sa数组](https://www.cnblogs.com/mengzhang/p/8919870.html)
 *
 */
public class SuffixArray1 {

	/**
	 * 原始字符串
	 */
	public char[] suffix;

	/**
	 * 字符串长度
	 */
	public int n;

	/**
	 * Suffix[i]在所有后缀中的排名
	 */
	public int[] rank;

	/**
	 * 满足Suffix[SA[1]] < Suffix[SA[2]] …… < Suffix[SA[Len]],即排名为i的后缀为Suffix[SA[i]]
	 */
	public int[] sa;
	// (与Rank是互逆运算)

	/**
	 * 表示Suffix[SA[i]]和Suffix[SA[i - 1]]的最长公共前缀，也就是排名相邻的两个后缀的最长公共前缀
	 */
	public int[] height;

	/**
	 * 等于Height[Rank[i]]，也就是后缀Suffix[i]和它前一名的后缀的最长公共前缀
	 */
	public int[] h;

	/**
	 * 计数排序辅助数组
	 */
	public int[] ws;

	/**
	 * 第二关键字rank数组
	 */
	public int[] y;

	/**
	 * rank的辅助数组
	 */
	public int[] x;

	public SuffixArray1(String str) {
		n = str.length();
		suffix = new char[n];
		for (int i = 0; i < n; i++) {
			suffix[i] = str.charAt(i);
		}
	}

	public static void main(String[] args) {
		String str = "aabaaaab";
		SuffixArray1 arrayTest = new SuffixArray1(str);
		arrayTest.initSa();// 求sa数组
		System.out.println(Arrays.toString(arrayTest.sa));
	}

	public void initSa() {
		// 初始化数组，并进行一次计数排序
		rank = new int[n];
		sa = new int[n];
		ws = new int[255];
		y = new int[n];
		x = new int[n];
		// 循环原字符串转换int值放入rank数组
		for (int i = 0; i < n; i++) {
			rank[i] = (int) suffix[i];
		}
		// 第一次计数排序
		for (int i = 0; i < n; i++) {
			ws[rank[i]]++;
			x[i] = rank[i];
		}
		for (int i = 1; i < ws.length; i++) {
			ws[i] += ws[i - 1];
		}
		for (int i = n - 1; i >= 0; i--) {
			sa[--ws[rank[i]]] = i;
		}
		// 循环组合排序
		for (int j = 1, p = 0; j <= n; j = j << 1) {
			// 需要补位的先加入排序数组y
			p = 0;
			for (int i = n - j; i < n; i++) {
				y[p++] = i;
			}
			// 根据第一关键字sa排出第二关键字
			for (int i = 0; i < n; i++) {
				if (sa[i] >= j) {
					y[p++] = sa[i] - j;
				}
			}

			// 合并两个关键字的排序
			for (int i = 0; i < ws.length; i++) {
				ws[i] = 0;
			}
			for (int i : x) {
				ws[i]++;
			}
			for (int i = 1; i < ws.length; i++) {
				ws[i] += ws[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				sa[--ws[x[y[i]]]] = y[i];
				y[i] = 0;
			}

			// 根据sa算出rank数组
			int xb[] = new int[n];// x数组备份
			for (int i = 0; i < n; i++) {
				xb[i] = x[i];
			}
			int number = 1;
			x[sa[0]] = 1;
			for (int i = 1; i < n; i++) {
				if (xb[sa[i]] != xb[sa[i - 1]]) {
					x[sa[i]] = ++number;
				} else if (sa[i] + j >= n && sa[i - 1] + j >= n) {
					x[sa[i]] = number;
				} else if (sa[i] + j < n && sa[i - 1] + j >= n) {
					x[sa[i]] = ++number;
				} else if (xb[sa[i] + j] != xb[sa[i - 1] + j]) {
					x[sa[i]] = ++number;
				} else {
					x[sa[i]] = number;
				}
				if (number >= n) {
					break;
				}
			}
		}
	}

}
