package A2.Q1;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A2_Q1 {
    final static int HEIGHT = 5;
    final static int WIDTH = 9;
    final static String TEST_FOLDER = "src/A2/Q1/Tests";

    public static int[] game(String[][] board){
        int maxWidth = board[0].length;
        int maxHeight = board.length;
        int count = 0;
        for(int i = 0; i < maxHeight; i++){
            for (int j = 0; j < maxWidth; j++) {
                if (board[i][j].equals("o")) count++;
            }
        }
        int[] returnArray = {count, 0};


        for(int i = 0; i < maxHeight; i++){
            for (int j = 0; j < maxWidth; j++) {
                if (board[i][j].equals("o")) {
                    if (j-2 >= 0 && board[i][j-1].equals("o") && board[i][j-2].equals(".")) { //check left side
                        String[][] boardCopy = copyBoard(board);
                        boardCopy[i][j] = ".";
                        boardCopy[i][j-1] = ".";
                        boardCopy[i][j-2] = "o";
                        int[] resp = game(boardCopy);
                        if (resp[0] < returnArray[0]) {
                            returnArray[0] = resp[0];
                            returnArray[1] = resp[1]+1;
                        }
                    }
                    if (i-2 >= 0 && board[i-1][j].equals("o") && board[i-2][j].equals(".")) { //check top side
                        String[][] boardCopy = copyBoard(board);
                        boardCopy[i][j] = ".";
                        boardCopy[i-1][j] = ".";
                        boardCopy[i-2][j] = "o";
                        int[] resp = game(boardCopy);
                        if (resp[0] < returnArray[0]) {
                            returnArray[0] = resp[0];
                            returnArray[1] = resp[1]+1;
                        }
                    }
                    if (j+2 < maxWidth && board[i][j+1].equals("o") && board[i][j+2].equals(".")) { //check right side
                        String[][] boardCopy = copyBoard(board);
                        boardCopy[i][j] = ".";
                        boardCopy[i][j+1] = ".";
                        boardCopy[i][j+2] = "o";
                        int[] resp = game(boardCopy);
                        if (resp[0] < returnArray[0]) {
                            returnArray[0] = resp[0];
                            returnArray[1] = resp[1]+1;
                        }
                    }
                    if (i+2 < maxHeight && board[i+1][j].equals("o") && board[i+2][j].equals(".")) { //check bottom side
                        String[][] boardCopy = copyBoard(board);
                        boardCopy[i][j] = ".";
                        boardCopy[i+1][j] = ".";
                        boardCopy[i+2][j] = "o";
                        int[] resp = game(boardCopy);
                        if (resp[0] < returnArray[0]) {
                            returnArray[0] = resp[0];
                            returnArray[1] = resp[1]+1;
                        }
                    }
                }
            }
        }
        return returnArray;

    }

    private static String[][] copyBoard(String[][] array) {
        int maxWidth = array[0].length;
        int maxHeight = array.length;
        //deep copy board
        String[][] copy = new String[maxHeight][maxWidth];
        for (int i = 0; i < maxHeight; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return copy;
    }

    public static void main(String[] args) {
        File f = new File(TEST_FOLDER);
        int countSuccess = 0;
        int countCases = 0;
        for (String name : f.list()) {
            if (name.endsWith(".in")) {
                try {
                    File in = new File(TEST_FOLDER + "/" + name);
                    File out = new File(TEST_FOLDER + "/" + name.substring(0, name.length()-3)+".ans");
                    Scanner in_scan = new Scanner(in);
                    Scanner out_scan = new Scanner(out);
                    System.out.printf("\n\nAttempting file %s\n", name);
                    int n = in_scan.nextInt();
                    in_scan.nextLine();
                    for (int cs = 0; cs < n; cs++) {
                        String[][] board = new String[HEIGHT][WIDTH];
                        for (int i = 0; i < HEIGHT; i++) {
                            String line = in_scan.nextLine();
                            for (int j = 0; j < WIDTH; j++) {
                                board[i][j] = new String(new char[]{line.charAt(j)});
                            }
                        }
                        int[] got = game(board);
                        int expected_0 = out_scan.nextInt();
                        int expected_1 = out_scan.nextInt();
                        countCases++;
                        if (got[0] != expected_0 || got[1] != expected_1) {
                            System.out.printf("Expected %d %d but got %d %d\n", expected_0, expected_1, got[0], got[1]);
                        } else {
                            System.out.printf("Passed test %d\n", cs);
                            countSuccess++;
                        }
                        try {
                            in_scan.nextLine(); // Skip empty line
                        } catch (NoSuchElementException e) {

                        }
                    }

                    in_scan.close();
                    out_scan.close();
                    } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            }
        }
        System.out.printf("\n\nPassed %d on %d total test cases.", countSuccess, countCases );
    }

}