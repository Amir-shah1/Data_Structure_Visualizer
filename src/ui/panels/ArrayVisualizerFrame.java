package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class ArrayVisualizerFrame extends JFrame {
    private ArrayList<Integer> array;
    private ArrayPanel arrayPanel;
    private JTextField inputField;
    private JTextField indexField;

    public ArrayVisualizerFrame() {
        array = new ArrayList<>();
        setTitle("Array Operations Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make visualizer frame full-screen for better visualization
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

        JLabel titleLabel = UITheme.createTitleLabel("Array Visualization");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        // spacer for balance
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        arrayPanel = new ArrayPanel();

        // === CONTROL PANEL ===
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Input Row
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setOpaque(false);

        inputRow.add(new JLabel("Value:"));
        inputField = UITheme.createStyledTextField(8);
        inputRow.add(inputField);

        inputRow.add(new JLabel("Index:"));
        indexField = UITheme.createStyledTextField(8);
        inputRow.add(indexField);

        // Action Buttons Row
        JPanel actionsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionsRow.setOpaque(false);

        actionsRow.add(createActionButton("Insert at End", UITheme.PRIMARY_COLOR, e -> insertAtEnd()));
        actionsRow.add(createActionButton("Insert at Index", UITheme.SECONDARY_COLOR, e -> insertAtIndex()));
        actionsRow.add(createActionButton("Delete at Index", UITheme.ERROR_COLOR, e -> deleteAtIndex()));
        actionsRow.add(createActionButton("Search", UITheme.ACCENT_COLOR, e -> search()));
        actionsRow.add(createActionButton("Clear All", UITheme.TEXT_SECONDARY_COLOR, e -> clearArray()));

        controlPanel.add(inputRow);
        controlPanel.add(actionsRow);

        // Add to Frame
        add(headerPanel, BorderLayout.NORTH);
        add(arrayPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(160, 45));
        btn.addActionListener(action);
        return btn;
    }

    // === LOGIC METHODS (Preserved) ===
    private void insertAtEnd() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            array.add(value);
            arrayPanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertAtIndex() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            int index = Integer.parseInt(indexField.getText().trim());

            if (index < 0 || index > array.size()) {
                JOptionPane.showMessageDialog(this, "Invalid index", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            array.add(index, value);
            arrayPanel.repaint();
            inputField.setText("");
            indexField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAtIndex() {
        try {
            int index = Integer.parseInt(indexField.getText().trim());

            if (index < 0 || index >= array.size()) {
                JOptionPane.showMessageDialog(this, "Invalid index", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            array.remove(index);
            arrayPanel.repaint();
            indexField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid index", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            int index = array.indexOf(value);

            if (index != -1) {
                JOptionPane.showMessageDialog(this, "Found " + value + " at index " + index);
            } else {
                JOptionPane.showMessageDialog(this, value + " not found in array");
            }
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearArray() {
        array.clear();
        arrayPanel.repaint();
    }

    // === CUSTOM PAINTING PANEL ===
    class ArrayPanel extends JPanel {
        public ArrayPanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (array.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 24));
                String msg = "Array is empty. Add elements to visualize.";
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
                return;
            }

            int boxSize = 70;
            int spacing = 15;
            int totalWidth = (boxSize + spacing) * array.size();

            // Center the array drawing vertically and start with some margin
            int startX = 50;
            int startY = getHeight() / 2 - boxSize / 2;
            int elementsPerRow = (getWidth() - 100) / (boxSize + spacing);
            if (elementsPerRow < 1)
                elementsPerRow = 1;

            for (int i = 0; i < array.size(); i++) {
                int row = i / elementsPerRow;
                int col = i % elementsPerRow;
                int x = startX + col * (boxSize + spacing);
                int y = startY + row * (boxSize + spacing + 30) - (array.size() > elementsPerRow ? 100 : 0); // adjust y
                                                                                                             // if
                                                                                                             // multi-row

                // Element Background (Gradient or solid)
                RoundRectangle2D box = new RoundRectangle2D.Double(x, y, boxSize, boxSize, 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.fill(box);

                // Border/Highlight
                g2d.setColor(UITheme.PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(box);

                // Value
                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));
                String value = String.valueOf(array.get(i));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (boxSize - fm.stringWidth(value)) / 2;
                int textY = y + (boxSize + fm.getAscent()) / 2 - 4;
                g2d.drawString(value, textX, textY);

                // Index Label
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                String indexStr = String.valueOf(i);
                int indexX = x + (boxSize - g2d.getFontMetrics().stringWidth(indexStr)) / 2;
                g2d.drawString(indexStr, indexX, y + boxSize + 20);
            }
        }
    }
}
