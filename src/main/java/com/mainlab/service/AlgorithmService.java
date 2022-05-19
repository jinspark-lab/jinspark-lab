package com.mainlab.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlgorithmService {

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> countMap = new LinkedHashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        List<int[]> list = new ArrayList<>();
        for (var key : countMap.keySet()) {
            list.add(new int[]{key, countMap.get(key)});
        }
        Collections.sort(list, (o1, o2) -> o2[1] == o1[1] ? o1[0] - o2[0] : o2[1] - o1[1]);
        return list.stream().limit(k).map(a -> a[0]).mapToInt(Integer::intValue).toArray();
    }

    public int maxProfit(int[] prices) {
        // [7,1,5,3,6,4]
        // 7
        // 5 - 1, 6 - 3
        int profit = 0;
        int hold = Integer.MIN_VALUE;
        for (int price : prices) {
            profit = Math.max(profit, price + hold);
            hold = Math.max(hold, profit - price);
        }
        return profit;
    }

    private int[] nearestSmallerToRight(int[] nums) {
        // [4,5,2,10,8]
        // [2,2,-1,8,-1]
        int[] solve = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() >= nums[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                solve[i] = -1;
            } else {
                solve[i] = stack.peek();
            }
            stack.push(nums[i]);
        }
        return solve;
    }

    private boolean testRun() {
        // Test : nearestSmallerToRight (NSR)
        boolean test1 = true;
        int[] tc1 = { 4,5,2,10,8};
        int[] assert1 = {2,2,-1,8,-1};
        int[] result1 = nearestSmallerToRight(tc1);
        for (int i=0; i<result1.length; i++) {
            if (assert1[i] != result1[i]) {
                test1 = false;
                break;
            }
        }

        boolean test2 = true;
        int[] tc2 = {1,2,3,4,5};
        int[] assert2 = {-1,-1,-1,-1,-1};
        int[] result2 = nearestSmallerToRight(tc2);
        for (int i=0; i<result2.length; i++) {
            if (assert2[i] != result2[i]) {
                test2 = false;
                break;
            }
        }

        boolean test3 = true;
        int[] tc3 = {2,2,1,3,1};
        int[] assert3 = {1,1,-1,1,-1};
        int[] result3 = nearestSmallerToRight(tc3);
        for (int i=0; i<result3.length; i++) {
            if (assert3[i] != result3[i]) {
                test3 = false;
                break;
            }
        }

        return test1 && test2 && test3;
    }

    public void run() {
//        int[] solve = sortedSquares(nums);
//        int[] tc = {1,1,1,2,2,3};
//        int[] tc = {0,1,2,2,2,2,2,3,4,4,4};
//        removeDuplicates(tc);

        List<Integer> ranked = new ArrayList<>(List.of(new Integer[]{100, 50, 40, 20, 10}));
        List<Integer> player = new ArrayList<>(List.of(new Integer[]{5, 25, 50, 120}));

//        findAnagrams("cbaebabacd", "abc");
//        findMaxLength(new int[]{0,1,1,0,0,0,1});

//        int[] pushed = {1,2,3,4,5};
//        int[] popped = {4,5,3,2,1};
//        validateStackSequences(pushed, popped);

        int[] customers = {1,0,1,2,1,1,7,5};
        int[] grumpy = {0,1,0,1,0,1,0,1};
//        maxSatisfied(customers, grumpy, 3);

//        int[] tc = {1,2,3,4,5,6,1};
//        int[] tc = {2, 2, 2};
//        int[] tc = {9,7,7,9,7,7,9};
//        int[] tc = {74,64,50,30};
//        int[] tc = {11,49,100,20,86,29};
//        maxScore(tc, 4);
//        testRun();

//        String tc = "bcabc";
//        removeDuplicateLetters(tc);

        System.out.println("DONE");
    }
}
