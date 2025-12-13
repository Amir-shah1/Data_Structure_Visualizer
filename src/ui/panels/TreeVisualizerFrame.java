package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import core.structures.BinarySearchTree;
import core.structures.TreeNode;
import core.algorithms.TreeAlgorithms;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.border.EmptyBorder;

public class TreeVisualizerFrame extends JFrame {
    private BinarySearchTree bst;
    private TreePanel treePanel;
    private JTextField inputField;

    public TreeVisualizerFrame() {
        bst = new BinarySearchTree();
        setTitle("Binary Search Tree Operations Visualizer");
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

        JLabel titleLabel = UITheme.createTitleLabel("Binary Search Tree Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        treePanel = new TreePanel();

        // === CONTROL PANEL ===
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Input
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setOpaque(false);
        inputRow.add(new JLabel("Value:"));
        inputField = UITheme.createStyledTextField(8);
        inputRow.add(inputField);

        // Action Rows
        JPanel mainActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        mainActions.setOpaque(false);
        mainActions.add(createActionButton("Insert", UITheme.PRIMARY_COLOR, e -> insert()));
        mainActions.add(createActionButton("Delete", UITheme.ERROR_COLOR, e -> delete()));
        mainActions.add(createActionButton("Search", UITheme.WARNING_COLOR, e -> search()));
        mainActions.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        JPanel travActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        travActions.setOpaque(false);
        travActions.setBorder(BorderFactory.createTitledBorder("Traversals"));
        travActions.add(createActionButton("Inorder", UITheme.SECONDARY_COLOR,
                e -> displayTraversal("Inorder", TreeAlgorithms.inorder(bst.getRoot()))));
        travActions.add(createActionButton("Preorder", UITheme.ACCENT_COLOR,
                e -> displayTraversal("Preorder", TreeAlgorithms.preorder(bst.getRoot()))));
        travActions.add(createActionButton("Postorder", new Color(156, 39, 176),
                e -> displayTraversal("Postorder", TreeAlgorithms.postorder(bst.getRoot()))));

        controlPanel.add(inputRow);
        controlPanel.add(mainActions);
        controlPanel.add(travActions);

        add(headerPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
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
    private void insert() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            bst.insert(value);
            treePanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (bst.search(value)) {
                bst.delete(value);
                treePanel.repaint();
                inputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Not found");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            boolean found = bst.search(value);
            JOptionPane.showMessageDialog(this, found ? "Found " + value : "Not found " + value);
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayTraversal(String type, String result) {
        JOptionPane.showMessageDialog(this, result, type + " Traversal", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clear() {
        bst.clear();
        treePanel.repaint();
    }

    class TreePanel extends JPanel {
        TreePanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bst.getRoot() == null) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "Tree is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);
                return;
            }

            drawTree(g2d, bst.getRoot(), getWidth() / 2, 50, getWidth() / 4);
        }

        private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int xOffset) {
            if (node == null)
                return;

            int nodeRadius = 25;
            int diameter = nodeRadius * 2;

            // Edges
            if (node.left != null) {
                int childX = x - xOffset;
                int childY = y + 80;
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
                drawTree(g2d, node.left, childX, childY, xOffset / 2);
            }

            if (node.right != null) {
                int childX = x + xOffset;
                int childY = y + 80;
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);
                drawTree(g2d, node.right, childX, childY, xOffset / 2);
            }

            // Node
            Ellipse2D circle = new Ellipse2D.Double(x - nodeRadius, y - nodeRadius, diameter, diameter);
            g2d.setColor(UITheme.PRIMARY_COLOR);
            g2d.fill(circle);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(circle);

            // Value
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String value = String.valueOf(node.value);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x - fm.stringWidth(value) / 2;
            int textY = y + fm.getAscent() / 2 - 3;
            g2d.drawString(value, textX, textY);
        }
    }
}