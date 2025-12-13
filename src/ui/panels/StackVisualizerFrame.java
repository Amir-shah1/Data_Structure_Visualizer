package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Stack;
import javax.swing.border.EmptyBorder;

public class StackVisualizerFrame extends JFrame {
    private Stack<Integer> stack;
    private StackPanel stackPanel;
    private JTextField inputField;
    private static final int MAX_SIZE = 12;

    public StackVisualizerFrame() {
        stack = new Stack<>();
        setTitle("Stack Operations Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Full-screen for clearer visualization
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

        JLabel titleLabel = UITheme.createTitleLabel("Stack Operations (LIFO)");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        stackPanel = new StackPanel();

        // === CONTROL PANEL ===
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Input Row
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setOpaque(false);
        inputRow.add(new JLabel("Value:"));
        inputField = UITheme.createStyledTextField(10);
        inputRow.add(inputField);

        // Buttons Row
        JPanel actionsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionsRow.setOpaque(false);

        actionsRow.add(createActionButton("Push (Insert)", UITheme.PRIMARY_COLOR, e -> push()));
        actionsRow.add(createActionButton("Pop (Remove)", UITheme.ERROR_COLOR, e -> pop()));
        actionsRow.add(createActionButton("Peek (Top)", UITheme.SECONDARY_COLOR, e -> peek()));
        actionsRow.add(createActionButton("Clear Stack", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(actionsRow);

        add(headerPanel, BorderLayout.NORTH);
        add(stackPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(160, 45));
        btn.addActionListener(action);
        return btn;
    }

    // === LOGIC ===
    private void push() {
        try {
            if (stack.size() >= MAX_SIZE) {
                JOptionPane.showMessageDialog(this, "Stack Overflow! Maximum size is " + MAX_SIZE, "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int value = Integer.parseInt(inputField.getText().trim());
            stack.push(value);
            stackPanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pop() {
        if (stack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stack Underflow! Stack is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        stack.pop();
        stackPanel.repaint();
    }

    private void peek() {
        if (stack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stack is empty", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int value = stack.peek();
        JOptionPane.showMessageDialog(this, "Top element: " + value);
    }

    private void clear() {
        stack.clear();
        stackPanel.repaint();
    }

    // === PANEL ===
    class StackPanel extends JPanel {
        StackPanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int boxWidth = 200;
            int boxHeight = 50;
            int centerX = getWidth() / 2 - boxWidth / 2;
            int startY = getHeight() - 100;

            if (stack.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "Stack is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);

                // Draw Base
                g2d.setColor(new Color(200, 200, 200));
                g2d.fillRect(centerX - 10, startY, boxWidth + 20, 10);
                return;
            }

            for (int i = 0; i < stack.size(); i++) {
                int y = startY - ((i + 1) * (boxHeight + 5));

                RoundRectangle2D box = new RoundRectangle2D.Double(centerX, y, boxWidth, boxHeight, 10, 10);
                g2d.setColor(new Color(100, 149, 237)); // Cornflower Blue
                g2d.fill(box);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 20));
                String val = String.valueOf(stack.get(i));
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(val, centerX + (boxWidth - fm.stringWidth(val)) / 2,
                        y + (boxHeight + fm.getAscent()) / 2 - 3);

                if (i == stack.size() - 1) {
                    g2d.setColor(UITheme.ACCENT_COLOR);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2d.drawString("← TOP", centerX + boxWidth + 15, y + boxHeight / 2 + 5);
                }
            }

            // Draw Base
            g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
            g2d.fillRect(centerX - 10, startY, boxWidth + 20, 10);
        }
    }
}
