package edu.odu.cs.zeil.enrollment;

public record DataPoint(double x, double y) implements Comparable<DataPoint> {

    @Override
    public int compareTo(DataPoint d) {
        return (x() < d.x()) ? -1 : ((x() == d.x()) ? 0 : 1);
    }

}
