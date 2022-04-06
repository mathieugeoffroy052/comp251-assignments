package A3.Q1;

import java.lang.reflect.Array;
import java.util.*;


public class Q1 {

    /**
     * . is white
     * - is grey
     * # is black
     * @param jail
     * @return
     */
    public static int find_exit(String[][][] jail) {
        int levels =  jail.length;
        int rows = jail[0].length;
        int columns = jail[0][0].length;
        int[] start = new int[4];

        for (int i = 0; i < levels; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++){
                    if (jail[i][j][k].equals("S")){
                        start = new int[]{i, j, k, 0};
                        break;
                    }
                }
            }
        }

        Queue<int[]> Q = new LinkedList<int[]>();
        Q.add(start);

        while(!Q.isEmpty()) {
            int[] u = Q.remove();
            for (int[] v : adj(u, jail)) {
                if (jail[v[0]][v[1]][v[2]].equals(".")) {
                    jail[v[0]][v[1]][v[2]] = "-";
                    v[3] = u[3] + 1;
                    Q.add(v);
                } else if (jail[v[0]][v[1]][v[2]].equals("E")) {
                    return u[3] + 1;
                }
            }
            jail[u[0]][u[1]][u[2]] = "#";
        }
        return -1;
    }

    private static ArrayList<int[]> adj(int[] node, String[][][] jail) {
        int levels = jail.length;
        int rows = jail[0].length;
        int columns = jail[0][0].length;
        ArrayList<int[]> ans = new ArrayList<int[]>();

        //Level below
        if (node[0] > 0 && (jail[node[0] - 1][node[1]][node[2]].equals(".") || jail[node[0] - 1][node[1]][node[2]].equals("-") || jail[node[0] - 1][node[1]][node[2]].equals("E"))) {
            ans.add(new int[]{node[0] - 1, node[1], node[2], -1});
        }
        //Level above
        if (node[0] < levels-1 && (jail[node[0] + 1][node[1]][node[2]].equals(".") || jail[node[0] + 1][node[1]][node[2]].equals("-") || jail[node[0] + 1][node[1]][node[2]].equals("E"))) {
            ans.add(new int[]{node[0] + 1, node[1], node[2], -1});
        }
        //Row below
        if (node[1] > 0 && (jail[node[0]][node[1]-1][node[2]].equals(".") || jail[node[0]][node[1]-1][node[2]].equals("-") || jail[node[0]][node[1]-1][node[2]].equals("E"))) {
            ans.add(new int[]{node[0], node[1] - 1, node[2], -1});
        }
        //Row above
        if (node[1] < rows-1 && (jail[node[0]][node[1]+1][node[2]].equals(".") || jail[node[0]][node[1]+1][node[2]].equals("-") || jail[node[0]][node[1]+1][node[2]].equals("E"))) {
            ans.add(new int[]{node[0], node[1] + 1, node[2], -1});
        }
        //Column below
        if (node[2] > 0 && (jail[node[0]][node[1]][node[2]-1].equals(".") || jail[node[0]][node[1]][node[2]-1].equals("-") || jail[node[0]][node[1]][node[2]-1].equals("E"))) {
            ans.add(new int[]{node[0], node[1], node[2] - 1, -1});
        }
        //Column above
        if (node[2] < columns-1 && (jail[node[0]][node[1]][node[2]+1].equals(".") || jail[node[0]][node[1]][node[2]+1].equals("-") || jail[node[0]][node[1]][node[2]+1].equals("E"))) {
            ans.add(new int[]{node[0], node[1], node[2] + 1, -1});
        }
        return ans;
    }


    public static void main(String[] args) {

    }

}