package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import core.structures.SimpleGraph;
import core.algorithms.GraphAlgorithms;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class GraphVisualizerFrame extends JFrame {
    private SimpleGraph graph;
    private Map<Integer, Point> nodePositions;
    private GraphPanel graphPanel;
    private JTextField fromField, toField, nodeField;

    public GraphVisualizerFrame() {
        graph = new SimpleGraph();
        nodePositions = new HashMap<>();
        setTitle("Graph Operations Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.setFullScreen(this, false);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND_COLOR);

        // === HEADER PANEL ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton backButton = UITheme.createStyledButton("← Back", UITheme.TEXT_SECONDARY_COLOR);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            new NonLinearDSMenuFrame().setVisible(true); // Renamed target
            dispose();
        });

        JLabel titleLabel = UITheme.createTitleLabel("Graph Operations (Directed)");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        graphPanel = new GraphPanel();

        // === CONTROL PANEL ===
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Inputs
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        inputRow.setOpaque(false);
        inputRow.add(new JLabel("Node ID:"));
        nodeField = UITheme.createStyledTextField(6);
        inputRow.add(nodeField);
        inputRow.add(Box.createHorizontalStrut(20));
        inputRow.add(new JLabel("From:"));
        fromField = UITheme.createStyledTextField(6);
        inputRow.add(fromField);
        inputRow.add(new JLabel("To:"));
        toField = UITheme.createStyledTextField(6);
        inputRow.add(toField);

        // Actions
        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        actionRow.setOpaque(false);
        actionRow.add(createActionButton("Add Node", UITheme.PRIMARY_COLOR, e -> addNode()));
        actionRow.add(createActionButton("Add Edge", UITheme.SECONDARY_COLOR, e -> addEdge()));
        actionRow.add(createActionButton("Remove Edge", UITheme.ERROR_COLOR, e -> removeEdge()));

        JPanel traversalRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        traversalRow.setOpaque(false);
        traversalRow.add(createActionButton("BFS", UITheme.ACCENT_COLOR, e -> performBFS()));
        traversalRow.add(createActionButton("DFS", new Color(156, 39, 176), e -> performDFS()));
        traversalRow.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(actionRow);
        controlPanel.add(traversalRow);

        add(headerPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(110, 35));
        btn.addActionListener(action);
        return btn;
    }

    // === LOGIC ===
    private void addNode() {
        try {
            int node = Integer.parseInt(nodeField.getText().trim());
            if (!graph.addNode(node)) {
                JOptionPane.showMessageDialog(this, "Node exists");
                return;
            }
            // Circular positioning
            recalculatePositions();

            graphPanel.repaint();
            nodeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recalculatePositions() {
        int centerX = getWidth() / 2;
        int centerY = (getHeight() - 200) / 2;
        if (graphPanel != null && graphPanel.getWidth() > 0) {
            centerX = graphPanel.getWidth() / 2;
            centerY = graphPanel.getHeight() / 2;
        }
        int radius = 200;
        int i = 0;
        int total = graph.size();
        for (Integer node : graph.getAllNodes()) {
            double angle = (2 * Math.PI * i) / Math.max(1, total) - Math.PI / 2;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            nodePositions.put(node, new Point(x, y));
            i++;
        }
    }

    private void addEdge() {
        try {
            int from = Integer.parseInt(fromField.getText().trim());
            int to = Integer.parseInt(toField.getText().trim());
            try {
                if (graph.addEdge(from, to)) {
                    graphPanel.repaint();
                    fromField.setText("");
                    toField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Edge exists");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integers required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeEdge() {
        try {
            int from = Integer.parseInt(fromField.getText().trim());
            int to = Integer.parseInt(toField.getText().trim());
            if (graph.removeEdge(from, to)) {
                graphPanel.repaint();
                fromField.setText("");
                toField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Edge not found");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integers required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performBFS() {
        if (graph.isEmpty())
            return;
        try {
            int start = Integer.parseInt(nodeField.getText().trim());
            String result = GraphAlgorithms.bfs(graph, start);
            JOptionPane.showMessageDialog(this, "BFS: " + result);
            nodeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Start node required");
        }
    }

    private void performDFS() {
        if (graph.isEmpty())
            return;
        try {
            int start = Integer.parseInt(nodeField.getText().trim());
            String result = GraphAlgorithms.dfs(graph, start);
            JOptionPane.showMessageDialog(this, "DFS: " + result);
            nodeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Start node required");
        }
    }

    private void clear() {
        graph.clear();
        nodePositions.clear();
        graphPanel.repaint();
    }

    class GraphPanel extends JPanel {
        GraphPanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (graph.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "Graph is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);
                return;
            }

            int nodeRadius = 25;
            int diameter = nodeRadius * 2;

            // Edges
            g2d.setStroke(new BasicStroke(2));
            Map<Integer, List<Integer>> adj = graph.getAdjacencyList();
            for (Map.Entry<Integer, List<Integer>> entry : adj.entrySet()) {
                int from = entry.getKey();
                Point fromPos = nodePositions.get(from);
                for (int to : entry.getValue()) {
                    Point toPos = nodePositions.get(to);
                    if (fromPos != null && toPos != null) {
                        g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                        g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);

                        // Arrow
                        double angle = Math.atan2(toPos.y - fromPos.y, toPos.x - fromPos.x);
                        int arrowX = (int) (toPos.x - nodeRadius * Math.cos(angle));
                        int arrowY = (int) (toPos.y - nodeRadius * Math.sin(angle));
                        int[] xPoints = { arrowX, (int) (arrowX - 10 * Math.cos(angle - Math.PI / 6)),
                                (int) (arrowX - 10 * Math.cos(angle + Math.PI / 6)) };
                        int[] yPoints = { arrowY, (int) (arrowY - 10 * Math.sin(angle - Math.PI / 6)),
                                (int) (arrowY - 10 * Math.sin(angle + Math.PI / 6)) };
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    }
                }
            }

            // Nodes
            for (Map.Entry<Integer, Point> entry : nodePositions.entrySet()) {
                int node = entry.getKey();
                Point p = entry.getValue();

                Ellipse2D circle = new Ellipse2D.Double(p.x - nodeRadius, p.y - nodeRadius, diameter, diameter);
                g2d.setColor(UITheme.PRIMARY_COLOR);
                g2d.fill(circle);
                g2d.setColor(Color.WHITE);
                g2d.draw(circle);

                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                String s = String.valueOf(node);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(s, p.x - fm.stringWidth(s) / 2, p.y + fm.getAscent() / 2 - 3);
            }
        }
    }
}
