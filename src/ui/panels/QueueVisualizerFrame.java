package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.border.EmptyBorder;

public class QueueVisualizerFrame extends JFrame {
    private Queue<Integer> queue;
    private QueuePanel queuePanel;
    private JTextField inputField;
    private static final int MAX_SIZE = 12;

    public QueueVisualizerFrame() {
        queue = new LinkedList<>();
        setTitle("Queue Operations Visualizer");
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

        JLabel titleLabel = UITheme.createTitleLabel("Queue Operations (FIFO)");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        queuePanel = new QueuePanel();

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

        actionsRow.add(createActionButton("Enqueue", UITheme.PRIMARY_COLOR, e -> enqueue()));
        actionsRow.add(createActionButton("Dequeue", UITheme.ERROR_COLOR, e -> dequeue()));
        actionsRow.add(createActionButton("Peek", UITheme.SECONDARY_COLOR, e -> peek()));
        actionsRow.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(actionsRow);

        add(headerPanel, BorderLayout.NORTH);
        add(queuePanel, BorderLayout.CENTER);
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
    private void enqueue() {
        try {
            if (queue.size() >= MAX_SIZE) {
                JOptionPane.showMessageDialog(this, "Queue Full! Max size is " + MAX_SIZE, "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int value = Integer.parseInt(inputField.getText().trim());
            queue.offer(value);
            queuePanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valid integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dequeue() {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue Empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        queue.poll();
        queuePanel.repaint();
    }

    private void peek() {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue Empty", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Front: " + queue.peek());
    }

    private void clear() {
        queue.clear();
        queuePanel.repaint();
    }

    // === PANEL ===
    class QueuePanel extends JPanel {
        QueuePanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (queue.isEmpty()) {
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 22));
                String msg = "Queue is empty";
                g2d.drawString(msg, (getWidth() - g2d.getFontMetrics().stringWidth(msg)) / 2, getHeight() / 2);
                return;
            }

            int boxSize = 70;
            int spacing = 15;
            Integer[] elements = queue.toArray(new Integer[0]);

            // Center horizontally
            int totalWidth = elements.length * boxSize + (elements.length - 1) * spacing;
            int startX = (getWidth() - totalWidth) / 2;
            int centerY = getHeight() / 2 - boxSize / 2;

            for (int i = 0; i < elements.length; i++) {
                int x = startX + i * (boxSize + spacing);

                RoundRectangle2D box = new RoundRectangle2D.Double(x, centerY, boxSize, boxSize, 12, 12);
                g2d.setColor(UITheme.SECONDARY_COLOR);
                g2d.fill(box);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));
                String val = String.valueOf(elements[i]);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(val, x + (boxSize - fm.stringWidth(val)) / 2,
                        centerY + (boxSize + fm.getAscent()) / 2 - 4);

                if (i == 0) {
                    g2d.setColor(UITheme.PRIMARY_COLOR);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2d.drawString("FRONT", x + boxSize / 2 - 20, centerY - 10);
                }
                if (i == elements.length - 1) {
                    g2d.setColor(UITheme.ACCENT_COLOR);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    g2d.drawString("REAR", x + boxSize / 2 - 18, centerY + boxSize + 20);
                }
            }
        }
    }
}
