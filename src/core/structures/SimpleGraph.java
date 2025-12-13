package core.structures;

import java.util.*;

public class SimpleGraph {
    private Map<Integer, List<Integer>> adjacencyList;

    public SimpleGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public boolean addNode(int node) {
        if (adjacencyList.containsKey(node)) {
            return false;
        }
        adjacencyList.put(node, new ArrayList<>());
        return true;
    }

    public boolean addEdge(int from, int to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException("Nodes must exist");
        }
        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
            return true;
        }
        return false;
    }

    public boolean removeEdge(int from, int to) {
        if (adjacencyList.containsKey(from)) {
            return adjacencyList.get(from).remove(Integer.valueOf(to));
        }
        return false;
    }

    public boolean hasNode(int node) {
        return adjacencyList.containsKey(node);
    }

    public List<Integer> getNeighbors(int node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<Integer> getAllNodes() {
        return adjacencyList.keySet();
    }

    public void clear() {
        adjacencyList.clear();
    }

    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    public int size() {
        return adjacencyList.size();
    }

    // Helper to access raw map if absolutely needed, but prefer methods
    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}
