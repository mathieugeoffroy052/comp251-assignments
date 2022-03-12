package A2.Q2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;


public class A2_Q2 {

    //Source, comp251 notes for DP (knapsack)
    public static int weight(int[] plates) {
        int[][] table = new int[plates.length+1][1401]; //2d array larger than 1000 to store values that could be above (1400 arbitrarily)
        int closestWeight = 0; //keep track of closest weight to 1000

        for (int w = 0; w < 1400; w++) {
            table[0][w] = 0;
        }
        for(int i = 1; i <= plates.length; i++) {
            int plateValue = plates[i-1];
            for (int w = 1; w <= 1400 - 1; w++) {
                if (plateValue > w) table[i][w] = table[i-1][w];
                //check distance from 1000 for both cases of knapsack
                else if (Math.abs(table[i-1][w] - 1000) < Math.abs(plateValue + table[i-1][w-plateValue] - 1000))
                    table[i][w] = table[i-1][w];
                else {
                    table[i][w] = table[i-1][w-plateValue] + plateValue;
                    closestWeight = table[i][w];
                }

            }
        }
        return table[plates.length][closestWeight];
    }


    final static String TEST_FOLDER = "src/A2/Q2/Tests";
    public static void main(String[] args) {
        File f = new File(TEST_FOLDER);
        System.out.println(f);
        System.out.println(f.list());
        for (String name : f.list()) {
            if (name.endsWith(".in")) {
                try {
                    File in = new File(TEST_FOLDER + "/" + name);
                    File out = new File(TEST_FOLDER + "/" + name.substring(0, name.length()-3)+".ans");
                    Scanner in_scan = new Scanner(in);
                    Scanner out_scan = new Scanner(out);
                    int n = in_scan.nextInt();
                    int[] weights = new int[n];
                    for (int i = 0; i < n; i++) {
                        weights[i] = in_scan.nextInt();
                    }
                    int got = weight(weights);
                    int expected = out_scan.nextInt();
                    if (got != expected) {
                        System.out.printf("Expected %d but got %d\n", expected, got);
                    } else {
                        System.out.printf("Passed test %s\n", name);
                    }
                    in_scan.close();
                    out_scan.close();
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            }
        }
    }

}