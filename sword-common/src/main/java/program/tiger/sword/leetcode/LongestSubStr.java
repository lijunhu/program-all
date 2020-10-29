package program.tiger.sword.leetcode;


import java.util.HashSet;
import java.util.Set;

/**
 * 最长子串
 *
 * @author TigerLee
 */
public class LongestSubStr {

    public int lengthOfLongestSubstring(String s) {
        Set<Character> charSet = new HashSet<>(16);
        int ri = -1, result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                charSet.remove(s.charAt(i - 1));
            }
            while (ri + 1 < s.length() && !charSet.contains(s.charAt(ri + 1))) {
                charSet.add(s.charAt(ri + 1));
                ++ri;
            }
            result = Math.max(result, ri - i + 1);

        }
        return result;
    }

    public int lengthOfLongestSubstringArr(String s) {
        if (s.length() == 0) {
            return 0;
        }
        //用于记录每个字符
        char[] windows = new char[128];
        //双指针控制窗口大小
        int left = 0, right = 0;
        //记录窗口最大长度
        int maxlength = 0;

        while (right < s.length()) {
            char ch = s.charAt(right);
            windows[ch]++;

            //如果有重复字符则左边窗口一直加，直到剔除重复字符
            while (windows[ch] > 1) {
                char ch1 = s.charAt(left);
                windows[ch1]--;
                left++;
            }
            maxlength = Math.max(right - left + 1, maxlength);
            right++;
        }
        return maxlength;
    }

}
