package ru.vladrus13;

import ru.vladrus13.sort.QuickSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sorter {

    private static final Random random = new Random();

    public static int[] getArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static int[] getArray(int[] a) {
        return a.clone();
    }

    public static boolean check(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }

    public static boolean test(List<QuickSort> sorters, int[] lengths) {
        for (int length : lengths) {
            int[] test = getArray(length);
            for (QuickSort sorter : sorters) {
                int[] copy = getArray(test);
                sorter.sort(copy);
                if (!check(copy)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<long[]> timeTest(List<QuickSort> sorters, int[] lengths) {
        List<long[]> times = new ArrayList<>();
        int[][] tests = new int[lengths.length][];
        for (int i = 0; i < lengths.length; i++) {
            tests[i] = getArray(lengths[i]);
        }
        for (QuickSort sorter : sorters) {
            long[] result = new long[lengths.length];
            for (int i = 0; i < lengths.length; i++) {
                int[] test = getArray(tests[i]);
                long start = System.currentTimeMillis();
                sorter.sort(test);
                long finish = System.currentTimeMillis();
                result[i] = finish - start;
            }
            times.add(result);
        }
        return times;
    }
}
