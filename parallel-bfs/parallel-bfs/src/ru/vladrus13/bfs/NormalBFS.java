package ru.vladrus13.bfs;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class NormalBFS {
    public static int run(int[][] graph, int start, int finish) {
        if (start == finish) return 0;
        Collection<Integer> used = new HashSet<>();
        Queue<Distance> queue = new LinkedList<>();
        used.add(start);
        queue.add(new Distance(start, 0));
        while (!queue.isEmpty()) {
            Distance current = queue.poll();
            for (int child : graph[current.node]) {
                if (child == finish) {
                    return current.distance + 1;
                }
                if (!used.contains(child)) {
                    used.add(child);
                    queue.add(new Distance(child, current.distance + 1));
                }
            }
        }
        return -1;
    }

    public static class Distance {
        public final int node;
        public final int distance;

        public Distance(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
