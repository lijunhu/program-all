package program.tiger.sword.leetcode;

import org.junit.Test;

public class TestLongestSubStr {


    @Test
    public void TestLengthOfLongestSubstring(){
        LongestSubStr longestSubStr = new LongestSubStr();

        int m  = longestSubStr.lengthOfLongestSubstringArr("pwwkew");
        System.out.println(m);
    }
}
