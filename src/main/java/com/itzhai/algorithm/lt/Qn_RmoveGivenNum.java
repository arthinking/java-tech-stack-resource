package com.itzhai.algorithm.lt;

/**
 * description
 *
 * Created by arthinking on 20/3/2020.
 */
public class Qn_RmoveGivenNum {

	public static void main(String[] args) {
		Ring<Integer> ring = new Ring<>();
		for (int i = 1; i <= 100; i++) {
			ring.append(i);
		}
		System.out.println(ring.kill(7));
	}
}

class Ring<E> {

	private transient int size = 0;
	private transient Node<E> first;
	private transient Node<E> last;

	E kill(int value) {
		if (size == 0) {return null;}
		if (size == 1) {return first.value;}

		Node<E> currentNode = first;
		int count = 1;
		while (size > 1) {
			final Node<E> preNode = currentNode;
			currentNode = currentNode.next == null ? first : currentNode.next;
			count ++;
			if (count % value == 0) {
				removeItem(currentNode, preNode);
			}
		}
		return first.value;
	}

	/**
	 * 移除元素
	 * @param node 待移除的节点
	 * @param preNode 待移除节点的前一个节点
	 */
	private void removeItem(final Node<E> node, final Node<E> preNode) {
		if(null == node) {
			return;
		}
		if (node == first) {
			first = node.next;
		} else {
			preNode.next = node.next;
		}
		if (null == node.next) {
			last = preNode;
		}
		System.out.println("remove node " + node.value);
		size--;
	}

	void append(E e) {
		final Node<E> l = last;
		final Node<E> newNode = new Node<>(e, null);
		last = newNode;
		if (l == null) { first = newNode; }
		else { l.next = newNode; }
		size ++;
	}
}

class Node<E> {
	E value;
	Node<E> next;

	Node(E element, Node<E> next) {
		this.value = element;
		this.next = next;
	}
}