package com.itzhai.algorithm.lt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 *
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 *
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 *
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Created by arthinking on 9/3/2020.
 */
public class Q3_LongestSubstringWithoutRepeatingCharacters {

	public static void main(String[] args) {
		Q3_LongestSubstringWithoutRepeatingCharacters test = new Q3_LongestSubstringWithoutRepeatingCharacters();
		int result = test.test("abcdefghijklmnopqrstuvwxyza");
		System.out.println(result);
	}

	/**
	 * 暴力破解法
	 *
	 * 时间复杂度：O(n^3)
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		int n = s.length();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j <= n; j++) {
				if (allUnique(s, i, j)) {
					ans = Math.max(ans, j - i);
				}
			}

		}

		return ans;
	}

	/**
	 * 滑动窗口法，通过使用HashSet作为滑动窗口
	 * 定义结果，定义双重下标
	 * 定义辅助数据结构
	 * 遍历，判断hashset中是否包含当前字符串，
	 *      如果不包含，则加到set中，并获取的到区间范围；
	 *      如果包含，则移除区间左侧内容，并且区间右移
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring2(String s) {
		int n = s.length();
		Set<Character> set = new HashSet<>();
		int ans = 0, i = 0, j = 0;
		while (i < n && j < n) {
			// try to extend the range [i, j]
			if (!set.contains(s.charAt(j))){
				set.add(s.charAt(j++));
				ans = Math.max(ans, j - i);
			}
			else {
				set.remove(s.charAt(i++));
			}
		}
		return ans;
	}

	public int test(String s) {
		int length = s.length();
		int result = 0;
		int i = 0, j = 0;
		Set<Character> set = new HashSet<>();
		while (i < length && j < length) {
			if(!set.contains(s.charAt(j))) {
				set.add(s.charAt(j));
				j++;
				result = Math.max(result, j - i);
			} else {
				set.remove(s.charAt(i));
				i++;
			}
		}
		return result;
	}


	/**
	 * 优化的滑动窗口，使用HashMap
	 *
	 * 通过定义字符到索引的映射，如果 s[j]s[j] 在 [i, j)[i,j) 范围内有与 j'j′重复的字符，我们不需要逐渐增加 ii 。 我们可以直接跳过 [i，j'][i，j′]
	 * 范围内的所有元素，并将 ii 变为 j' + 1j′ +1。
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring3(String s) {
		int n = s.length(), ans = 0;
		// current index of character
		Map<Character, Integer> map = new HashMap<>(s.length());
		// try to extend the range [i, j]
		for (int j = 0, i = 0; j < n; j++) {
			if (map.containsKey(s.charAt(j))) {
				i = Math.max(map.get(s.charAt(j)), i);
			}
			ans = Math.max(ans, j - i + 1);
			map.put(s.charAt(j), j + 1);
		}
		return ans;
	}

	/**
	 * 优化的滑动窗口，使用数组取代HashMap，针对ASCII 128
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring4(String s) {
		int n = s.length(), ans = 0;
		// current index of character
		int[] index = new int[128];
		// try to extend the range [i, j]
		for (int j = 0, i = 0; j < n; j++) {
			i = Math.max(index[s.charAt(j)], i);
			ans = Math.max(ans, j - i + 1);
			index[s.charAt(j)] = j + 1;
		}
		return ans;
	}

	public boolean allUnique(String s, int start, int end) {
		Set<Character> set = new HashSet<>();
		for (int i = start; i < end; i++) {
			Character ch = s.charAt(i);
			if (set.contains(ch)) {
				return false;
			}
			set.add(ch);
		}
		return true;
	}
}