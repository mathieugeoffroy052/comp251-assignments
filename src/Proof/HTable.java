package Proof;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Implementation of a hashtable that uses chaining to handle collisions.
 * Source: Assignment 1, COMP 251 Winter 2022
 */
public class HTable {

    public int m; // number of SLOTS
    public int size; //number of nodes in the table
    public ArrayList<LinkedList<Integer>>  Table;

    private int w;
    private int r;
    private int A; // the default random number
    private int countRehash = 0; //number of times the table has be rehashed
    private static boolean verbose;
    private static boolean canRehash;

    /**
     * Creates a new HashTable.
     * if A==-1, then a random A is generated. else, input A is used.
     * Taken from Assignment 1, COMP 251 Winter 2022.
     */
    protected HTable(int w, int A){
        this.w = w;
        this.r = (int) (w-1)/2 +1;
        this.m = (int) Math.pow(2, r);
        this.size = 0;
        this.Table = new ArrayList<LinkedList<Integer>>(m);
        for (int i=0; i<m; i++) {
            Table.add(new LinkedList<Integer>());
        }
        if (A==-1){
            this.A = generateRandom((int) Math.pow(2, w-1), (int) Math.pow(2, w));
        }
        else{
            this.A = A;
        }

    }

    //generate a random number in a range (for A)
    public static int generateRandom(int min, int max) {
        Random generator = new Random();
        int i = generator.nextInt(max-min-1);
        return i+min+1;
    }

    /**
     * Implements the hash function h(k) from Assignment 1, COMP 251 winter 2022
     */
    public int chain (int key) {
        return ((this.A * key) % ((int) Math.pow(2, this.w))) >> (this.w - this.r);
    }

    /**
     * Inserts key k into hash table without checking for an existing key
     * of the same value. This assumes only unique keys are added.
     */
    public void insertUniqueKey(int key){
        int hash = chain(key);
        this.Table.get(hash).addFirst(key); //add at top of the linked list
        this.size++;
    }

    /**
     * Inserts key k into hash table but checks for collisions first.
     */
    public void insertKey(int key){
        int hash = chain(key);
        int collisions = this.Table.get(hash).size(); //get num of elements already in the array at hashed index
        boolean insertNewKey = true;
        for (int i = 0; i < collisions; i++) {
            if (this.Table.get(hash).get(i) == key) { //check if key is already in list
                insertNewKey = false;
                // if was using key-value pairs, would replace value by new value here
                break;
            }
        }
        if (insertNewKey)  //add at top of the array if no key exisits of that value
            this.Table.get(hash).addFirst(key);
        this.size++;

        //check load factor of table, rehash to larger HashTable if > 0.75
        double loadFactor = ((double) size) / ((double) this.m);
        if (loadFactor >= .75 && canRehash) {
            if (verbose) System.out.printf("Node #%d: ", this.size);
            this.rehash(2+this.w);
            countRehash++;
            if (verbose) System.out.printf("Rehash #%d. New size: %d.\n", countRehash, this.m);
        }
    }

    /**
     * Updates the hashtable with the new chosen w
     * and re-inserts all elements from previous hashtable
     * into new one
     */
    private void rehash(int w) {
        HTable tempTable = new HTable(w, -1);
        this.m = tempTable.m;
        this.w = tempTable.w;
        this.Table = tempTable.Table;
        this.r = tempTable.r;
        this.size = tempTable.size;
        for(LinkedList<Integer> list : tempTable.Table) {
            for (Integer key : list) {
                this.insertUniqueKey(key); //keys will always be unique once in the hashtable
            }
        }
    }

    public static void main(String[] args) {
        /* Modify these parameters */
        int maxNodes = 1000; //number of nodes to insert
        boolean unique = false; //assume nodes are unique
        verbose = true; //print rehash messages
        canRehash = true; //set to false to get worst case

        Random rgen = new Random();
        HTable table = new HTable(2, -1);
        if (verbose) System.out.printf("New HashTable of size %d.\n", table.m);
        ArrayList<Integer> runtime = new ArrayList<Integer>();
        runtime.add(0,0);
        ArrayList<Integer> numNodes = new ArrayList<Integer>();
        numNodes.add(0,0);

        //Add n nodes to hashtable
        if (verbose) System.out.printf("Add %d nodes to HashTable\n\n", maxNodes);
        for (int n = 1; n <= maxNodes; n++) {
            double startTime = System.nanoTime();

            //Select appropriate function depending on unique assumption
            if (unique) table.insertUniqueKey(rgen.nextInt(0, maxNodes*10));
            else table.insertKey(rgen.nextInt(0, maxNodes*10));

            double endTime = System.nanoTime();
            double totalTime = endTime - startTime;

            //Add data point to arraylists
            runtime.add((int) totalTime/1000);
            numNodes.add(n);
        }
        //Name for graph
        if (verbose) System.out.println("Rehashed " + table.countRehash + " times.");
        String method;
        if (unique) method = "InsertUniquekey()";
        else method = "InsertKey()";

        //create graph
        XYChart chart = QuickChart.getChart("Runtime to Insert " +maxNodes + " Nodes in a Hashmap with " + method, "Number of Nodes", "Execution Time (us)", method, numNodes, runtime);
        chart.getStyler().setLegendVisible(false);



        //save graph in high-res
        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "./"+method+"_n="+maxNodes, BitmapEncoder.BitmapFormat.PNG, 370);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //show graph
        new SwingWrapper(chart).displayChart();
    }
}
