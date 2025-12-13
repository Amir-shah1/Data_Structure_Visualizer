package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;

public class NonLinearDSMenuFrame extends JFrame {

    public NonLinearDSMenuFrame() {
        setTitle("Non-Linear Data Structures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Apply full screen
        UITheme.setFullScreen(this, false);

        initComponents();
    }

    private void initComponents() {
        // Main Background
        JPanel mainPanel = UITheme.createGradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Content Card
        JPanel cardInfo = UITheme.createCardPanel();
        cardInfo.setLayout(new BorderLayout(30, 30));
        cardInfo.setPreferredSize(new Dimension(800, 600));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);
        JLabel titleLabel = UITheme.createTitleLabel("Non-Linear Data Structures");
        JLabel subtitleLabel = new JLabel("Complex relationships and hierarchies", SwingConstants.CENTER);
        subtitleLabel.setFont(UITheme.SUBTITLE_FONT);
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY_COLOR);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Buttons Panel (Vertical list here as there are only 2 items)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Tree
        JButton treeButton = UITheme.createStyledButton("Tree Operations", UITheme.SECONDARY_COLOR);
        treeButton.setMaximumSize(new Dimension(400, 60));
        treeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        treeButton.addActionListener(e -> {
            TreeVisualizerFrame treeFrame = new TreeVisualizerFrame();
            treeFrame.setVisible(true);
            dispose();
        });

        // Graph
        JButton graphButton = UITheme.createStyledButton("Graph Operations", UITheme.ACCENT_COLOR);
        graphButton.setMaximumSize(new Dimension(400, 60));
        graphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        graphButton.addActionListener(e -> {
            GraphVisualizerFrame graphFrame = new GraphVisualizerFrame();
            graphFrame.setVisible(true);
            dispose();
        });

        buttonPanel.add(treeButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(graphButton);

        // Back Button
        JButton backButton = UITheme.createStyledButton("Back to Main Menu", UITheme.ERROR_COLOR);
        backButton.addActionListener(e -> {
            new MainMenuFrame("User").setVisible(true);
            dispose();
        });

        cardInfo.add(headerPanel, BorderLayout.NORTH);
        cardInfo.add(buttonPanel, BorderLayout.CENTER);
        cardInfo.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(cardInfo);
        add(mainPanel);
    }
}
