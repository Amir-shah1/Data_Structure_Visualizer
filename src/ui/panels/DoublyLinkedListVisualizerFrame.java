package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import core.structures.DoublyLinkedList;
import core.structures.DoublyNode;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class DoublyLinkedListVisualizerFrame extends JFrame {
    private DoublyLinkedList list;
    private DoublyLinkedListPanel listPanel;
    private JTextField inputField;
    private JTextField positionField;

    public DoublyLinkedListVisualizerFrame() {
        list = new DoublyLinkedList();
        setTitle("Doubly LinkedList Operations Visualizer");
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

        JLabel titleLabel = UITheme.createTitleLabel("Doubly LinkedList Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        listPanel = new DoublyLinkedListPanel();

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
    class DoublyLinkedListPanel extends JPanel {
        DoublyLinkedListPanel() {
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

                // Draw NULLs
                g2d.setColor(UITheme.ERROR_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2d.drawString("NULL ← HEAD / TAIL → NULL", (getWidth() - 240) / 2, getHeight() / 2 + 40);
                return;
            }

            int nodeWidth = 120;
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
            g2d.drawString("HEAD", startX, startY - 20);

            DoublyNode current = list.getHead();
            int index = 0;
            while (current != null) {
                int row = index / nodesPerRow;
                int col = index % nodesPerRow;
                int x = startX + col * (nodeWidth + arrowLength);
                int y = startY + row * (nodeHeight + 80);

                // Node Body
                RoundRectangle2D nodeBody = new RoundRectangle2D.Double(x, y, nodeWidth, nodeHeight, 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.fill(nodeBody);
                g2d.setColor(UITheme.PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(nodeBody);

                // Dividers
                int div1 = x + 30;
                int div2 = x + nodeWidth - 30;
                g2d.drawLine(div1, y, div1, y + nodeHeight);
                g2d.drawLine(div2, y, div2, y + nodeHeight);

                // Labels
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                g2d.setColor(Color.GRAY);
                g2d.drawString("Prev", x + 5, y + 28);
                g2d.drawString("Next", div2 + 5, y + 28);

                // Value
                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                String val = String.valueOf(current.data);
                g2d.drawString(val, (div1 + div2 - g2d.getFontMetrics().stringWidth(val)) / 2, y + 32);

                // Pointer Dots
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + 10, y + 20, 6, 6); // Prev dot
                g2d.fillOval(x + nodeWidth - 16, y + 20, 6, 6); // Next dot

                // Arrows
                if (current.next != null) {
                    if (col < nodesPerRow - 1) {
                        int ax1 = x + nodeWidth;
                        int ax2 = x + nodeWidth + arrowLength;
                        int ay = y + nodeHeight / 2;

                        // Next arrow (top)
                        g2d.drawLine(ax1, ay - 5, ax2, ay - 5);
                        g2d.fillPolygon(new int[] { ax2, ax2 - 6, ax2 - 6 }, new int[] { ay - 5, ay - 9, ay - 1 }, 3);

                        // Prev arrow (bottom)
                        g2d.drawLine(ax1, ay + 5, ax2, ay + 5);
                        g2d.fillPolygon(new int[] { ax1, ax1 + 6, ax1 + 6 }, new int[] { ay + 5, ay + 1, ay + 9 }, 3);
                    } else {
                        // End of row
                        g2d.setColor(Color.BLACK);
                        g2d.drawLine(x + nodeWidth / 2, y + nodeHeight, x + nodeWidth / 2, y + nodeHeight + 40);
                    }
                } else {
                    // Create TAIL Label
                    g2d.setColor(UITheme.ACCENT_COLOR);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2d.drawString("TAIL", x + nodeWidth + 10, y + 30);
                }

                current = current.next;
                index++;
            }
        }
    }
}
