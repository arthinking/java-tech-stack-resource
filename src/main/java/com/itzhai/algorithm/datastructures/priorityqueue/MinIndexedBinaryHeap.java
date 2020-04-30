package com.itzhai.algorithm.datastructures.priorityqueue;

/**
 * Created by arthinking on 27/4/2020.
 *
 * 例子来源: https://github.com/learning-resource/data-structures
 *
 */
public class MinIndexedBinaryHeap<T extends Comparable<T>> extends MinIndexedDHeap<T> {
    public MinIndexedBinaryHeap(int maxSize) {
        super(2, maxSize);
    }
}

