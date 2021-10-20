package ru.vladrus13.slow;

import ru.vladrus13.sort.QuickSort;

public class SlowQuickSort extends QuickSort {

    @Override
    public void sort(int[] a, int l, int r) {
        if (r > l + 1) {
            int median = a[random.nextInt(r - l) + l];
            Pair part = partition(a, l, r, median);
            sort(a, l, part.a + 1);
            sort(a, part.b, r);
        }
    }
}
