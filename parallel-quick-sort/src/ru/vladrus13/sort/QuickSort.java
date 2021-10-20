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

    private void swap(int[] a, int x, int y) {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    protected Pair partition(int[] a, int l, int r, int median) {
        int it = l;
        for (int i = l; i < r; i++) {
            if (a[i] < median) {
                swap(a, i, it);
                it++;
            }
        }
        int lastLess = it - 1;
        for (int i = it; i < r; i++) {
            if (a[i] == median) {
                swap(a, i, it);
                it++;
            }
        }
        int lastEquals = it - 1;
        return new Pair(lastLess, lastEquals);
    }
}
