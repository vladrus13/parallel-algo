package ru.vladrus13;

import ru.vladrus13.bfs.NormalBFS;
import ru.vladrus13.bfs.ParallelBFS;
import ru.vladrus13.generators.Cube;

public class Launcher {

    public static Pair<Long, Long> run(Test test) {
        long start = System.currentTimeMillis();
        int x = NormalBFS.run(test.graph, test.start, test.finish);
        long finish = System.currentTimeMillis();
        long normal = finish - start;
        start = System.currentTimeMillis();
        int y = ParallelBFS.run(test.graph, test.start, test.finish);
        finish = System.currentTimeMillis();
        long parallel = finish - start;
        if (x != y) {
            throw new IllegalStateException(String.format("%d, %d", x, y));
        }
        return new Pair<>(normal, parallel);
    }

    public static void preset(Test test) {
        Pair<Long, Long> pair = run(test);
        System.out.printf("%d - %d%n", pair.a, pair.b);
    }

    public static void main(String[] args) {
        int[][] betterCube = Cube.generate(300, 300, 300);
        int[][] maximaCube = Cube.generate(360, 360, 360);

        preset(new Test(betterCube, 0, 26999999));

        preset(new Test(maximaCube, 0, 46655999));
    }

    public static class Pair<A, B> {
        public final A a;
        public final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class Test {
        public final int[][] graph;
        public final int start;
        public final int finish;

        public Test(int[][] graph, int start, int finish) {
            this.graph = graph;
            this.start = start;
            this.finish = finish;
        }
    }
}
