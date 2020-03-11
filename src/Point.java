import java.util.Arrays;

public class Point {
    private int nDims;
    private double[] coordinates;

    public Point(double[] coordinates) {
        nDims = coordinates.length;
        this.coordinates = coordinates.clone();
    }

    public Point(double x, double y) {
        nDims = 2;
        coordinates = new double[]{x, y};
    }

    public int getDims() {
        return nDims;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
        this.nDims = coordinates.length;
    }

    @Override
    public String toString() {
        return "Point{" +
                "nDims=" + nDims +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
