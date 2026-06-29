import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

import edu.odu.cs.cs417.PiecewiseLinearInterpolation;
import edu.odu.cs.cs417.PiecewiseLinearInterpolation.LineSegment;
import edu.odu.cs.cs417.TemperatureParser.CoreTempReading;
import static edu.odu.cs.cs417.TemperatureParser.parseRawTemps;

/**
 * A driver program to demonstrate the functionality of the TemperatureParser and PiecewiseLinearInterpolation classes.
 */
public class ParseTempsDriver {

    /**
     * The main function used to demonstrate the functionality of the TemperatureParser and PiecewiseLinearInterpolation classes.
     * 
     * @param args used to pass in a single filename
     */
    public static void main(String[] args)
    {
        BufferedReader tFileStream = null;
        File inputFile = null;

        // Parse command line argument 1
        try {
            inputFile = new File(args[0]);
            tFileStream = new BufferedReader(new FileReader(inputFile));
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: No input file provided.");
            System.exit(1);
        }
        catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
            System.exit(1);
        }

        // Extract the basename for output file generation
        String basename = inputFile.getName();
        int dotIndex = basename.lastIndexOf('.');
        if (dotIndex > 0) {
            basename = basename.substring(0, dotIndex);
        }

        // Parse the file using the provided TemperatureParser
        List<CoreTempReading> allTheTemps = parseRawTemps(tFileStream);

        //----------------------------------------------------------------------
        // Split into separate arrays
        //----------------------------------------------------------------------
        final int numberOfReadings = allTheTemps.size();
        final int numberOfCores = allTheTemps.get(0).readings.length;

        int[] times = new int[numberOfReadings];
        double[][] coreReadings = new double[numberOfCores][numberOfReadings];

        for (int lineIdx = 0; lineIdx < numberOfReadings; ++lineIdx) {
            for (int coreIdx = 0; coreIdx < numberOfCores; ++coreIdx) {
                times[lineIdx] = allTheTemps.get(lineIdx).step;
                coreReadings[coreIdx][lineIdx] = allTheTemps.get(lineIdx).readings[coreIdx];
            }
        }

        //----------------------------------------------------------------------
        // Compute Piecewise Linear Interpolation and Write to Files
        //----------------------------------------------------------------------
        for (int coreIdx = 0; coreIdx < numberOfCores; ++coreIdx) {
            
            // Pass the split arrays into the computational module 
            List<LineSegment> segments = PiecewiseLinearInterpolation.compute(coreReadings[coreIdx], times);
            
            // Format output filename (e.g., sample-input-core-00.txt)
            String outputFilename = String.format("%s-core-%02d.txt", basename, coreIdx);
            
            try (PrintWriter writer = new PrintWriter(outputFilename)) {
                for (LineSegment segment : segments) {
                    writer.println(segment.toString());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error creating output file: " + outputFilename);
            }
        }
    }
}
