package ui.panels;

import javax.swing.*;
import ui.components.UITheme;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class MainMenuFrame extends JFrame {
    private String userName;

    public MainMenuFrame(String regNo) {
        this.userName = regNo;
        setTitle("Data Structure Visualizer - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Apply full screen mode from theme
        UITheme.setFullScreen(this, false);

        initComponents();
    }

    private void initComponents() {
        // Main container with gradient background
        JPanel mainPanel = UITheme.createGradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Content Card
        JPanel cardInfo = UITheme.createCardPanel();
        cardInfo.setLayout(new BorderLayout(30, 30));
        cardInfo.setPreferredSize(new Dimension(800, 600));

        // Header Section
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setOpaque(false);

        JLabel titleLabel = UITheme.createTitleLabel("Welcome, " + userName);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Explore and visualize data structures interactively", SwingConstants.CENTER);
        subtitleLabel.setFont(UITheme.SUBTITLE_FONT);
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY_COLOR);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Buttons Section
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        JButton btnLinear = UITheme.createStyledButton("Linear Data Structures", UITheme.PRIMARY_COLOR);
        btnLinear.addActionListener(e -> {
            new LinearDSMenuFrame().setVisible(true);
            dispose();
        });

        JButton btnNonLinear = UITheme.createStyledButton("Non-Linear Data Structures", UITheme.SECONDARY_COLOR);
        btnNonLinear.addActionListener(e -> {
            new NonLinearDSMenuFrame().setVisible(true);
            dispose();
        });

        JButton btnExit = UITheme.createStyledButton("Exit Application", UITheme.ERROR_COLOR);
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonPanel.add(btnLinear);
        buttonPanel.add(btnNonLinear);
        buttonPanel.add(btnExit);

        // Footer
        JLabel footerLabel = new JLabel("Designed for Data Structure Visualization", SwingConstants.CENTER);
        footerLabel.setFont(UITheme.BODY_FONT);
        footerLabel.setForeground(new Color(150, 150, 150));

        // Assemble Card
        cardInfo.add(headerPanel, BorderLayout.NORTH);
        cardInfo.add(buttonPanel, BorderLayout.CENTER);
        cardInfo.add(footerLabel, BorderLayout.SOUTH);

        mainPanel.add(cardInfo);
        setContentPane(mainPanel);
    }
}