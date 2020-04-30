package com.itzhai.algorithm.datastructures.fenwicktree;

import java.util.Arrays;

/**
 * 单点更新，区间查找 Fenwick Tree
 *
 * Created by arthinking on 21/4/2020.
 */
public class FenwickTreeRangeQueryPointUpdate {

	/**
	 * 存储 Fenwick tree 值的数组的大小
	 */
	final int N;

	/**
	 * 该数组用于存储 Fenwick tree，该数组从索引1开始
	 */
	private long[] tree;

	/**
	 * 创建一个大小为sz的空的 Fenwick Tree
	 * @param sz
	 */
	public FenwickTreeRangeQueryPointUpdate(int sz) {
		tree = new long[(N = sz + 1)];
	}

	/**
	 * 通过一个数组构造一颗Fenwick tree，数组索引必须从1开始，构造时间复杂度：O(n)
	 * @param values
	 */
	public FenwickTreeRangeQueryPointUpdate(long[] values) {

		if (values == null) {
			throw new IllegalArgumentException("Values array cannot be null!");
		}

		N = values.length;
		values[0] = 0L;

		// 为了避免直接操纵原数组，破坏了其所有原始内容，我们复制一个values数组
		tree = values.clone();

		for (int i = 1; i < N; i++) {
			// 获取当前节点的父节点
			int parent = i + lsb(i);
			if (parent < N) {
				// 父节点累加当前节点的值
				tree[parent] += tree[i];
			}
		}
	}

	/**
	 * 返回i对应的最低有效位（least significant bit (LSB)）
	 *
	 * lsb(108) = lsb(0b1101100) =     0b100 = 4
	 * lsb(104) = lsb(0b1101000) =    0b1000 = 8
	 * lsb(96)  = lsb(0b1100000) =  0b100000 = 32
	 * lsb(64)  = lsb(0b1000000) = 0b1000000 = 64
	 *
	 * @param i
	 * @return
	 */
	private static int lsb(int i) {
		return i & -i;
		// 这里也可以直接用Java的内置方法：
		// return Integer.lowestOneBit(i);
	}

	/**
	 * 计算 [1, i] 区间元素之和, O(log(n))
 	 */
	private long prefixSum(int i) {
		long sum = 0L;
		while (i != 0) {
			sum += tree[i];
			// 等价于 i -= lsb(i);
			i &= ~lsb(i);
		}
		return sum;
	}

	/**
	 * 计算 [left, right] 区间元素之和, O(log(n))
 	 */
	public long sum(int left, int right) {
		if (right < left) {
			throw new IllegalArgumentException("Make sure right >= left");
		}
		return prefixSum(right) - prefixSum(left - 1);
	}

	/**
	 * 获取第i个元素的值
	 * @param i
	 * @return
	 */
	public long get(int i) {
		return sum(i, i);
	}

	/**
	 * 添加 v 到 索引 i 处，O(log(n))
	 * @param i
	 * @param v
	 */
	public void add(int i, long v) {
		while (i < N) {
			tree[i] += v;
			i += lsb(i);
		}
	}

	/**
	 * 索引i处的值设置为 v，O(log(n))
	 * @param i
	 * @param v
	 */
	public void set(int i, long v) {
		add(i, v - sum(i, i));
	}

	@Override
	public String toString() {
		return java.util.Arrays.toString(tree);
	}

	public static void main(String[] args) {
		// 元素0作为占位符，数组从索引1开始
		/**
		 * 0 1 2 2 4
		 * 0 1 3 2 4
		 * 0 1
		 *
		 *
		 */
		long[] values = {0,1,2,2,4};
		FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(values);
		System.out.println(Arrays.toString(ft.tree));

		// 9, [1, 4] 之间的元素之和，O(log(n))
		ft.sum(1, 4);
		// 索引3处 加 1
		ft.add(3, 1);

		// 10, [1, 4] 之间的元素之和
		ft.sum(1, 4);
		// 索引4处的值设置为0
		ft.set(4, 0);
		// 6, [1, 4] 之间的元素之和
		ft.sum(1, 4);
		// 2, 获取索引2处的值，等价于 sum(2, 2)
		ft.get(2);
	}

}