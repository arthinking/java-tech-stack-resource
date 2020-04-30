package com.itzhai.algorithm.lt;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * 关键点：
 * 1 单向链表的遍历
 * 2 字符串的处理
 * 3 构建单向链表
 * 4 哑节点的使用
 * 6 进位追加到后一个节点
 *
 * Created by arthinking on 9/3/2020.
 */
public class Q2_AddTwoNumbers {

	public static void main(String[] args) {
		Q2_AddTwoNumbers test = new Q2_AddTwoNumbers();
		ListNode list1 = test.buildNodeListByNumber(342, true);
		ListNode list2 = test.buildNodeListByNumber(465, true);
		ListNode result = test.addTwoNumbers3(list1, list2);
		System.out.println(test.traversalAndGetNumber(result));

	}

	/**
	 * 时间复杂度：O(\max(m, n))O(max(m,n))，假设 mm 和 nn 分别表示 l1l1 和 l2l2 的长度，上面的算法最多重复 \max(m, n)max(m,n) 次。
	 *
	 * 空间复杂度：O(\max(m, n))O(max(m,n))， 新列表的长度最多为 \max(m,n) + 1max(m,n)+1。
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
		// 哑节点
		ListNode dummyHead = new ListNode(0);
		// 遍历两个单向链表，每个节点相加，并且把结果追加到哑节点的后面
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while (p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			int sum = carry + x + y;
			// 获取进位，累加到下一个节点中
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if (p != null) {
				p = p.next;
			}
			if (q != null) {
				q = q.next;
			}
		}
		if (carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

	/**
	 * 如果链表中的数字不是按逆序存储的呢？例如：
	 *
	 * (3→4→2)+(4→6→5)=8→0→7
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
		ListNode firstNode = new ListNode(0);
		ListNode a = l1, b = l2, currentNode = firstNode;
		while ( a != null || b != null) {
			int x = (a != null) ? a.val:0;
			int y = (b != null) ? b.val:0;
			int sum = x + y;
			int carry = sum / 10;
			if (carry > 0) {
				currentNode.val = currentNode.val + carry;
			}
			currentNode.next = new ListNode(sum % 10);
			currentNode = currentNode.next;
			if (a != null) {
				a = a.next;
			}
			if (b != null) {
				b = b.next;
			}
		}
		if (firstNode.val > 0) {
			return firstNode;
		}
		return firstNode.next;
	}

	/**
	 * 错误解法，节点相加有可能大于10，需要进位
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		long val1 = traversalAndGetNumber(l1);
		long val2 = traversalAndGetNumber(l2);
		long result = val1 + val2;
		return buildNodeListByNumber(result, true);
	}

	/**
	 * 根据数字构建单向链表
	 * @param result
	 * @param isreverse
	 * @return
	 */
	private ListNode buildNodeListByNumber(long result, boolean isreverse) {
		String str = String.valueOf(result);
		ListNode firstNode = new ListNode(0);
		ListNode currentNode = firstNode;
		for (int i=0; i < str.length(); i++) {
			int index = str.length() - 1 - i;
			currentNode.next = new ListNode(Integer.valueOf(str.substring(index, index +1)));
			currentNode = currentNode.next;
		}
		return firstNode.next;
	}

	private Long traversalAndGetNumber(ListNode l1) {
		long result = 0;
		long digits = 1;
		for (ListNode tmp = l1; tmp != null; tmp = tmp.next) {
			if (tmp == null) {
				break;
			}
			result += tmp.val * digits;
			digits = digits * 10;
		}
		return result;
	}
}

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
}