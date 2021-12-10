package ru.vladrus13.generators;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private static int getNumber(int i, int j, int m) {
        return i * m + j;
    }

    private static boolean isCorrect(int i, int j, int n, int m) {
        return i >= 0 && j >= 0 && i < n && j < m;
    }


    private static List<Integer> generateNeighbours(int i, int j, int n, int m) {
        List<Integer> answer = new ArrayList<>();
        if (isCorrect(i - 1, j, n, m)) {
            answer.add(getNumber(i - 1, j, m));
        }
        if (isCorrect(i + 1, j, n, m)) {
            answer.add(getNumber(i + 1, j, m));
        }
        if (isCorrect(i, j - 1, n, m)) {
            answer.add(getNumber(i, j - 1, m));
        }
        if (isCorrect(i, j + 1, n, m)) {
            answer.add(getNumber(i, j + 1, m));
        }
        return answer;
    }

    public static int[][] generate(int n, int m) {
        int[][] graph = new int[n * m][];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int number = getNumber(i, j, m);
                List<Integer> neighbours = generateNeighbours(i, j, n, m);
                graph[number] = new int[neighbours.size()];
                for (int kk = 0; kk < neighbours.size(); kk++) {
                    graph[number][kk] = neighbours.get(kk);
                }
            }
        }
        return graph;
    }
}
