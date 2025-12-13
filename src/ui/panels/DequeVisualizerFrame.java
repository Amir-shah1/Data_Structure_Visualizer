package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.border.EmptyBorder;

public class DequeVisualizerFrame extends JFrame {
    private Deque<Integer> deque;
    private DequePanel dequePanel;
    private JTextField inputField;
    private static final int MAX_SIZE = 12;

    public DequeVisualizerFrame() {
        deque = new ArrayDeque<>();
        setTitle("Double Ended Queue (Deque) Visualizer");
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
            new LinearDSMenuFrame().setVisible(true);
            dispose();
        });

        JLabel titleLabel = UITheme.createTitleLabel("Deque (Double-Ended Queue)");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        dequePanel = new DequePanel();

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

        // Actions
        JPanel frontOps = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        frontOps.setOpaque(false);
        frontOps.setBorder(BorderFactory.createTitledBorder("Front Operations"));
        frontOps.add(createActionButton("Add Front", UITheme.PRIMARY_COLOR, e -> addFront()));
        frontOps.add(createActionButton("Remove Front", UITheme.ERROR_COLOR, e -> removeFront()));
        frontOps.add(createActionButton("Peek Front", UITheme.SECONDARY_COLOR, e -> peekFront()));

        JPanel rearOps = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        rearOps.setOpaque(false);
        rearOps.setBorder(BorderFactory.createTitledBorder("Rear Operations"));
        rearOps.add(createActionButton("Add Rear", UITheme.ACCENT_COLOR, e -> addRear()));
        rearOps.add(createActionButton("Remove Rear", UITheme.ERROR_COLOR, e -> removeRear()));
        rearOps.add(createActionButton("Peek Rear", UITheme.SECONDARY_COLOR, e -> peekRear()));

        JPanel utilityOps = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        utilityOps.setOpaque(false);
        utilityOps.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(frontOps);
        controlPanel.add(rearOps);
        controlPanel.add(utilityOps);

        add(headerPanel, BorderLayout.NORTH);
        add(dequePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(110, 35));
        btn.addActionListener(action);
        return btn;
    }

    // === LOGIC ===
    private void addFront() {
        try {
            if (deque.size() >= MAX_SIZE) {
                JOptionPane.showMessageDialog(this, "Deque full", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int val = Integer.parseInt(inputField.getText().trim());
            deque.addFirst(val);
            dequePanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRear() {
        try {
            if (deque.size() >= MAX_SIZE) {
                JOptionPane.showMessageDialog(this, "Deque full", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int val = Integer.parseInt(inputField.getText().trim());
            deque.addLast(val);
            dequePanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeFront() {
        if (deque.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        deque.removeFirst();
        dequePanel.repaint();
    }

    private void removeRear() {
        if (deque.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        deque.removeLast();
        dequePanel.repaint();
    }

    private void peekFront() {
        if (deque.isEmpty())
            return;
        JOptionPane.showMessageDialog(this, "Front: " + deque.peekFirst());
    }

    private void peekRear() {
        if (deque.isEmpty())
            return;
        JOptionPane.showMessageDialog(this, "Rear: " + deque.peekLast());
    }

    private void clear() {
        deque.clear();
        dequePanel.repaint();
    }

    // === PANEL ===
    class DequePanel extends JPanel {
        DequePanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (deque.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "Deque is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);
                return;
            }

            int boxSize = 60;
            int spacing = 15;
            Integer[] elements = deque.toArray(new Integer[0]);
            int totalWidth = elements.length * boxSize + (elements.length - 1) * spacing;
            int startX = (getWidth() - totalWidth) / 2;
            int centerY = getHeight() / 2 - boxSize / 2;

            // Markers
            g2d.setColor(UITheme.PRIMARY_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2d.drawString("FRONT", startX, centerY - 15);
            g2d.setColor(UITheme.ACCENT_COLOR);
            g2d.drawString("REAR", startX + totalWidth - 40, centerY - 15);
            // Actually Real Rear depends on if size > 1.
            // If size == 1, Front and Rear are same.

            for (int i = 0; i < elements.length; i++) {
                int x = startX + i * (boxSize + spacing);

                RoundRectangle2D rect = new RoundRectangle2D.Double(x, centerY, boxSize, boxSize, 10, 10);
                g2d.setColor(new Color(255, 185, 50)); // Amber-ish
                g2d.fill(rect);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(rect);

                g2d.setFont(new Font("Segoe UI", Font.BOLD, 20));
                String s = String.valueOf(elements[i]);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(s, x + (boxSize - fm.stringWidth(s)) / 2, centerY + (boxSize + fm.getAscent()) / 2 - 4);

                // Arrows
                if (i < elements.length - 1) {
                    g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                    // Double arrows
                    int ax = x + boxSize;
                    int ay = centerY + boxSize / 2;
                    g2d.drawLine(ax, ay - 3, ax + spacing, ay - 3);
                    g2d.drawLine(ax, ay + 3, ax + spacing, ay + 3);
                }
            }

            g2d.setColor(UITheme.TEXT_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2d.drawString("Size: " + deque.size(), 30, 30);
        }
    }
}
