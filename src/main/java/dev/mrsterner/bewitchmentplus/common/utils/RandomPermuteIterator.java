package dev.mrsterner.bewitchmentplus.common.utils;

import java.util.Enumeration;
import java.util.Random;

public class RandomPermuteIterator implements Enumeration<Integer> {
    int c = 1013904223, a = 1664525;
    int seed, N, m, next;
    boolean hasNext = true;

    /**
     *
     * @param N ceiling of random permutator
     * @throws Exception
     */
    public RandomPermuteIterator(int N) throws Exception {
        if (N <= 0 || N > Math.pow(2, 62)) throw new Exception("Unsupported size: " + N);
        this.N = N;
        m = (int) Math.pow(2, Math.ceil(Math.log(N) / Math.log(2)));
        next = seed = new Random().nextInt(N);
    }



    @Override
    public boolean hasMoreElements() {
        return hasNext;
    }

    @Override
    public Integer nextElement() {
        next = (a * next + c) % m;
        while (next >= N) next = (a * next + c) % m;
        if (next == seed) hasNext = false;
        return  next;
    }
}
