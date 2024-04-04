package main;

import java.util.*;

public class Graph {
    private final Map<Integer, Set<Integer>> adjacencyList;
    private final Map<Integer, Set<Integer>> reverseList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.reverseList = new HashMap<>();
    }

    public void addNode(int nodeID) {
        adjacencyList.putIfAbsent(nodeID, new HashSet<>());
        reverseList.putIfAbsent(nodeID, new HashSet<>());
        // used https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#putIfAbsent-K-V-/ //
    }

    public void addEdge(int fNodeID, int tNodeID) {
        adjacencyList.computeIfAbsent(fNodeID, k -> new HashSet<>()).add(tNodeID);
        reverseList.computeIfAbsent(tNodeID, k -> new HashSet<>()).add(fNodeID);
        //https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#computeIfAbsent-K-java.util.function.Function-//
    }


    public Set<Integer> getNodes(int start) {
        return helper(start, adjacencyList);
    }

    public Set<Integer> getNodesReverse(int start) {
        return helper(start, reverseList);
    }

    private Set<Integer> helper(int start, Map<Integer, Set<Integer>> graph) {
        Set<Integer> ancestors = new HashSet<>();
        Queue<Integer> visit = new LinkedList<>();
        visit.add(start);
        while (!visit.isEmpty()) {
            int current = visit.remove();
            if (!ancestors.contains(current)) {
                ancestors.add(current);
                visit.addAll(graph.get(current));
            }
        }
        return ancestors;
    }
}
