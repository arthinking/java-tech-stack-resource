package com.itzhai.algorithm.datastructures.fenwicktree;

import java.util.Arrays;

/**
 * 区间更新，单点查找 Fenwick Tree
 *
 * Created by arthinking on 21/4/2020.
 */
public class FenwickTreeRangeUpdatePointQuery {

	/**
	 * 存储 Fenwick tree 值的数组的大小
	 */
	final int N;

	/**
	 * 这个数组包含首次创建时原始的 Fenwick tree 范围值
	 */
	private long[] originalTree;

	/**
	 * 这个输出包含已更新的范围值
	 */
	private long[] currentTree;

	/**
	 * 通过数组构造一个 Fenwick tree，数组从下表1开始
	 * @param values
	 */
	public FenwickTreeRangeUpdatePointQuery(long[] values) {

		if (values == null) {
			throw new IllegalArgumentException("Values array cannot be null!");
		}

		N = values.length;
		values[0] = 0L;

		// 为了避免直接操纵原数组，破坏了其所有原始内容，我们复制一个values数组
		long[] fenwickTree = values.clone();

		for (int i = 1; i < N; i++) {
			int parent = i + lsb(i);
			if (parent < N) {
				fenwickTree[parent] += fenwickTree[i];
			}
		}

		originalTree = fenwickTree;
		currentTree = fenwickTree.clone();
	}

	/**
	 * 使用val值更新 [left, right] 区间，O(log(n))复杂度
	 * @param left
	 * @param right
	 * @param val
	 */
	public void updateRange(int left, int right, long val) {
		add(left, +val);
		add(right + 1, -val);
	}

	/**
	 * 把索引i的值更新为v，O(log(n))
	 * @param i
	 * @param v
	 */
	private void add(int i, long v) {
		while (i < N) {
			currentTree[i] += v;
			i += lsb(i);
		}
	}

	/**
	 * 获取特定索引处的值。此方法背后的逻辑与在Fenwick树中查找前面总和prefixSum()相同，
	 * 不同之处在于，您需要获取当前树与原始树之间的差取作为i的值。
	 * @param i
	 * @return
	 */
	public long get(int i) {
		return prefixSum(i, currentTree) - prefixSum(i - 1, originalTree);
	}

	/**
	 * 计算 [1, i] 区间元素之和, O(log(n))
	 */
	private long prefixSum(int i, long[] tree) {
		long sum = 0L;
		while (i != 0) {
			sum += tree[i];
			i &= ~lsb(i); // 等价于 i -= lsb(i);
		}
		return sum;
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

	public static void main(String[] args) {
		long[] values = {0,+1,-2,+3,-4,+5,-6};
		FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
		System.out.println(Arrays.toString(ft.currentTree));

		// [1, 4] 区间加10，O(log(n))
		ft.updateRange(1, 4, 10);
		ft.get(1); // 11
		ft.get(4); // 6
		ft.get(5); // 5
		System.out.println(Arrays.toString(ft.currentTree));

		// [1, 4] 区间 加 -20，O(log(n))
		ft.updateRange(3, 6, -20);
		ft.get(3); // -7
		ft.get(4); // -14
		ft.get(5); // -15
	}

}
