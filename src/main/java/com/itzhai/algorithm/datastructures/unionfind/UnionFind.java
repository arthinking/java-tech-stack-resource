package com.itzhai.algorithm.datastructures.unionfind;

/**
 * 并查集
 *
 * 本例子使用帮派的术语解释并查集, 相关概念:
 * [超有爱的并查集~](https://blog.csdn.net/niushuai666/article/details/6662911)
 *
 * 例子来源: https://github.com/learning-resource/data-structures
 *
 * Created by arthinking on 26/4/2020.
 */
public class UnionFind {

    /**
     * 并查集中元素的数量
     */
    private int size;

    /**
     * 用于跟踪组件的数量
     */
    private int[] sizeArr;

    // parentIdArr[i] points to the parent of i, if parentIdArr[i] = i then i is a root node
    /**
     * parentIdArr[i]指向大佬的索引i, 如果id[i]=i,那么i就是大佬节点了
     */
    private int[] parentIdArr;

    /**
     * 并查集中的组件数量
     */
    private int numComponents;

    public UnionFind(int size) {

        if (size <= 0) {
            throw new IllegalArgumentException("Size <= 0 is not allowed");
        }
        // 初始化
        this.size = numComponents = size;
        sizeArr = new int[size];
        parentIdArr = new int[size];
        // 初始化所有元素自立门派,视自己为掌门人
        for (int i = 0; i < size; i++) {
            parentIdArr[i] = i;
            sizeArr[i] = 1;
        }
    }

    /**
     * 判断p的掌门人是谁
     * @param p
     * @return
     */
    public int find(int p) {

        // 找到帮派的掌门人
        int root = p;
        while (root != parentIdArr[root]) {
            root = parentIdArr[root];
        }

        /**
         * 进行路径压缩: 压缩p到根之间的路径
         */
        while (p != root) {
            int next = parentIdArr[p];
            parentIdArr[p] = root;
            p = next;
        }

        return root;
    }

    /**
     * 判断 p 和 q 是不是在同一个帮派
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 返回p所在帮派的人数
     * @param p
     * @return
     */
    public int componentSize(int p) {
        return sizeArr[find(p)];
    }

    /**
     * 返回并查集中所有的元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 返回当前帮派数量
     * @return
     */
    public int components() {
        return numComponents;
    }

    /**
     * 合并p和q所处的两个帮派
     * @param p
     * @param q
     */
    public void unify(int p, int q) {

        int root1 = find(p);
        int root2 = find(q);

        // 已经是同一个帮派
        if (root1 == root2) return;

        // Merge smaller component/set into the larger one.
        /**
         * 小帮派合并到大帮派中
         */
        if (sizeArr[root1] < sizeArr[root2]) {
            sizeArr[root2] += sizeArr[root1];
            parentIdArr[root1] = root2;
        } else {
            sizeArr[root1] += sizeArr[root2];
            parentIdArr[root2] = root1;
        }

        // 帮派合并了,帮派数量减一
        numComponents--;
    }
}
