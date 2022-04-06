package A3.Q3;

import java.util.*;
import java.io.File;

public class FordFulkerson {

    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        ArrayList<Integer> path = new ArrayList<Integer>();
        int[] colour = new int[graph.getNbNodes()]; //0 white, 1 grey, 2 black
        return pathDFSVisit(source, destination, graph, colour);
    }

    public static ArrayList<Integer> pathDFSVisit(Integer source, Integer destination, WGraph graph, int[] colour){
        ArrayList<Integer> path = new ArrayList<Integer>();
        colour[source] = 1;

        if (source == destination) {
            path.add(source);
            return path;
        }
        for (Edge edge : graph.listOfEdgesSorted()) {
            if (edge.nodes[0] == source && colour[edge.nodes[1]] == 0){
                path = pathDFSVisit(edge.nodes[1], destination, graph, colour);
                if (!path.isEmpty()) {
                    path.add(0, source);
                    return path;
                }
            }
        }
        colour[source] = 2;
        return path;
    }


    public static String fordfulkerson( WGraph graph){
        String answer="";
        int maxFlow = 0;
        WGraph flow = makeEmptyRGraph(graph);
        WGraph rGraph = makeRGraph(graph, flow);
        ArrayList<Integer> path = pathDFS(rGraph.getSource(), rGraph.getDestination(), rGraph);
        while (!path.isEmpty()) {
            maxFlow += findMin(getEdges(rGraph, path));
            augment(flow, rGraph, path);
            rGraph = makeRGraph(graph, flow);
            path = pathDFS(rGraph.getSource(), rGraph.getDestination(), rGraph);
        }

        answer += maxFlow + "\n" + flow.toString();
        return answer;
    }

    private static WGraph makeRGraph(WGraph graph, WGraph prevRGraph) {
        WGraph newRGraph = new WGraph();
        newRGraph.setDestination(graph.getDestination());
        newRGraph.setSource(graph.getSource());
        for (Edge edge : graph.getEdges()) {
            int flow = getFlow(edge, prevRGraph);
            int capacity = edge.weight;
            if (flow < capacity) {
                Edge forwardEdge = new Edge(edge.nodes[0], edge.nodes[1], capacity-flow);
                newRGraph.addEdge(forwardEdge);
            } else if (flow > 0) {
                Edge backwardEdge = new Edge(edge.nodes[1], edge.nodes[0], -flow);
                newRGraph.addEdge(backwardEdge);
            }
        }
        return newRGraph;
    }

    private static WGraph makeEmptyRGraph(WGraph graph) {
        WGraph newGraph = new WGraph(graph);
        for (Edge edge : graph.getEdges()) {
            newGraph.setEdge(edge.nodes[0], edge.nodes[1], 0);
        }
        return newGraph;
    }

    private static int getFlow(Edge edge, WGraph residualGraph) {
        return residualGraph.getEdge(edge.nodes[0], edge.nodes[1]).weight;
    }

    private static WGraph augment(WGraph flow, WGraph rGraph, ArrayList<Integer> path) {
        Edge[] edges = getEdges(rGraph, path);
        int beta = findMin(edges);
        for (Edge edge : edges) {
            if (edge.weight > 0) {
                flow.setEdge(edge.nodes[0], edge.nodes[1], beta);
            }
        }
        return flow;
    }

    private static int findMin (Edge[] edges) {
        int min = Integer.MAX_VALUE;
        for (Edge edge : edges) {
            min = Math.min(min, edge.weight);
        }
        return min;
    }

    private static Edge[] getEdges(WGraph graph, ArrayList<Integer> path) {
        Edge[] edges = new Edge[path.size()-1];
        for (int i = 0; i < path.size()-1; i++){
            edges[i] = graph.getEdge(path.get(i),path.get(i+1));
        }
        return edges;
    }



    public static void main(String[] args){
        String file = args[0];
        File f = new File(file);
        WGraph g = new WGraph(file);
        System.out.println(fordfulkerson(g));
    }
}
