package com.itzhai.algorithm.lt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they append up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * 说明：暴力破解简单，利用hash结构巧用空间换时间执行速度更快。
 *
 * Created by arthinking on 9/3/2020.
 */
public class Q1_TwoSum {

	public static void main(String[] args) {
		Q1_TwoSum test = new Q1_TwoSum();
		int[] arr = {1, 3, 3, 8};
		int[] result = test.twoSum4(arr, 6);
		System.out.println(Arrays.toString(result));
	}

	/**
	 * 方法一：暴力法，不用空间换时间，而是时间换空间
	 * 暴力法很简单，遍历每个元素 xx，并查找是否存在一个值与 target - xtarget−x 相等的目标元素。
	 *
	 * 时间复杂度：O(n^2)
	 *
	 * 空间复杂度：O(1)
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					result[0]=i;
					result[1]=j;
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * 两遍hash表，通过一个临时的hash表，空间换时间
	 * 问题：一旦出现hash冲突，查找用时可能会退化到 O(n)，需要仔细挑选哈希函数。
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum2(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement) && map.get(complement) != i) {
				return new int[] { i, map.get(complement) };
			}
		}
		throw new IllegalArgumentException("No two sum solution");
	}

	/**
	 * 一遍hash表，优化，一遍处理完毕
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum3(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)) {
				return new int[] { map.get(complement), i };
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}

	/**
	 * hash存储，通过存储补数的方式，进一步缩减一半的时间
	 *
	 * 问题：[1,3,3,8] 6 不行
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum4(int[] nums, int target) {
		int[] indexs = new int[2];

		// 建立k-v ，一一对应的哈希表
		HashMap<Integer,Integer> hash = new HashMap<>();
		for(int i = 0; i < nums.length; i++){
			if(hash.containsKey(nums[i])){
				indexs[0] = i;
				indexs[1] = hash.get(nums[i]);
				return indexs;
			}
			// 将数据存入 key为补数 ，value为下标
			hash.put(target-nums[i],i);
		}
		return indexs;
	}

}
