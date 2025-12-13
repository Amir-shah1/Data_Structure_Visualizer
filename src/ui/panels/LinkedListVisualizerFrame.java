package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import javax.swing.border.EmptyBorder;

public class LinkedListVisualizerFrame extends JFrame {
    private LinkedList<Integer> linkedList;
    private LinkedListPanel listPanel;
    private JTextField inputField;
    private JTextField positionField;

    public LinkedListVisualizerFrame() {
        linkedList = new LinkedList<>();
        setTitle("LinkedList Operations Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use full-screen for better visualization
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
            new LinearDSMenuFrame().setVisible(true);
            dispose();
        });

        JLabel titleLabel = UITheme.createTitleLabel("Singly LinkedList Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        listPanel = new LinkedListPanel();

        // === CONTROL PANEL ===
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Rows
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setOpaque(false);
        inputRow.add(new JLabel("Value:"));
        inputField = UITheme.createStyledTextField(8);
        inputRow.add(inputField);
        inputRow.add(new JLabel("Position (for InsertAt):"));
        positionField = UITheme.createStyledTextField(8);
        inputRow.add(positionField);

        // Actions Row 1 (Inserts)
        JPanel actionsRow1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionsRow1.setOpaque(false);
        actionsRow1.add(createActionButton("Insert First", UITheme.PRIMARY_COLOR, e -> insertFirst()));
        actionsRow1.add(createActionButton("Insert Last", UITheme.SECONDARY_COLOR, e -> insertLast()));
        actionsRow1.add(createActionButton("Insert At Pos", UITheme.ACCENT_COLOR, e -> insertAtPosition()));

        // Actions Row 2 (Deletes)
        JPanel actionsRow2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionsRow2.setOpaque(false);
        actionsRow2.add(createActionButton("Delete First", UITheme.ERROR_COLOR, e -> deleteFirst()));
        actionsRow2.add(createActionButton("Delete Last", UITheme.ERROR_COLOR, e -> deleteLast()));
        actionsRow2.add(createActionButton("Delete Value", UITheme.ERROR_COLOR, e -> deleteValue()));

        // Actions Row 3 (Utils)
        JPanel actionsRow3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionsRow3.setOpaque(false);
        actionsRow3.add(createActionButton("Search", UITheme.WARNING_COLOR, e -> search()));
        actionsRow3.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(actionsRow1);
        controlPanel.add(actionsRow2);
        controlPanel.add(actionsRow3);

        add(headerPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(130, 40));
        btn.addActionListener(action);
        return btn;
    }

    // === LOGIC ===
    private void insertFirst() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            linkedList.addFirst(value);
            listPanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertLast() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            linkedList.addLast(value);
            listPanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertAtPosition() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            int position = Integer.parseInt(positionField.getText().trim());

