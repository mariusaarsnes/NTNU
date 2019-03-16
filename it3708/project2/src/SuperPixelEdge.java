import java.util.Comparator;


public class SuperPixelEdge implements Comparable<SuperPixelEdge> {
    final SuperPixel U, V;
    final double distance;

    SuperPixelEdge(SuperPixel U, SuperPixel V, double distance) {
        this.U = U;
        this.V = V;
        this.distance = distance;
    }

    @Override
    public int compareTo(SuperPixelEdge o) {
        return Double.compare(this.distance, o.distance);
    }
}

class SuperPixelEdgeComparator implements Comparator<SuperPixelEdge> {

    @Override
    public int compare(SuperPixelEdge o1, SuperPixelEdge o2) {
        return Double.compare(o1.distance, o2.distance);
    }
}
