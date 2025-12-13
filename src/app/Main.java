package app;

import javax.swing.*;
import ui.components.UITheme;
import ui.panels.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Apply app theme and launch the login frame
        UITheme.applyTheme();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}