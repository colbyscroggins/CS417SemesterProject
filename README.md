# About
 This program preprocesses temperature data taken from a 4-core Intel i7-6700K CPU, recorded every 30 seconds under heavy load while batch encoding videos, and then computes piecewise linear equations representing the temperature trends between each 30-second interval. The program takes an input file as a command line argument and then outputs the computed data as a set of four .txt files, one for each CPU core.

# Requirements

 * Java Development Kit 21
 * No libraries or built-in functions are required to execute the program

# Compilation

 The program can be compiled with the included Gradle wrapper by running:

 ```
 ./gradlew build
 ```

# Execution

 After compiling, the program can be executed with the Gradle run task and the input filepath:

 ```
 ./gradlew run --args="<input_filepath>"
 ```

# Sample Execution & Output

 Sample Compilation:
 ```
 ./gradlew build
 ```

 Sample Execution:
 ```
 ./gradlew run --args="src/test/data/sensors-2018.12.26-no-labels.txt"
 ```
 Sample Input:
 * sample-input.txt
 ```
 61.0 63.0 50.0 58.0
 80.0 81.0 68.0 77.0
 62.0 63.0 52.0 60.0
 83.0 82.0 70.0 79.0
 68.0 69.0 58.0 65.0
 ```
 Sample Output:
 * sample-input-core-00.txt
 ```
   0 <= x <=       30 ; y =      61.0000 +       0.6333 x ; interpolation
  30 <= x <=       60 ; y =      98.0000 +      -0.6000 x ; interpolation
  60 <= x <=       90 ; y =      20.0000 +       0.7000 x ; interpolation
  90 <= x <=      120 ; y =     128.0000 +      -0.5000 x ; interpolation
 ```
 * sample-input-core-01.txt
 ```
   0 <= x <=       30 ; y =      63.0000 +       0.6000 x ; interpolation
  30 <= x <=       60 ; y =      99.0000 +      -0.6000 x ; interpolation
  60 <= x <=       90 ; y =      25.0000 +       0.6333 x ; interpolation
  90 <= x <=      120 ; y =     121.0000 +      -0.4333 x ; interpolation
 ```
 * sample-input-core-02.txt
 ```
   0 <= x <=       30 ; y =      50.0000 +       0.6000 x ; interpolation
  30 <= x <=       60 ; y =      84.0000 +      -0.5333 x ; interpolation
  60 <= x <=       90 ; y =      16.0000 +       0.6000 x ; interpolation
  90 <= x <=      120 ; y =     106.0000 +      -0.4000 x ; interpolation
 ```
 * sample-input-core-03.txt
 ```
   0 <= x <=       30 ; y =      58.0000 +       0.6333 x ; interpolation
  30 <= x <=       60 ; y =      94.0000 +      -0.5667 x ; interpolation
  60 <= x <=       90 ; y =      22.0000 +       0.6333 x ; interpolation
  90 <= x <=      120 ; y =     121.0000 +      -0.4667 x ; interpolation
 ```