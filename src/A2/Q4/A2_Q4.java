package A2.Q4;

import java.math.BigInteger;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class A2_Q4 {
    private static int[] fibArray;

    public static String mod_fibonacci(int N, BigInteger K) {
        fibArray = new int[N+1];
        // Base cases
        if (N < 1) return "";
        if (N == 1) return "X";
        if (N == 2) return "Y";

        //Recursive case K <= n-2 (or K < n-1)
        if (K.compareTo(BigInteger.valueOf(fib(N - 2))) <= 0) {
            return mod_fibonacci(N-2, K);
        }
        //Recursive case K >= n-1 (or K > n-2)
        else  {
            return mod_fibonacci(N-1, K.subtract(BigInteger.valueOf(fib(N - 2))));
        }
    }

    private static int fib(int n) {
        if (n <= 1) return n;
        else {
            if (fibArray[n-1] == 0) fibArray[n-1] = fib(n-1);
            if (fibArray[n-2] == 0) fibArray[n-2] = fib(n-2);
        }
        fibArray[n] = fibArray[n-2] + fibArray[n-1];
        return fibArray[n];
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
