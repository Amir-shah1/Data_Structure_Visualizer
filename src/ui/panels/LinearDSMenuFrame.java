package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class LinearDSMenuFrame extends JFrame {

        public LinearDSMenuFrame() {
                setTitle("Linear Data Structures");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Apply theme full screen
                UITheme.setFullScreen(this, false);

                initComponents();
                setVisible(true); // Ensure visible after init
        }

        private void initComponents() {
                // Main Background
                JPanel mainPanel = UITheme.createGradientPanel();
                mainPanel.setLayout(new GridBagLayout());

                // Content Card
                JPanel cardInfo = UITheme.createCardPanel();
                // Use BorderLayout or similar to hold header + grid + footer
                cardInfo.setLayout(new BorderLayout(30, 30));
                cardInfo.setPreferredSize(new Dimension(800, 600));

                // Header
                JPanel headerPanel = new JPanel(new GridLayout(2, 1));
                headerPanel.setOpaque(false);
                JLabel titleLabel = UITheme.createTitleLabel("Linear Data Structures");
                JLabel subtitleLabel = new JLabel("Select a data structure to visualize operations",
                                SwingConstants.CENTER);
                subtitleLabel.setFont(UITheme.SUBTITLE_FONT);
                subtitleLabel.setForeground(UITheme.TEXT_SECONDARY_COLOR);

                headerPanel.add(titleLabel);
                headerPanel.add(subtitleLabel);

                // Grid Actions
                JPanel gridPanel = new JPanel(new GridLayout(4, 2, 20, 15)); // 4 rows, 2 cols
                gridPanel.setOpaque(false);
                gridPanel.setBorder(new EmptyBorder(10, 50, 10, 50));

                // Add buttons using UITheme factory
                gridPanel.add(
                                createNavButton("Array Operations", UITheme.PRIMARY_COLOR,
                                                e -> openFrame(new ArrayVisualizerFrame())));
                gridPanel.add(createNavButton("Stack Operations", UITheme.SECONDARY_COLOR,
                                e -> openFrame(new StackVisualizerFrame())));
                gridPanel.add(
                                createNavButton("Queue Operations", UITheme.ACCENT_COLOR,
                                                e -> openFrame(new QueueVisualizerFrame())));
                gridPanel.add(createNavButton("Circular Queue", UITheme.WARNING_COLOR,
                                e -> openFrame(new CircularQueueVisualizerFrame())));
                gridPanel.add(
                                createNavButton("Deque Operations", UITheme.PRIMARY_COLOR,
                                                e -> openFrame(new DequeVisualizerFrame())));
                gridPanel.add(createNavButton("Linked List", UITheme.SECONDARY_COLOR,
                                e -> openFrame(new LinkedListVisualizerFrame())));
                gridPanel.add(createNavButton("Doubly Linked List", UITheme.ACCENT_COLOR,
                                e -> openFrame(new DoublyLinkedListVisualizerFrame())));
                gridPanel.add(createNavButton("Circular Linked List", UITheme.WARNING_COLOR,
                                e -> openFrame(new CircularLinkedListVisualizerFrame())));

                // Back Button
                JButton backButton = UITheme.createStyledButton("Back to Main Menu", UITheme.ERROR_COLOR);
                backButton.setPreferredSize(new Dimension(300, 50));
                backButton.addActionListener(e -> {
                        new MainMenuFrame("User").setVisible(true);
                        dispose();
                });

                // Footer Panel for Back Button
                JPanel footerPanel = new JPanel();
                footerPanel.setOpaque(false);
                footerPanel.add(backButton);

                cardInfo.add(headerPanel, BorderLayout.NORTH);
                cardInfo.add(gridPanel, BorderLayout.CENTER);
                cardInfo.add(footerPanel, BorderLayout.SOUTH);

                mainPanel.add(cardInfo);
                setContentPane(mainPanel);
        }

        private JButton createNavButton(String text, Color color, java.awt.event.ActionListener action) {
                JButton btn = UITheme.createStyledButton(text, color);
                // Ensure they fill the grid cells nicely
                btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btn.addActionListener(action);
                return btn;
        }

        private void openFrame(JFrame frame) {
                frame.setVisible(true);
                dispose();
        }
}
