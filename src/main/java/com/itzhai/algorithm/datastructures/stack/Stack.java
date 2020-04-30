package com.itzhai.algorithm.datastructures.stack;

import java.util.LinkedList;

/**
 * 使用链表创建栈
 * Created by arthinking on 26/4/2020.
 */
public class Stack<T> implements Iterable<T> {

    private LinkedList<T> list = new LinkedList<>();

    public Stack() {}

    /**
     * 通过一个初始节点创建一个栈
     * @param firstElem
     */
    public Stack(T firstElem) {
        push(firstElem);
    }

    /**
     * 返回栈的元素个数
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * 判断栈是否为空
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 往栈push一个元素
     * @param elem
     */
    public void push(T elem) {
        list.addLast(elem);
    }

    /**
     * 从栈中pop一个元素,如果为空则抛异常
     * @return
     */
    public T pop() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return list.removeLast();
    }

    /**
     * 获取栈顶元素,不做移除操作,如果为空则抛异常
     * @return
     */
    public T peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return list.peekLast();
    }

    /**
     * 返回迭代器,用于遍历栈
     * @return
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}