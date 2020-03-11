import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int bound = 10;
        Random r = new Random();

        ArrayList<Point> pathA = new ArrayList<>();
        ArrayList<Point> pathB = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pathA.add(new Point(r.nextInt(bound), r.nextInt(bound)));
            pathB.add(new Point(r.nextInt(bound), r.nextInt(bound)));
        }

        System.out.println(pathA.toString());
        System.out.println(pathB.toString());
        System.out.println("\nFrechet");
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "minkowski", 1));
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "manhattan", 1));
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "euclidian", 1));
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "chebyshev", 1));
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "cosine", 1));
        System.out.println(Similarity.frechetDistancePath(pathA, pathB, "haversine", 1));
        System.out.println("\nHausdorff");
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "minkowski", 1));
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "manhattan", 1));
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "euclidian", 1));
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "chebyshev", 1));
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "cosine", 1));
        System.out.println(Similarity.hausdorffDistancePath(pathA, pathB, "haversine", 1));
        System.out.println("\nDTW");
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "minkowski", 1));
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "manhattan", 1));
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "euclidian", 1));
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "chebyshev", 1));
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "cosine", 1));
        System.out.println(Similarity.dynamicTimeWarpingDistancePath(pathA, pathB, "haversine", 1));
    }
}
