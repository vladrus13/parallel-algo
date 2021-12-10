package ru.vladrus13.utils;

import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class ParallelUtils {

    private static final int BLOCK_SIZE = 64;

    public static void parallel_for(int n, Consumer<Integer> task) {
        parallel_for(0, n, task);
    }

    private static void parallel_for(int l, int r, Consumer<Integer> task) {
        if (r - l < BLOCK_SIZE) {
            for (int i = l; i < r; i++) {
                task.accept(i);
            }
        } else {
            int middle = (l + r) / 2;
            RecursiveAction leftTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_for(l, middle, task);
                }
            };
            RecursiveAction rightTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_for(middle, r, task);
                }
            };
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();
        }
    }

    public static void parallel_scan(int n, int[] from, int[] target) {
        int[] tree = new int[2 * n];
        parallel_scan_build_tree(1, 0, n, from, tree);
        parallel_scan_sweep(1, 0, n, from, tree, target, 0);
    }

    private static void parallel_scan_build_tree(int v, int l, int r, int[] from, int[] target) {
        if (r - l < BLOCK_SIZE) {
            int accumulator = 0;
            for (int i = l; i < r; i++) {
                accumulator += from[i];
            }
            target[v] = accumulator;
        } else {
            int middle = (l + r) / 2;
            RecursiveAction leftTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_scan_build_tree(v * 2, l, middle, from, target);
                }
            };
            RecursiveAction rightTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_scan_build_tree(v * 2 + 1, middle, r, from, target);
                }
            };
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();
            target[v] = target[v * 2] + target[v * 2 + 1];
        }
    }

    private static void parallel_scan_sweep(int v, int l, int r, int[] from, int[] tree, int[] target, int accumulator) {
        if (r - l < BLOCK_SIZE) {
            int crack = accumulator;
            for (int i = l; i < r; i++) {
                target[i] = crack;
                crack += from[i];
            }
        } else {
            int middle = (l + r) / 2;
            RecursiveAction leftTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_scan_sweep(v * 2, l, middle, from, tree, target, accumulator);
                }
            };
            RecursiveAction rightTask = new RecursiveAction() {
                @Override
                protected void compute() {
                    parallel_scan_sweep(v * 2 + 1, middle, r, from, tree, target, accumulator + tree[v * 2]);
                }
            };
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();
        }
    }
}
