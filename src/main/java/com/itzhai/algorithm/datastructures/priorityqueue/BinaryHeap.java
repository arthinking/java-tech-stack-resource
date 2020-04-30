package com.itzhai.algorithm.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于小顶堆实现的优先级队列
 *
 * Created by arthinking on 16/4/2020.
 */
public class BinaryHeap<T extends Comparable<T>> {

	/**
	 * 队列当前大小(元素个数)
	 */
	int heapSize = 0;

	/**
	 * 队列容量
	 */
	int heapCapacity = 0;

	/**
	 * 小顶堆存放于数组中，使用ArrayList，不自己实现扩容
	 */
	List<T> heap = null;

	public BinaryHeap() {}

	/**
	 * 构造函数
	 * @param size
	 */
	public BinaryHeap(int size) {
		this.heap = new ArrayList<>(size);
	}

	/**
	 * 通过数组初始化小顶堆
	 * @param elements
	 */
	public BinaryHeap(T[] elements) {
		heapSize = heapCapacity = elements.length;
		this.heap = new ArrayList<>(heapCapacity);
		// 把数组放入ArrayList中
		for (int i = 0; i < heapSize; i++) {
			heap.add(elements[i]);
		}
		// 依次遍历处理数组前面一半元素，构造小顶堆：如果元素比较大，就一直下沉，这样前面一半的元素处理完后，就构造完了小顶堆
		for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) {
			sink(i);
		}

	}

	/**
	 * 判断某一个元素是否在队列中，O(n)
	 * @param elem
	 * @return
	 */
	public boolean contains(T elem) {
		// Linear scan to check containment
		for (int i = 0; i < heapSize; i++) {
			if (heap.get(i).equals(elem)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}

	public void clear() {
		for (int i = 0; i < heapSize; i++) {
			heap.set(i, null);
		}
		heapSize = 0;
	}

	/**
	 * 添加一个元素到优先级队列中
	 * @param elem
	 */
	public void add(T elem) {

		if (elem == null) {
			throw new IllegalArgumentException();
		}
		// 先加到heap对应的list中
		if (heapSize < heapCapacity) {
			heap.set(heapSize, elem);
		} else {
			heap.add(elem);
			heapCapacity++;
		}
		// 加入的元素执行上浮操作
		swim(heapSize);
		heapSize++;
	}

	/**
	 * 获取优先级队列最小的元素，如果队列为空，则返回null
	 * @return
	 */
	public T peek() {
		if (isEmpty()) {
			return null;
		}
		return heap.get(0);
	}

	/**
	 * 从优先级队列中移除最小的元素
	 * @return
	 */
	public T poll() {
		// 时间复杂度 O(log(n))
		return removeAt(0);
	}

	/**
	 * 判断当前堆是否最小堆
	 * @return
	 */
	public boolean isMinHeap(int i) {
		if (i >= heapSize) {
			return true;
		}
		int left = i * 2 + 1;
		int right = i * 2 + 2;
		if (left < heapSize && !less(i, left)) {
			return false;
		}
		if (right < heapSize && !less(i, right)) {
			return false;
		}
		return isMinHeap(left) && isMinHeap(right);
	}

	/**
	 * 移除特定的元素
	 * @param element
	 * @return
	 */
	public boolean remove(T element) {
		if (element == null) {
			return false;
		}
		// 线性查找移除, O(n)
		for (int i = 0; i < heapSize; i++) {
			if (element.equals(heap.get(i))) {
				removeAt(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * 移除第i个元素
	 * @param i
	 * @return
	 */
	protected T removeAt(int i) {
		if (isEmpty()) {
			return null;
		}
		heapSize --;
		T removeItem = heap.get(i);
		// 待删除的元素和最后一个元素互换位置
		swap(i, heapSize);
		// 最后一个元素置空
		heap.set(heapSize, null);
		if (i == heapSize) {
			return removeItem;
		}
		T item = heap.get(i);
		// 此时第i个元素已经替换为了原来的最后一个元素
		// 现在让第i个元素做下沉操作
		sink(i);
		// 如果元素没有下沉，那么尝试做上浮操作
		if (heap.get(i).equals(item)) {
			swim(i);
		}
		return removeItem;

	}

	/**
	 * 小元素上浮
	 * @param i
	 */
	private void swim(int i) {
		// 根据父节点比较，如果父节点比较大，则i元素上浮
		int parent = (i - 1) / 2;
		while (i > 0 && less(i, parent)) {
			swap(parent, i);
			i = parent;
			parent = (i - 1) / 2;
		}
	}

	/**
	 * 大元素下沉
	 * @param i
	 */
	protected void sink(int i) {
		while(true) {
			int left = i*2 + 1;
			int right = i*2 + 2;
			// 先假设左子节点比较小
			int smaller = left;
			// 查找较小的子节点，判断右节点是否比较小
			if (right < heapSize && less(right, left)) {
				smaller = right;
			}
			// 如果i元素是最后一个元素，或者i元素都小于两个子节点了，那么表示处理完毕
			if (left >= heapSize || less(i, smaller)) {
				break;
			}
			// 否则，i下沉，让较小的元素上浮
			swap(i, smaller);
			// i下标变为了smaller，继续循环与左右子节点对比
			i = smaller;
		}
	}

	/**
	 * 判断 x 是否小于 y
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean less(int x, int y) {
		T ex = heap.get(x);
		T ey = heap.get(y);
		return ex.compareTo(ey) <= 0;
	}

	/**
	 * 交换x y两个下标对应的元素
	 * @param x
	 * @param y
	 */
	protected void swap(int x, int y) {
		T ex = heap.get(x);
		T ey = heap.get(y);
		heap.set(x, ey);
		heap.set(y, ex);
	}

	@Override
	public String toString() {
		return heap.toString();
	}

	public static void main(String[] args) {
		Integer[] arr = {20, 10, 1, 4, 6, 8, 12, 21, 9};
		BinaryHeap<Integer> heap = new BinaryHeap<>(arr);
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

	}

}
