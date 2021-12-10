package ru.vladrus13.bfs;

import ru.vladrus13.utils.ParallelUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class ParallelBFS {
    public static int run(int[][] graph, int start, int finish) {
        if (start == finish) return 0;
        AtomicInteger[] distance = new AtomicInteger[graph.length];
        ParallelUtils.parallel_for(graph.length, i -> distance[i] = new AtomicInteger(-1));
        distance[start].set(0);
        int[] frontier = new int[1];
        frontier[0] = start;
        while (frontier.length != 0) {
            int[] deg = new int[frontier.length];
            int[] finalFrontier = frontier; // TODO maybe have more efficient
            ParallelUtils.parallel_for(frontier.length, i -> {
                if (finalFrontier[i] == -1) {
                    deg[i] = 0;
                } else {
                    deg[i] = graph[finalFrontier[i]].length;
                }
            });
            int[] starts = new int[frontier.length];
            ParallelUtils.parallel_scan(frontier.length, deg, starts);
            int[] nextFrontier = new int[starts[frontier.length - 1] + deg[frontier.length - 1]];
            ParallelUtils.parallel_for(nextFrontier.length, i -> nextFrontier[i] = -1);
            AtomicInteger realDistance = new AtomicInteger(-1);
            ParallelUtils.parallel_for(frontier.length, i -> {
                int v = finalFrontier[i];
                if (v != -1) {
                    int vDistance = distance[v].get();
                    int startFrontierPosition = starts[i];
                    for (int u : graph[v]) {
                        if (u == finish) {
                            realDistance.compareAndSet(-1, vDistance + 1);
                        }
                        if (distance[u].compareAndSet(-1, vDistance + 1)) {
                            nextFrontier[startFrontierPosition] = u;
                            startFrontierPosition++;
                        }
                    }
                }
            });
            if (realDistance.get() != -1) {
                return realDistance.get();
            }
            frontier = nextFrontier;
        }
        return -1;
    }
}
