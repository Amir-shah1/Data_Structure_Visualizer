package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import core.structures.CircularLinkedList;
import core.structures.CircularNode;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class CircularLinkedListVisualizerFrame extends JFrame {
    private CircularLinkedList list;
    private CircularLinkedListPanel listPanel;
    private JTextField inputField;
    private JTextField positionField;

    public CircularLinkedListVisualizerFrame() {
        list = new CircularLinkedList();
        setTitle("Circular LinkedList Operations Visualizer");
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
            new LinearDSMenuFrame().setVisible(true); // Renamed target
            dispose();
        });

        JLabel titleLabel = UITheme.createTitleLabel("Circular LinkedList Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        listPanel = new CircularLinkedListPanel();

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
        inputRow.add(new JLabel("Position:"));
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
            list.insertFirst(value);
            listPanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertLast() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            list.insertLast(value);
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

            try {
                list.insertAt(position, value);
                listPanel.repaint();
                inputField.setText("");
                positionField.setText("");
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Invalid position", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integers required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFirst() {
        list.deleteFirst();
        listPanel.repaint();
    }

    private void deleteLast() {
        list.deleteLast();
        listPanel.repaint();
    }

    private void deleteValue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            boolean removed = list.delete(value);

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
            int index = list.search(value);
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
        list.clear();
        listPanel.repaint();
    }

    // === PANEL ===
    class CircularLinkedListPanel extends JPanel {
        CircularLinkedListPanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (list.getHead() == null) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "List is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);

                g2d.setColor(UITheme.ERROR_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2d.drawString("HEAD → NULL", (getWidth() - 120) / 2, getHeight() / 2 + 40);
                return;
            }

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2 - 20;
            int radius = Math.min(250, Math.max(150, list.getSize() * 35));
            int nodeWidth = 70;
            int nodeHeight = 50;

            CircularNode current = list.getHead();
            int index = 0;

            // Draw HEAD pointer
            g2d.setColor(UITheme.PRIMARY_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            double firstAngle = -Math.PI / 2;
            int firstX = centerX + (int) (radius * Math.cos(firstAngle)) - nodeWidth / 2;
            int firstY = centerY + (int) (radius * Math.sin(firstAngle)) - nodeHeight / 2;
            g2d.drawString("HEAD", firstX + 15, firstY - 15);

            do {
                double angle = 2 * Math.PI * index / list.getSize() - Math.PI / 2;
                int x = centerX + (int) (radius * Math.cos(angle)) - nodeWidth / 2;
                int y = centerY + (int) (radius * Math.sin(angle)) - nodeHeight / 2;

                // Node Body
                RoundRectangle2D nodeBody = new RoundRectangle2D.Double(x, y, nodeWidth, nodeHeight, 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.fill(nodeBody);
                g2d.setColor(UITheme.SECONDARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(nodeBody);

                // Divider
                int dividerX = x + (int) (nodeWidth * 0.7);
                g2d.drawLine(dividerX, y, dividerX, y + nodeHeight);

                // Value
                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                String value = String.valueOf(current.data);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(value, x + (dividerX - x - fm.stringWidth(value)) / 2,
                        y + (nodeHeight + fm.getAscent()) / 2 - 4);

                // Next Dot
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + nodeWidth - 15, y + nodeHeight / 2 - 4, 8, 8);

                // Pos Label
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String posLabel = "[" + index + "]";
                int labelX = centerX + (int) ((radius + 40) * Math.cos(angle)) - 10;
                int labelY = centerY + (int) ((radius + 40) * Math.sin(angle)) + 5;
                g2d.drawString(posLabel, labelX, labelY);

                // Arrow to next
                double nextAngle = 2 * Math.PI * ((index + 1) % list.getSize()) / list.getSize() - Math.PI / 2;
                int nextX = centerX + (int) (radius * Math.cos(nextAngle)) - nodeWidth / 2;
                int nextY = centerY + (int) (radius * Math.sin(nextAngle)) - nodeHeight / 2;

                // Calculate arrow
                double midAngle = (angle + nextAngle) / 2;
                if (Math.abs(nextAngle - angle) > Math.PI) { // For wrap around case (last to first)
                    midAngle += Math.PI;
                }

                int arrowRadius = radius - 20;
                int arcX = centerX + (int) (arrowRadius * Math.cos(midAngle));
                int arcY = centerY + (int) (arrowRadius * Math.sin(midAngle));

                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.drawLine(x + nodeWidth, y + nodeHeight / 2, arcX, arcY);
                g2d.drawLine(arcX, arcY, nextX, nextY + nodeHeight / 2); // Connect to left side of next node?
                // Ideally connect to closest point. Simple straight lines for now via arc
                // point.

                // Better arrow head logic needed but simplified here
                // Arrow head at destination
                if (list.getSize() > 1) { // Only draw arrow if pointing to another distinct location visually (or back
                                          // to
                    // self if size=1)
                    // ... actually simpler to just draw small arrows.
                }

                current = current.next;
                index++;
            } while (current != list.getHead());

            // Size
            g2d.setColor(UITheme.TEXT_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2d.drawString("Size: " + list.getSize(), 30, 30);
        }
    }
}
