package ru.vladrus13.generators;

import java.util.ArrayList;
import java.util.List;

public class Cube {

    private static int getNumber(int i, int j, int l, int m, int k) {
        return i * m * k + j * k + l;
    }

    private static boolean isCorrect(int i, int j, int l, int n, int m, int k) {
        return i >= 0 && j >= 0 && l >= 0 && i < n && j < m && l < k;
    }

    private static List<Integer> generateNeighbours(int i, int j, int l, int n, int m, int k) {
        List<Integer> answer = new ArrayList<>();
        if (isCorrect(i - 1, j, l, n, m, k)) {
            answer.add(getNumber(i - 1, j, l, m, k));
        }
        if (isCorrect(i + 1, j, l, n, m, k)) {
            answer.add(getNumber(i + 1, j, l, m, k));
        }
        if (isCorrect(i, j - 1, l, n, m, k)) {
            answer.add(getNumber(i, j - 1, l, m, k));
        }
        if (isCorrect(i, j + 1, l, n, m, k)) {
            answer.add(getNumber(i, j + 1, l, m, k));
        }
        if (isCorrect(i, j, l - 1, n, m, k)) {
            answer.add(getNumber(i, j, l - 1, m, k));
        }
        if (isCorrect(i, j, l + 1, n, m, k)) {
            answer.add(getNumber(i, j, l + 1, m, k));
        }
        return answer;
    }

    public static int[][] generate(int n, int m, int k) {
        int[][] graph = new int[n * m * k][];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int l = 0; l < k; l++) {
                    int number = getNumber(i, j, l, m, k);
                    List<Integer> neighbours = generateNeighbours(i, j, l, n, m, k);
                    graph[number] = new int[neighbours.size()];
                    for (int kk = 0; kk < neighbours.size(); kk++) {
                        graph[number][kk] = neighbours.get(kk);
                    }
                }
            }
        }
        return graph;
    }
}