            if (position < 0 || position > linkedList.size()) {
                JOptionPane.showMessageDialog(this, "Invalid position", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            linkedList.add(position, value);
            listPanel.repaint();
            inputField.setText("");
            positionField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integers required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFirst() {
        if (linkedList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "List empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        linkedList.removeFirst();
        listPanel.repaint();
    }

    private void deleteLast() {
        if (linkedList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "List empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        linkedList.removeLast();
        listPanel.repaint();
    }

    private void deleteValue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            // java.util.LinkedList remove(Object o) removes the first occurrence
            boolean removed = linkedList.remove(Integer.valueOf(value));

            if (removed) {
                listPanel.repaint();
                inputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, value + " not found in the list", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            int index = linkedList.indexOf(value);
            if (index != -1) {
                JOptionPane.showMessageDialog(this, "Found " + value + " at index " + index);
            } else {
                JOptionPane.showMessageDialog(this, value + " not found");
            }
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clear() {
        linkedList.clear();
        listPanel.repaint();
    }

    // === PANEL ===
    class LinkedListPanel extends JPanel {
        LinkedListPanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (linkedList.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "List is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);

                // Draw NULL
                g2d.setColor(UITheme.ERROR_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                g2d.drawString("HEAD → NULL", (getWidth() - 120) / 2, getHeight() / 2 + 40);
                return;
            }

            int nodeWidth = 90;
            int nodeHeight = 50;
            int arrowLength = 50;
            int startX = 50;
            int startY = 150;
            int nodesPerRow = (getWidth() - 100) / (nodeWidth + arrowLength);
            if (nodesPerRow < 1)
                nodesPerRow = 1;

            // HEAD Label
            g2d.setColor(UITheme.PRIMARY_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2d.drawString("HEAD", startX, startY - 15);

            for (int i = 0; i < linkedList.size(); i++) {
                int row = i / nodesPerRow;
                int col = i % nodesPerRow;
                int x = startX + col * (nodeWidth + arrowLength);
                int y = startY + row * (nodeHeight + 80);

                // Node Body
                RoundRectangle2D nodeBody = new RoundRectangle2D.Double(x, y, nodeWidth, nodeHeight, 15, 15);
                g2d.setColor(new Color(255, 255, 255));
                g2d.fill(nodeBody);
                g2d.setColor(UITheme.PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(nodeBody);

                // Divider
                int dividerX = x + (int) (nodeWidth * 0.7);
                g2d.drawLine(dividerX, y, dividerX, y + nodeHeight);

                // Value
                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                String val = String.valueOf(linkedList.get(i));
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(val, x + (dividerX - x - fm.stringWidth(val)) / 2,
                        y + (nodeHeight + fm.getAscent()) / 2 - 4);

                // Next Pointer Dot
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + nodeWidth - 20, y + nodeHeight / 2 - 4, 8, 8);

                // Arrow
                if (i < linkedList.size() - 1) {
                    if (col < nodesPerRow - 1) {
                        // Straight arrow
                        drawArrow(g2d, x + nodeWidth, y + nodeHeight / 2, x + nodeWidth + arrowLength,
                                y + nodeHeight / 2);
                    } else {
                        // Curved arrow to next row
                        drawCurvedArrow(g2d, x + nodeWidth / 2, y + nodeHeight, x + nodeWidth + arrowLength / 2,
                                y + nodeHeight + 80);
                        // Simplified curve: down and left. Actually just straight down for now implies
                        // continuation
                        // Better visual:
                        g2d.draw(new Path2D.Double()); // placeholder
                        // Actually let's just draw a line down and an arrow to the start of next row
                        int nextRowY = y + nodeHeight + 80;
                        int nextRowX = startX;

                        // Line down
                        g2d.drawLine(x + nodeWidth / 2, y + nodeHeight, x + nodeWidth / 2, y + nodeHeight + 40);
                        // Line left (abstract)
                        // For simplicity in this grid layout, usually we just restart or show a snake.
                        // Let's just draw a line to indicate flow
                    }
                } else {
                    // NULL
                    g2d.setColor(UITheme.ERROR_COLOR);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2d.drawString("NULL", x + nodeWidth + 10, y + nodeHeight / 2 + 5);
                }

                // Index
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g2d.drawString(String.valueOf(i), x + nodeWidth / 2 - 5, y + nodeHeight + 20);
            }
        }

        private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
            g2.setColor(UITheme.TEXT_COLOR);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(x1, y1, x2, y2);
            g2.fillPolygon(new int[] { x2, x2 - 10, x2 - 10 }, new int[] { y2, y2 - 5, y2 + 5 }, 3);
        }

        private void drawCurvedArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
            // Simple down arrow representation for end of row
            g2.setColor(UITheme.TEXT_COLOR);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(x1, y1, x1, y1 + 30);
            g2.fillPolygon(new int[] { x1, x1 - 5, x1 + 5 }, new int[] { y1 + 30, y1 + 25, y1 + 25 }, 3);
        }
    }
}
