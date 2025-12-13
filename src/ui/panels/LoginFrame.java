package ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import ui.components.UITheme;

public class LoginFrame extends JFrame {
    private JTextField regNoField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Data Structure Visualizer - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Full-screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        initComponents();
    }

    private void initComponents() {
        // === BACKGROUND PANEL ===
        JPanel mainPanel = new BackgroundPanel("ui/assets/06.jpg");
        add(mainPanel);

        // === FORM PANEL (semi-transparent) ===
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(20, 40, 70, 220)); // Dark blue, mostly opaque
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleLabel = new JLabel("Data Structure Visualizer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(250, 250, 250));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        // Registration Number
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel regNoLabel = new JLabel("Username:");
        regNoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        regNoLabel.setForeground(Color.WHITE);
        formPanel.add(regNoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        regNoField = new JTextField(15);
        regNoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(regNoField, gbc);

        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        loginButton = UITheme.createStyledButton("Login", new Color(40, 80, 200));
        loginButton.setPreferredSize(new Dimension(140, 40));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        formPanel.add(loginButton, gbc);

        // Action Listeners
        loginButton.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());

        // Add formPanel to background panel (centered)
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(formPanel, new GridBagConstraints());
    }

    private void performLogin() {
        String regNo = regNoField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (regNo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both Registration Number and Password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simple validation (you can enhance this)
        if (regNo.length() >= 3 && password.length() >= 3) {
            dispose();
            new MainMenuFrame(regNo).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid credentials. Please try again.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // === BACKGROUND PANEL CLASS ===
    class BackgroundPanel extends JPanel {
        private Image bgImage;

        public BackgroundPanel(String imagePath) {
            try {
                URL imgUrl = getClass().getClassLoader().getResource(imagePath);
                if (imgUrl != null) {
                    bgImage = new ImageIcon(imgUrl).getImage();
                } else {
                    System.out.println("Resource not found: " + imagePath);
                    // Fallback to local source path for development if needed
                    bgImage = new ImageIcon("src/" + imagePath).getImage();
                }
            } catch (Exception e) {
                System.out.println("Background image load error: " + e.getMessage());
            }
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(20, 30, 70)); // fallback dark blue
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    // === MAIN METHOD TO RUN LOGIN ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
