package core.algorithms;

import core.structures.SimpleGraph;
import java.util.*;

public class GraphAlgorithms {

    public static String bfs(SimpleGraph graph, int startNode) {
        if (!graph.hasNode(startNode)) {
            return "Start node invalid";
        }

        StringBuilder sb = new StringBuilder();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        q.offer(startNode);
        visited.add(startNode);

        while (!q.isEmpty()) {
            int curr = q.poll();
            sb.append(curr).append(" ");

            List<Integer> neighbors = graph.getNeighbors(curr);
            // Sort for consistent output if needed, but not strictly required
            // Collections.sort(neighbors);

            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    q.offer(neighbor);
                }
            }
        }
        return sb.toString().trim();
    }

    public static String dfs(SimpleGraph graph, int startNode) {
        if (!graph.hasNode(startNode)) {
            return "Start node invalid";
        }

        StringBuilder sb = new StringBuilder();
        dfsHelper(graph, startNode, new HashSet<>(), sb);
        return sb.toString().trim();
    }

    private static void dfsHelper(SimpleGraph graph, int node, Set<Integer> visited, StringBuilder sb) {
        visited.add(node);
        sb.append(node).append(" ");

        for (int neighbor : graph.getNeighbors(node)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(graph, neighbor, visited, sb);
            }
        }
    }
}
