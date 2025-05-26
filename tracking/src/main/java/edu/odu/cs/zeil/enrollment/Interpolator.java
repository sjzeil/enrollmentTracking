package edu.odu.cs.zeil.enrollment;

import java.util.ArrayList;
import java.util.Collections;

public class Interpolator {
    

    private ArrayList<DataPoint> points;

    public Interpolator() {
        points = new ArrayList<>();
    }

    public void add(DataPoint dataPoint) {
        int pos = Collections.binarySearch(points, dataPoint);
        if (pos >= 0) {
            points.set(pos, dataPoint);
        } else {
            pos = -(pos + 1);
            points.add(pos, dataPoint);
        }
    }

    public double get(double x) {
        DataPoint dataPoint = new DataPoint(x, 0.0);
        int pos = Collections.binarySearch(points, dataPoint);
        if (pos >= 0) {
            return points.get(pos).y();
        } else if (points.size() <= 2) {
            throw new IndexOutOfBoundsException();
        } else {
            int p2 = -(pos + 1);
            int p1 = p2 - 1;
            if (p1 < 0) {
                return points.get(p2).y();
            } else if (p2 >= points.size()) {
                return points.get(points.size()-1).y();
            } else {
                DataPoint d1 = points.get(p1);
                DataPoint d2 = points.get(p2);
                
                double x1 = d1.x();
                double x2 = d2.x();
                double y1 = d1.y();
                double y2 = d2.y();

                return x1 + (x - x1) * (y2 - y1) / (x2 - x1);

            }
        }
    }
}
