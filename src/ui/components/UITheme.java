package ui.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UITheme {

    // === COLOR PALETTE (Modern Deep Blue / Tech Theme) ===
    public static final Color BACKGROUND_COLOR = new Color(245, 247, 250); // Light Gray-Blue
    public static final Color PRIMARY_COLOR = new Color(41, 98, 255); // Vibrant Blue
    public static final Color SECONDARY_COLOR = new Color(0, 150, 136); // Teal
    public static final Color ACCENT_COLOR = new Color(255, 87, 34); // Deep Orange
    public static final Color TEXT_COLOR = new Color(33, 33, 33); // Dark Gray
    public static final Color TEXT_SECONDARY_COLOR = new Color(117, 117, 117); // Medium Gray
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color SUCCESS_COLOR = new Color(76, 175, 80); // Green
    public static final Color WARNING_COLOR = new Color(255, 193, 7); // Amber
    public static final Color ERROR_COLOR = new Color(211, 47, 47); // Red

    // === FONTS ===
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Customize global defaults if needed, but we will mostly use custom components
            UIManager.put("Label.font", BODY_FONT);
            UIManager.put("Button.font", BUTTON_FONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFullScreen(JFrame frame, boolean undecorated) {
        // frame.setUndecorated(undecorated); // Optional: if user wants true borderless
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // === COMPONENT FACTORIES ===

    public static JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(baseColor.brighter());
                } else {
                    g2.setColor(baseColor);
                }

                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        return button;
    }

    // Kept for backward compatibility during refactor, redirects to new method
    public static void styleButton(JButton b, Color bgColor) {
        // This modifies an existing button to match the style as closely as possible
        // without replacing it
        b.setBackground(bgColor);
        b.setForeground(Color.WHITE);
        b.setFont(BUTTON_FONT);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(bgColor.brighter());
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(bgColor);
            }
        });
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BACKGROUND);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 244, 252), 0, getHeight(),
                        new Color(225, 230, 245));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    public static JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        return field;
    }
}
