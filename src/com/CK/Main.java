package com.CK;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[] words = {"ksqvsyq","ks","kss","czvh","zczpzvdhx","zczpzvh","zczpzvhx","zcpzvh","zczvh","gr","grukmj","ksqvsq","gruj","kssq","ksqsq","grukkmj","grukj","zczpzfvdhx","gru"};
        new Solution().longestStrChain(words);
    }
}

class Solution {
    public int longestStrChain(String[] words) {
        if (words.length == 0)
            return 0;
//        System.out.println(isNext("ks", "kss"));
        Map<Integer, Set<String>> dict = new HashMap<>();
        Map<String, Set<String>> nextMap = new HashMap<>();
        Map<String, Integer> seen = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>((s1, s2) -> s2.length() - s1.length());
        for (String word : words) {
            int len = word.length();
            dict.putIfAbsent(len, new HashSet<>());
            if (dict.get(len).contains(word)) {
                continue;
            }
            dict.get(len).add(word);
            pq.offer(word);
        }
        int res = 1;
        while (!pq.isEmpty()) {
            res = Math.max(res, dfs(dict, seen, nextMap, pq.poll()));
        }
        return res;
    }

    private int dfs(Map<Integer, Set<String>> dict, Map<String, Integer> seen, Map<String, Set<String>> nextMap, String s) {
        if (seen.containsKey(s)) {
            return seen.get(s);
        }

        int res = 1, len = s.length();
        if (!dict.containsKey(len + 1)) {
            seen.put(s, res);
            return res;
        }
        for (String next : dict.get(len + 1)) {
            nextMap.putIfAbsent(s, new HashSet<>());
            if (!nextMap.get(s).contains(next) && isNext(s, next)) {
                nextMap.get(s).add(next);
            }
            if (nextMap.get(s).contains(next)) {
                res = Math.max(res, 1 + dfs(dict, seen, nextMap, next));
            }
        }
        seen.put(s, res);
        return res;
    }

    // b is next of a
    private boolean isNext(String a, String b) {
        boolean skip = true;
        int aItr = 0, bItr = 0;
        while (aItr < a.length() && bItr < b.length()) {
            if (a.charAt(aItr) == b.charAt(bItr)) {
                aItr++;
                bItr++;
                continue;
            }
            if (skip) {
                bItr++;
                skip = false;
            } else
                break;
        }
        return aItr == a.length() && (bItr == b.length() || bItr == b.length() - 1);
    }
}

class Solution {
    public int longestStrChain(String[] words) {
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b)->a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
}