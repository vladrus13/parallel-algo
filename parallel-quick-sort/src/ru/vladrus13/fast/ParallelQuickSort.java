package ru.vladrus13.fast;

import ru.vladrus13.slow.SlowQuickSort;
import ru.vladrus13.sort.QuickSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelQuickSort extends QuickSort {

    private final int BLOCK = 100;
    private final QuickSort stupidSort = new SlowQuickSort();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);

    @Override
    public void before() {
    }

    @Override
    public void after() {
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sort(int[] a, int l, int r) {
        if (r - l < BLOCK) {
            stupidSort.sort(a, l, r);
        } else {
            int median = a[random.nextInt(r - l) + l];
            Pair part = partition(a, l, r, median);
            executorService.submit(() -> sort(a, l, part.a + 1));
            executorService.submit(() -> sort(a, part.b, r));
        }
    }
}
