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
        if (!adjacencyList.containsKey(nodeID)) {
            adjacencyList.put(nodeID, new HashSet<>());
            reverseList.put(nodeID, new HashSet<>());
        }
    }

    public void addEdge(int fNodeID, int tNodeID) {
        adjacencyList.get(fNodeID).add(tNodeID);
        reverseList.get(tNodeID).add(fNodeID);
        //https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#computeIfAbsent-K-java.util.function.Function-//
    }

    public Set<Integer> getNodes(int start) {
        return helper(start, adjacencyList);
    }

    public Set<Integer> getNodesReverse(int start) {
        return helper(start, reverseList);
    }

    private Set<Integer> helper(int start, Map<Integer, Set<Integer>> graph) {
        Set<Integer> visit = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visit.add(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : graph.getOrDefault(current, Collections.emptySet())) {
                if (!visit.contains(neighbor)) {
                    visit.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visit;
    }
}
