import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.odu.cs.cs417.PiecewiseLinearInterpolation;
import edu.odu.cs.cs417.PiecewiseLinearInterpolation.LineSegment;

/**
 * A simple command line test driver for TemperatureParser.
 */
public class ParseTempsDriver {

    public static void main(String[] args) {
        
        // 1. Arguments & Execution
        // Program must accept an input filename as the first command line argument
        if (args.length < 1) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java ParseTempsDriver <input-file>");
            System.exit(1);
        }

        String inputFilename = args[0];
        File inputFile = new File(inputFilename);

        // Extract basename (remove directory path and extension like .txt)
        String basename = inputFile.getName();
        int dotIndex = basename.lastIndexOf('.');
        if (dotIndex > 0) {
            basename = basename.substring(0, dotIndex);
        }

        // 2. Data pre-processing
        // Structure the data for analysis by separating it into four cores
        List<List<Double>> coreTemperatures = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            coreTemperatures.add(new ArrayList<>());
        }

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNext()) {
                // Each line represents temperature readings from 4 processor cores
                for (int i = 0; i < 4; i++) {
                    if (scanner.hasNext()) {
                        String token = scanner.next();
                        
                        // Clean the token of any labels (e.g., "+61.0°C" -> "61.0") 
                        // to handle both labeled and unlabeled data files seamlessly.
                        token = token.replace("+", "").replace("°C", "");
                        coreTemperatures.get(i).add(Double.parseDouble(token));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not open input file " + inputFilename);
            System.exit(1);
        }

        // 3. Piecewise Linear Interpolation & File Output
        // Readings are taken every 30 seconds
        int timeStep = 30; 

        for (int core = 0; core < 4; core++) {
            List<Double> tempsForCore = coreTemperatures.get(core);
            
            // Compute the interpolation lines for the current core
            List<LineSegment> segments = PiecewiseLinearInterpolation.compute(tempsForCore, timeStep);

            // Generate output filename: {basename}-core-0{core}.txt
            String outputFilename = String.format("%s-core-0%d.txt", basename, core);
            
            // Write all output to text files (one file per core)
            try (PrintWriter writer = new PrintWriter(outputFilename)) {
                for (LineSegment segment : segments) {
                    writer.println(segment.toString());
                }
                
                // Note: The global linear least squares approximation output 
                // will be generated and appended here in the next milestone.
                
            } catch (FileNotFoundException e) {
                System.err.println("Error: Could not create output file " + outputFilename);
            }
        }
    }
}
