package ru.vladrus13;

import ru.vladrus13.fast.ParallelQuickSort;
import ru.vladrus13.slow.SlowQuickSort;
import ru.vladrus13.sort.QuickSort;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void correctness() {
        ArrayList<QuickSort> sorters = new ArrayList<>();
        sorters.add(new SlowQuickSort());
        sorters.add(new ParallelQuickSort());
        System.out.println(Sorter.test(sorters, new int[]{2, 10, 1000, 10000, 50000, 100000}));
    }

    public static void speed() {
        int[] lengths = new int[]{1, 10, 1000, 10000, 100000, 10000000, 100000000};
        ArrayList<QuickSort> sorters = new ArrayList<>();
        sorters.add(new SlowQuickSort());
        sorters.add(new ParallelQuickSort());
        List<long[]> times = Sorter.timeTest(sorters, lengths);
        for (long[] timesSorter : times) {
            for (long time : timesSorter) {
                System.out.printf("%d ms, ", time);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        correctness();
        speed();
    }
}
