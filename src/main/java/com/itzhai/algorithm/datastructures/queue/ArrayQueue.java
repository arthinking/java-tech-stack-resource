package com.itzhai.algorithm.datastructures.queue;

/**
 * 基于Array实现的队列
 *
 * Created by arthinking on 13/4/2020.
 */
public class ArrayQueue {

	private Integer[] array;

	/**
	 * 队列的头指针，尾指针，以及大小
	 */
	private int front, end, size, used;

	public ArrayQueue(int size) {
		// 初始化队列的头尾指针指向0索引
		front = end = 0;
		//
		this.size = size;
		array = new Integer[this.size];
	}

	public Integer peek() {
		return array[front];
	}

	public int size() {
		if (end < front) {
			return end + size - front;
		}
		return end - front;
	}

	public void enqueue(int value) {
		if (used == size) {
			throw new RuntimeException("队列长度不足");
		}
		array[end] = value;
		used ++;
		if (++end == size) {
			end = 0;
		}
	}

	public Integer dequeue() {
		if (used == 0) {
			throw new RuntimeException("队列中暂无元素");
		}
		Integer retValue = array[front];
		array[front] = null;
		used --;
		if(++front == size) {
			front = 0;
		}
		return retValue;
	}

	public boolean isEmpty() {
		return used == 0;
	}

	public static void main(String[] args) {
		ArrayQueue queue = new ArrayQueue(5);
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);
		System.out.println("队列大小：" + queue.size());
		for (int i = 0; i < 4; i++) {
			System.out.println(queue.dequeue());
		}
		System.out.println("队列大小：" + queue.size());
		System.out.println("队列是否为空：" + queue.isEmpty());
		queue.enqueue(1);
		for (int i = 0; i < 3; i++) {
			System.out.println(queue.dequeue());
		}
		System.out.println("队列大小：" + queue.size());
		System.out.println("队列是否为空：" + queue.isEmpty());
	}

}
