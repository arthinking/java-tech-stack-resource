package com.itzhai.algorithm.datastructures.priorityqueue;

import java.util.*;

/**
 * 最小堆实现的优先队列，优化了删除方法
 *
 * Created by arthinking on 16/4/2020.
 */
public class BinaryHeapQuickRemovals<T extends Comparable<T>> extends BinaryHeap<T> {

	/**
	 * 存储具体值在优先级队列中的索引，通过使用该map，
	 * 让我的remove操作时间复杂度变为了 O(log(n))（为对应swim操作的复杂度），contains操作变为了O(1)
	 */
	private Map<T, TreeSet<Integer>> map = new HashMap<>();

	public BinaryHeapQuickRemovals(int size) {
		super(size);
	}

	public BinaryHeapQuickRemovals(T[] elements) {
		heapSize = heapCapacity = elements.length;
		this.heap = new ArrayList<>(heapCapacity);
		// 把数组放入ArrayList中
		for (int i = 0; i < heapSize; i++) {
			heap.add(elements[i]);
			mapAdd(elements[i], i);
		}
		// 依次遍历处理数组前面一半元素，构造小顶堆：如果元素比较大，就一直下沉，这样前面一半的元素处理完后，就构造完了小顶堆
		for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) {
			sink(i);
		}
	}

	@Override
	public void clear() {
		super.clear();
		map.clear();
	}


	/**
	 * 判断某个元素是否在堆中，O(1)
	 */
	@Override
	public boolean contains(T elem) {
		if (elem == null) {
			return false;
		}
		return map.containsKey(elem);
	}

	/**
	 * 添加一个元素到优先级队列中
	 * @param elem
	 */
	@Override
	public void add(T elem) {
		super.add(elem);
		mapAdd(elem, heapSize-1);
	}

	@Override
	protected void swap(int i, int j) {

		T i_elem = heap.get(i);
		T j_elem = heap.get(j);

		heap.set(i, j_elem);
		heap.set(j, i_elem);

		mapSwap(i_elem, j_elem, i, j);
	}

	/**
	 * O(log(n))
	 * @param element
	 * @return
	 */
	@Override
	public boolean remove(T element) {

		if (element == null) {
			return false;
		}

		// 通过map查找移, O(log(n))
		Integer index = mapGet(element);
		if (index != null) {
			removeAt(index);
		}
		return index != null;
	}

	/**
	 * 移除i索引对应的元素, O(log(n))
	 * @param i
	 * @return
	 */
	@Override
	protected T removeAt(int i) {
		T removedData = super.removeAt(i);
		mapRemove(removedData, heapSize);
		return removedData;
	}

	/**
	 * 把元素的值及其索引记录到map中
	 */
	private void mapAdd(T value, int index) {
		TreeSet<Integer> set = map.get(value);
		if (set == null) {
			set = new TreeSet<>();
			set.add(index);
			map.put(value, set);
		} else {
			set.add(index);
		}
	}

	/**
	 * 移除特定value的index索引, O(log(n))
	 * @param value
	 * @param index
	 */
	private void mapRemove(T value, int index) {
		TreeSet<Integer> set = map.get(value);
		// TreeSets 的移除操作会花费 O(log(n)) 时间复杂度
		set.remove(index);
		if (set.size() == 0) {
			map.remove(value);
		}
	}

	private Integer mapGet(T value) {
		TreeSet<Integer> set = map.get(value);
		if (set != null) {
			return set.last();
		}
		return null;
	}

	/**
	 * 交换map中两个value对应的索引
	 * @param val1
	 * @param val2
	 * @param val1Index
	 * @param val2Index
	 */
	private void mapSwap(T val1, T val2, int val1Index, int val2Index) {

		Set<Integer> set1 = map.get(val1);
		Set<Integer> set2 = map.get(val2);

		set1.remove(val1Index);
		set2.remove(val2Index);

		set1.add(val2Index);
		set2.add(val1Index);
	}

	public static void main(String[] args) {
		Integer[] arr = {20, 10, 1, 4, 6, 8, 12, 21, 9, 8};
		BinaryHeapQuickRemovals<Integer> heap = new BinaryHeapQuickRemovals<>(arr);
		/**
		 *          1
		 *      4        8
		 *    9   6   20   12
		 *  21 10
		 *
		 */
		System.out.println(heap);
		heap.poll();
		System.out.println(heap);
		System.out.println(heap.isMinHeap(1));
		System.out.println((heap).map);

	}

}
