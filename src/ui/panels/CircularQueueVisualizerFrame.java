package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class CircularQueueVisualizerFrame extends JFrame {
    private int[] queue;
    private int front, rear, size;
    private CircularQueuePanel queuePanel;
    private JTextField inputField;
    private static final int MAX_SIZE = 12;

    public CircularQueueVisualizerFrame() {
        queue = new int[MAX_SIZE];
        front = -1;
        rear = -1;
        size = 0;
        setTitle("Circular Queue Operations Visualizer");
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

        JLabel titleLabel = UITheme.createTitleLabel("Circular Queue Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createHorizontalStrut(120), BorderLayout.EAST);

        // === VISUALIZATION PANEL ===
        queuePanel = new CircularQueuePanel();

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

        // Actions Row
        JPanel actionsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionsRow.setOpaque(false);
        actionsRow.add(createActionButton("Enqueue", UITheme.PRIMARY_COLOR, e -> enqueue()));
        actionsRow.add(createActionButton("Dequeue", UITheme.ERROR_COLOR, e -> dequeue()));
        actionsRow.add(createActionButton("Peek", UITheme.ACCENT_COLOR, e -> peek()));
        actionsRow.add(createActionButton("Clear", UITheme.TEXT_SECONDARY_COLOR, e -> clear()));

        controlPanel.add(inputRow);
        controlPanel.add(actionsRow);

        add(headerPanel, BorderLayout.NORTH);
        add(queuePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = UITheme.createStyledButton(text, color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(130, 40));
        btn.addActionListener(action);
        return btn;
    }

    private boolean isFull() {
        return (rear + 1) % MAX_SIZE == front;
    }

    private boolean isEmpty() {
        return front == -1;
    }

    private void enqueue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (isFull()) {
                JOptionPane.showMessageDialog(this, "Queue is full (Max " + MAX_SIZE + ")", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (isEmpty())
                front = 0;
            rear = (rear + 1) % MAX_SIZE;
            queue[rear] = value;
            size++;
            queuePanel.repaint();
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Integer required", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // int value = queue[front]; // Removed 'value' variable
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % MAX_SIZE;
        }
        size--;
        queuePanel.repaint();
    }

    private void peek() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Front: " + queue[front] + " at index " + front);
        }
    }

    private void clear() {
        front = -1;
        rear = -1;
        size = 0;
        queuePanel.repaint();
    }

    class CircularQueuePanel extends JPanel {
        CircularQueuePanel() {
            setBackground(UITheme.BACKGROUND_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = 180;
            int boxSize = 50;

            // Draw circular slots
            for (int i = 0; i < MAX_SIZE; i++) {
                double angle = 2 * Math.PI * i / MAX_SIZE - Math.PI / 2;
                int x = centerX + (int) (radius * Math.cos(angle)) - boxSize / 2;
                int y = centerY + (int) (radius * Math.sin(angle)) - boxSize / 2;

                RoundRectangle2D box = new RoundRectangle2D.Double(x, y, boxSize, boxSize, 10, 10);

                boolean isOccupied = !isEmpty()
                        && (front <= rear ? (i >= front && i <= rear) : (i >= front || i <= rear));

                if (isOccupied) {
                    g2d.setColor(UITheme.PRIMARY_COLOR);
                    g2d.fill(box);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    String val = String.valueOf(queue[i]);
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.drawString(val, x + (boxSize - fm.stringWidth(val)) / 2,
                            y + (boxSize + fm.getAscent()) / 2 - 4);
                } else {
                    g2d.setColor(new Color(230, 230, 230));
                    g2d.fill(box);
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.draw(box);
                }

                // Index
                g2d.setColor(UITheme.TEXT_SECONDARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                String idx = String.valueOf(i);
                int lx = centerX + (int) ((radius + 40) * Math.cos(angle)) - 5;
                int ly = centerY + (int) ((radius + 40) * Math.sin(angle)) + 5;
                g2d.drawString(idx, lx, ly);

                // Front/Rear Markers
                if (!isEmpty()) {
                    if (i == front) {
                        g2d.setColor(UITheme.SECONDARY_COLOR);
                        g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        g2d.drawString("FRONT", x - 10, y - 10);
                    }
                    if (i == rear) {
                        g2d.setColor(UITheme.ACCENT_COLOR);
                        g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        g2d.drawString("REAR", x + boxSize - 20, y + boxSize + 20);
                    }
                }
            }

            // Connectors (Circular line)
            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 9 }, 0));
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            // Info
            g2d.setColor(UITheme.TEXT_COLOR);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2d.drawString(
                    "Size: " + (isEmpty() ? 0 : (front <= rear ? rear - front + 1 : MAX_SIZE - front + rear + 1)), 30,
                    30);
        }
    }
}
