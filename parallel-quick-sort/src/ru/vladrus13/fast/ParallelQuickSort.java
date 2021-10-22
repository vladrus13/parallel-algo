package ru.vladrus13.fast;

import ru.vladrus13.slow.SlowQuickSort;
import ru.vladrus13.sort.QuickSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends QuickSort {

    public class ParallelQuickSortTask extends RecursiveAction {

        private final int[] a;
        private final int l;
        private final int r;

        public ParallelQuickSortTask(int[] a, int l, int r) {
            this.a = a;
            this.l = l;
            this.r = r;
        }

        @Override
        protected void compute() {
            if (r - l < BLOCK) {
                stupidSort.sort(a, l, r);
            } else {
                int median = a[random.nextInt(r - l) + l];
                Pair part = partition(a, l, r, median);
                ParallelQuickSortTask task1 = new ParallelQuickSortTask(a, l, part.a + 1);
                task1.fork();
                ParallelQuickSortTask task2 = new ParallelQuickSortTask(a, part.b, r);
                task2.fork();
                task1.join();
                task2.join();
            }
        }
    }

    private final int PARTITION_BLOCK = 100;

    private void partitionFor(int[] a, int al, int ar, int[] from, int l, int r) {
        if (r - l < PARTITION_BLOCK) {
            System.arraycopy(from, l, a, al, ar);
        } else {
            int count = (ar - al) / 2;
            int am = al + count;
            int m = l + count;
            RecursiveAction task1 = new RecursiveAction() {
                @Override
                protected void compute() {
                    partitionFor(a, al, am, from, l, m);
                }
            };
            RecursiveAction task2 = new RecursiveAction() {
                @Override
                protected void compute() {
                    partitionFor(a, am + 1, ar, from, m + 1, r);
                }
            };
            task1.fork();
            task2.fork();
            task1.join();
            task2.join();
        }
    }

    @Override
    protected Pair partition(int[] a, int l, int r, int median) {
        int[] leftest = new int[r - l];
        int[] middllest = new int[r - l];
        int[] rightest = new int[r - l];
        final int[] leftIt = {0};
        final int[] middleIt = {0};
        final int[] rightIt = {0};
        RecursiveAction task1 = new RecursiveAction() {
            @Override
            protected void compute() {
                for (int i = l; i < r; i++) {
                    if (a[i] < median) {
                        leftest[leftIt[0]] = a[i];
                        leftIt[0]++;
                    }
                }
            }
        };
        task1.fork();
        RecursiveAction task2 = new RecursiveAction() {
            @Override
            protected void compute() {
                for (int i = l; i < r; i++) {
                    if (a[i] == median) {
                        middllest[middleIt[0]] = a[i];
                        middleIt[0]++;
                    }
                }
            }
        };
        task2.fork();
        RecursiveAction task3 = new RecursiveAction() {
            @Override
            protected void compute() {
                for (int i = l; i < r; i++) {
                    if (a[i] > median) {
                        rightest[rightIt[0]] = a[i];
                        rightIt[0]++;
                    }
                }
            }
        };
        task3.fork();

        task1.join();
        task2.join();
        task3.join();
        RecursiveAction task4 = new RecursiveAction() {
            @Override
            protected void compute() {
                int it = l;
                for (int i = 0; i < leftIt[0]; i++) {
                    a[it] = leftest[i];
                    it++;
                }
            }
        };
        task4.fork();
        RecursiveAction task5 = new RecursiveAction() {
            @Override
            protected void compute() {
                int it = l + leftIt[0];
                for (int i = 0; i < middleIt[0]; i++) {
                    a[it] = middllest[i];
                    it++;
                }
            }
        };
        task5.fork();
        RecursiveAction task6 = new RecursiveAction() {
            @Override
            protected void compute() {
                int it = l + leftIt[0] + middleIt[0];
                for (int i = 0; i < rightIt[0]; i++) {
                    a[it] = rightest[i];
                    it++;
                }
            }
        };
        task6.fork();
        task4.join();
        task5.join();
        task6.join();
        return new Pair(l + leftIt[0] - 1, l + leftIt[0] + middleIt[0] - 1);
    }

    private final int BLOCK = 8;
    private final QuickSort stupidSort = new SlowQuickSort();

    @Override
    public void before() {
    }

    @Override
    public void after() {
    }

    @Override
    public void sort(int[] a, int l, int r) {
        if (r - l < BLOCK) {
            stupidSort.sort(a, l, r);
        } else {
            ParallelQuickSortTask task = new ParallelQuickSortTask(a, l, r);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(task);
        }
    }
}
