package ru.vladrus13.sort;

import java.util.Random;

public abstract class QuickSort {
    public static class Pair {
        public final int a;
        public final int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    protected final Random random = new Random();

    public void sort(int[] a) {
        before();
        sort(a, 0, a.length);
        after();
    }

    public void before() {}

    public void after() {}

    public abstract void sort(int[] a, int l, int r);

    protected Pair partition(int[] a, int l, int r, int median) {
        int[] leftest = new int[r - l + 1];
        int leftIt = 0;
        for (int i = l; i < r; i++) {
            if (a[i] < median) {
                leftest[leftIt] = a[i];
                leftIt++;
            }
        }
        int[] middllest = new int[r - l + 1];
        int middleIt = 0;
        for (int i = l; i < r; i++) {
            if (a[i] == median) {
                middllest[middleIt] = a[i];
                middleIt++;
            }
        }
        int[] rightest = new int[r - l + 1];
        int rightIt = 0;
        for (int i = l; i < r; i++) {
            if (a[i] > median) {
                rightest[rightIt] = a[i];
                rightIt++;
            }
        }
        {
            int it = l;
            for (int i = 0; i < leftIt; i++) {
                a[it] = leftest[i];
                it++;
            }
        }
        {
            int it = l + leftIt;
            for (int i = 0; i < middleIt; i++) {
                a[it] = middllest[i];
                it++;
            }
        }
        {
            int it = l + leftIt + middleIt;
            for (int i = 0; i < rightIt; i++) {
                a[it] = rightest[i];
                it++;
            }
        }
        return new Pair(l + leftIt - 1, l + leftIt + middleIt - 1);
    }
}
