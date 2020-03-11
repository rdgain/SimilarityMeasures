import java.util.ArrayList;

public class Similarity {

    /***
     * Java port of https://pypi.org/project/SimilarityCalculator/

     Compute the discrete Frechet distance

     Compute the Discrete Frechet Distance between two N-D curves according to
     [1]_. The Frechet distance has been defined as the walking dog problem.
     From Wikipedia: "In mathematics, the Frechet distance is a measure of
     similarity between curves that takes into account the location and
     ordering of the points along the curves. It is named after Maurice Frechet.
     https://en.wikipedia.org/wiki/Fr%C3%A9chet_distance

     Parameters
     ----------
     @param pathA : array_like
     First curve. pathA is of (M, N) shape, where
     M is the number of data points, and N is the number of dimmensions
     @param pathB : array_like
     Second curve. pathB is of (P, N) shape, where P
     is the number of data points, and N is the number of dimmensions
     @param distanceMeasure : String
     Indicates which distance measure should be used for each point in path
     @param p : double, 1 <= p <= infinity
     Which Minkowski p-norm to use. Default is p=2 (Eculidean).
     The manhattan distance is p=1.

     Returns
     -------
     @return double
     discrete Frechet distance

     References
     ----------
     .. [1] Thomas Eiter and Heikki Mannila. Computing discrete Frechet
     distance. Technical report, 1994.
     http://www.kr.tuwien.ac.at/staff/eiter/et-archive/cdtr9464.pdf
     http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.90.937&rep=rep1&type=pdf

     Notes
     -----
     Your x locations of data points should be pathA[:, 0], and the y
     locations of the data points should be pathA[:, 1]. Same for pathB.

     Thanks to Arbel Amir for the issue, and Sen ZHANG for the iterative code
     https://github.com/cjekel/similarity_measures/issues/6
     */
    public static double frechetDistance(ArrayList<double[]> pathA, ArrayList<double[]> pathB, String distanceMeasure, double p) {
        int n = pathA.size();
        int m = pathB.size();
        double[][] ca = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ca[i][j] = -1.0;
            }
        }
        ca[0][0] = applyDistance(pathA.get(0), pathB.get(0), p, distanceMeasure);
        for (int i = 1; i < n; i++) {
            ca[i][0] = Math.max(ca[i-1][0], applyDistance(pathA.get(i), pathB.get(0), p, distanceMeasure));
        }
        for (int j = 1; j < m; j++) {
            ca[0][j] = Math.max(ca[0][j-1], applyDistance(pathA.get(0), pathB.get(j), p, distanceMeasure));
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                ca[i][j] = Math.max(Math.min(ca[i-1][j], Math.min(ca[i][j-1], ca[i-1][j-1])),
                        applyDistance(pathA.get(i), pathB.get(j), p, distanceMeasure));
            }
        }

        return ca[n-1][m-1];
    }

    public static double frechetDistancePath(ArrayList<Point> pathA, ArrayList<Point> pathB, String distanceMeasure, double p) {
        return frechetDistance(pathToDoubleList(pathA), pathToDoubleList(pathB), distanceMeasure, p);
    }


    /**
     * Java port of https://pypi.org/project/SimilarityCalculator/

    Compute the Dynamic Time Warping distance.

    This computes a generic Dynamic Time Warping (DTW) distance and follows
    the algorithm from [1]_.

    Parameters
    ----------
    @param pathA : array_like
        First curve. pathA is of (M, N) shape, where
        M is the number of data points, and N is the number of dimmensions
    @param pathB : array_like
        Second curve. pathB is of (P, N) shape, where P
        is the number of data points, and N is the number of dimmensions
    @param distanceMeasure : String
        Indicates which distance measure should be used for each point in path
    @param p : double, 1 <= p <= infinity
    Which Minkowski p-norm to use. Default is p=2 (Eculidean).
    The manhattan distance is p=1.

    Retruns
    -------
    @return double
        DTW distance.

    Notes
    -----
    The DTW distance is d[-1, -1].

    This has O(M, P) computational cost.

    Your x locations of data points should be pathA[:, 0], and the y
    locations of the data points should be pathA[:, 1]. Same for pathB.

    This uses the euclidean distance for now. In the future it should be
    possible to support other metrics.

    DTW is a non-metric distance, which means DTW doesn't hold the triangle
    inequality.
    https://en.wikipedia.org/wiki/Triangle_inequality

    References
    ----------
    .. [1] Senin, P., 2008. Dynamic time warping algorithm review. Information
        and Computer Science Department University of Hawaii at Manoa Honolulu,
        USA, 855, pp.1-23.
        http://seninp.github.io/assets/pubs/senin_dtw_litreview_2008.pdf
    */
    public static double dynamicTimeWarpingDistance(ArrayList<double[]> pathA, ArrayList<double[]> pathB, String distanceMeasure, double p) {
        int n = pathA.size();
        int m = pathB.size();
        double[][] c = new double[n][m];
        double[][] d = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                c[i][j] = applyDistance(pathA.get(i), pathB.get(j), p, distanceMeasure);
                d[i][j] = 0.0;
            }
        }
        for (int i = 1; i < n; i++) {
            d[i][0] = d[i-1][0] + c[i][0];
        }
        for (int j = 1; j < m; j++) {
            d[0][j] = d[0][j-1] + c[0][j];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                d[i][j] = c[i][j] + Math.min(d[i-1][j], Math.min(d[i][j-1], d[i-1][j-1]));
            }
        }

        return d[n-1][m-1];
    }

    public static double dynamicTimeWarpingDistancePath(ArrayList<Point> pathA, ArrayList<Point> pathB, String distanceMeasure, double p) {
        return dynamicTimeWarpingDistance(pathToDoubleList(pathA), pathToDoubleList(pathB), distanceMeasure, p);
    }

    /**
     * Java port of https://pypi.org/project/hausdorff/
     *
     * Hausdorff distance.
     * Implementation of the algorithm presented in An Efficient Algorithm for Calculating the Exact Hausdorff Distance
     * (DOI: [10.1109/TPAMI.2015.2408351](https://doi.org/10.1109/TPAMI.2015.2408351)) by Aziz and Hanbury, 2015.
     *

     Parameters
     ----------
     @param pathA : array_like
     First curve. pathA is of (M, N) shape, where
     M is the number of data points, and N is the number of dimmensions
     @param pathB : array_like
     Second curve. pathB is of (P, N) shape, where P
     is the number of data points, and N is the number of dimmensions
     @param distanceMeasure : String
     Indicates which distance measure should be used for each point in path
     @param p : double, 1 <= p <= infinity
     Which Minkowski p-norm to use. Default is p=2 (Eculidean).
     The manhattan distance is p=1.

     Retruns
     -------
     @return double
     Hausdorff distance.
     */
    public static double hausdorffDistance(ArrayList<double[]> pathA, ArrayList<double[]> pathB, String distanceMeasure, double p) {
        double cmax = 0.0;
        cmax = helper_hausdorff(pathA, pathB, cmax, distanceMeasure, p);
        cmax = helper_hausdorff(pathB, pathA, cmax, distanceMeasure, p);
        return cmax;
    }

    public static double hausdorffDistancePath(ArrayList<Point> pathA, ArrayList<Point> pathB, String distanceMeasure, double p) {
        return hausdorffDistance(pathToDoubleList(pathA), pathToDoubleList(pathB), distanceMeasure, p);
    }

    private static double helper_hausdorff(ArrayList<double[]> pathA, ArrayList<double[]> pathB, double cmax, String distanceMeasure, double p) {
        for (double[] pointA : pathA) {
            double cmin = Double.POSITIVE_INFINITY;
            for (double[] pointB : pathB) {
                double d = applyDistance(pointA, pointB, p, distanceMeasure);
                if (d < cmin) {
                    cmin = d;
                }
                if (cmin < cmax) {
                    break;
                }
            }
            if (cmin > cmax && Double.POSITIVE_INFINITY > cmin) {
                cmax = cmin;
            }
        }
        return cmax;
    }

    private static ArrayList<double[]> pathToDoubleList(ArrayList<Point> path) {
        ArrayList<double[]> pa = new ArrayList<>();
        for (Point o: path) {
            pa.add(o.getCoordinates());
        }
        return pa;
    }

    private static double applyDistance(double[] x, double[] y, double p, String which) {
        switch (which) {
            case "minkowski": return Distance.minkowski_distance(x, y, p);
            case "manhattan": return Distance.manhattan_distance(x, y);
            case "chebyshev": return Distance.chebyshev_distance(x, y);
            case "cosine": return Distance.cosine_distance(x, y);
            case "haversine": return Distance.haversine_distance(x, y);
            default: return Distance.euclidian_distance(x, y);
        }
    }

}
