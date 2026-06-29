package edu.odu.cs.cs417;

import java.util.ArrayList;
import java.util.List;

/**
 * Computes Piecewise Linear Interpolation for CPU temperature readings.
 */
public class PiecewiseLinearInterpolation {

    /**
     * Represents a discrete linear interpolation line between two adjacent data points.
     */
    public static class LineSegment {
        public final double x0;
        public final double x1;
        public final double m; // Slope (c1)
        public final double b; // Y-intercept (c0)

        public LineSegment(double x0, double x1, double m, double b) {
            this.x0 = x0;
            this.x1 = x1;
            this.m = m;
            this.b = b;
        }

        /**
         * Formats the segment to match the project's exact output specifications.
         * 
         * @return A formatted string (e.g., "       0 <= x <=       30 ; y =      61.0000 +       0.6333 x ; interpolation")
         */
        @Override
        public String toString() {
            // Adjust spacing constraints exactly to match sample outputs.
            return String.format("%8.0f <= x <= %8.0f ; y = %12.4f + %12.4f x ; interpolation", x0, x1, b, m);
        }
    }

    /**
     * Computes piecewise linear equations for a sequence of temperatures.
     *
     * @param temperatures List of raw temperature floating point values for a single CPU core.
     * @param timeStep     The time interval in seconds between readings.
     * @return A list of computed line segments formatted for file output.
     */
    public static List<LineSegment> compute(List<Double> temperatures, int timeStep) {
        List<LineSegment> segments = new ArrayList<>();

        // Iterate through n-1 segments to compute adjacent pairs
        for (int k = 0; k < temperatures.size() - 1; k++) {
            double x_k = k * timeStep;
            double x_k1 = (k + 1) * timeStep;
            
            double y_k = temperatures.get(k);
            double y_k1 = temperatures.get(k + 1);

            // Compute slope (m) and y-intercept (b)
            double m = (y_k1 - y_k) / (x_k1 - x_k);
            double b = y_k - (m * x_k);

            segments.add(new LineSegment(x_k, x_k1, m, b));
        }

        return segments;
    }
}