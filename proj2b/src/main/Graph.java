package main;

import java.util.*;

public class Graph {
    private final Map<Integer, Set<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(int nodeID) {
        adjacencyList.putIfAbsent(nodeID, new HashSet<>());
    }

    public void addEdge(int fNodeID, int tNodeID) {
        adjacencyList.computeIfAbsent(fNodeID, k -> new HashSet<>()).add(tNodeID);
    }

    public Set<Integer> neighbors(int nodeID) {
        return adjacencyList.getOrDefault(nodeID, new HashSet<>());
    }

    public Set<Integer> getNodes(int start) {
        Set<Integer> visit = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visit.add(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            Set<Integer> neighbors = getNeighbors(current);
            for (int neighbor : neighbors) {
                if (!visit.contains(neighbor)) {
                    visit.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visit;
    }

    public Set<Integer> getNeighbors(int node) {
        return adjacencyList.getOrDefault(node, new HashSet<>());
    }
}
