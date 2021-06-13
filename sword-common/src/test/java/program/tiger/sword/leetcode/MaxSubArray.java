package program.tiger.sword.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MaxSubArray {


    public static int solution(int [] arr){
        int isum=arr[0],jsum=0,max=0;
        List<Integer> sumList = new ArrayList<Integer>(16);
        for (int i=1;i<arr.length-1;i++){
            isum  +=arr[i];
            sumList.add(isum);
            jsum = arr[i];
            for (int j=i+1;j<arr.length;j++){
                jsum+=arr[j];
                sumList.add(jsum);
            }
        }
        for (Integer i : sumList){
            if (i>max){
                max = i;
            }
        }
        return max;
    }


    @Test
    public void testSolution(){
        int[] arr = new int[]{1,2,3,4,5,-1};
        System.out.println(solution(arr));
    }
}
