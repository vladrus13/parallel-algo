package ru.vladrus13.generators;

public class Bamboo {
    public static int[][] generate(int length) {
        int[][] graph = new int[length][];
        graph[0] = new int[1];
        graph[0][0] = 1;
        graph[length - 1] = new int[1];
        graph[length - 1][0] = length - 2;
        for (int i = 1; i < length - 1; i++) {
            graph[i] = new int[2];
            graph[i][0] = i - 1;
            graph[i][1] = i + 1;
        }
        return graph;
    }
}
