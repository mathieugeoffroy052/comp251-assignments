package A3.Q2;

import java.util.*;

public class Q2 {

    public static int rings(Hashtable<Integer, ArrayList<Integer>> graph, int[] location) {
        //calculate in-degree for each node
        Hashtable<Integer, Integer> inDegree = new Hashtable<Integer, Integer>();
        for (Integer k : graph.keySet()) {
            for (Integer i : graph.get(k)) {
                if (inDegree.containsKey(i)) {
                    inDegree.replace(i, inDegree.get(i) + 1);
                } else {
                    inDegree.put(i, 1);
                }
            }
        }

        Queue<Integer> earth = new LinkedList<Integer>();
        Queue<Integer> asgard = new LinkedList<Integer>();

        Set<Integer> sources = graph.keySet();
        Set<Integer> sourceNodes = new HashSet<Integer>(sources);
        sourceNodes.removeAll(inDegree.keySet());

        for (Integer i : sources) {
            if (location[i] == 1) {
                earth.add(i);
            } else {
                asgard.add(i);
            }
        }

        boolean previousEarth = false;
        if (earth.size() > asgard.size()) {
            previousEarth = true;
        }

        int countTravel = 0;
        while (!earth.isEmpty() || !asgard.isEmpty()) {
            Integer removedNode;
            if (previousEarth && !earth.isEmpty()) {
                removedNode = earth.remove();
            } else if (!previousEarth && !asgard.isEmpty()) {
                removedNode = asgard.remove();
            } else if (previousEarth && earth.isEmpty()) {
                removedNode = asgard.remove();
                previousEarth = false;
                countTravel++;
            } else if (!previousEarth && asgard.isEmpty()) {
                removedNode = earth.remove();
                previousEarth = true;
                countTravel++;
            } else {
                return countTravel;
            }
            for (Integer i : graph.get(removedNode)) {
                if (inDegree.get(i) == 1) {
                    inDegree.remove(i);
                    if (location[i] == 1) {
                        earth.add(i);
                    } else {
                        asgard.add(i);
                    }
                } else {
                    inDegree.replace(i, inDegree.get(i) - 1);
                }
            }
        }
        return countTravel;
    }


    public static void main(String[] args) {
        Hashtable<Integer, ArrayList<Integer>> graph = new Hashtable<>();
        graph.put(0, new ArrayList<Integer>(Arrays.asList(1, 2)));
        graph.put(1, new ArrayList<Integer>(Arrays.asList(3, 4)));
        graph.put(2, new ArrayList<Integer>(Arrays.asList(3, 4)));
        graph.put(3, new ArrayList<Integer>());
        graph.put(4, new ArrayList<Integer>());

        int[] location = {1, 2, 1, 2, 1};

        System.out.println(rings(graph, location));
        return;


    }

}
