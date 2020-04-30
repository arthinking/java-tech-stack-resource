package com.itzhai.algorithm.datastructures.queue;

import java.util.Iterator;

/**
 * 基于链表实现的队列
 *
 * Created by arthinking on 13/4/2020.
 */
public class LinkedListQueue<T> implements Iterable<T> {

	private java.util.LinkedList<T> list = new java.util.LinkedList<>();

	public LinkedListQueue(){}

	public LinkedListQueue(T firstElement){
		offer(firstElement);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public T peek() {
		if (isEmpty()) {
			throw new RuntimeException("队列为空");
		}
		return list.peekFirst();
	}

	public T poll() {
		if (isEmpty()) {
			throw new RuntimeException("队列为空");
		}
		return list.removeFirst();
	}

	private void offer(T element) {
		list.addLast(element);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	public static void main(String[] args) {
		LinkedListQueue<Integer> queue = new LinkedListQueue<>(10);
		queue.offer(12);
		queue.offer(20);
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
