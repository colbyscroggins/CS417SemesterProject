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
        public final int x0;
        public final int x1;
        public final double m; // Slope (c1)
        public final double b; // Y-intercept (c0)

        public LineSegment(int x0, int x1, double m, double b) {
            this.x0 = x0;
            this.x1 = x1;
            this.m = m;
            this.b = b;
        }

        /**
         * Formats the segment to match the project's exact output specifications.
         * @return A formatted string 
         */
        @Override
        public String toString() {
            return String.format("%8d <= x <= %8d ; y = %12.4f + %12.4f x ; interpolation", x0, x1, b, m);
        }
    }

    /**
     * Computes piecewise linear equations for a sequence of temperatures.
     * @param temperatures Array of raw temperature values for a single CPU core.
     * @param times Array of corresponding time steps in seconds.
     * @return A list of computed line segments formatted for file output.
     */
    public static List<LineSegment> compute(double[] temperatures, int[] times) {
        List<LineSegment> segments = new ArrayList<>();

        // Iterate through n-1 segments to compute adjacent pairs
        for (int k = 0; k < temperatures.length - 1; k++) {
            int x_k = times[k];
            int x_k1 = times[k + 1];
            
            double y_k = temperatures[k];
            double y_k1 = temperatures[k + 1];

            // Compute slope (m) and y-intercept (b)
            double m = (y_k1 - y_k) / (x_k1 - x_k);
            double b = y_k - (m * x_k);

            segments.add(new LineSegment(x_k, x_k1, m, b));
        }

        return segments;
    }
}