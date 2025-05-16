package east.Teacher;

import east.Login.MainApplication;
import east.Login.LoginUI;
import javax.swing.*;
import java.awt.*;

public class TeacherUI extends JPanel {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JLabel messageLabel;
    private final MainApplication mainApp;
    private final LoginUI loginUI;

    public TeacherUI(MainApplication mainApp, LoginUI loginUI) {
        this.mainApp = mainApp;
        this.loginUI = loginUI;
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        mainPanel.setPreferredSize(new Dimension(350, 250));
        mainPanel.setMaximumSize(new Dimension(350, 250));

        JLabel loginTitle = new JLabel("Teacher Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(new Color(50, 50, 80));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Teacher Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(new Color(240, 245, 250));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        nameField.setMaximumSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(240, 245, 250));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setMaximumSize(new Dimension(200, 30));

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color buttonColor = new Color(70, 130, 180);
        Color buttonHoverColor = buttonColor.brighter();

        JButton loginButton = mainApp.createStyledButton("Login", buttonColor, buttonHoverColor);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        TeacherMechanics mechanics = new TeacherMechanics(nameField, passwordField, messageLabel, mainApp, loginUI);
        loginButton.addActionListener(mechanics);

        JButton cancelButton = mainApp.createStyledButton("Cancel", buttonColor, buttonHoverColor);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> {
            System.out.println("Cancel button clicked, navigating to Welcome panel");
            loginUI.getCardLayout().show(loginUI.getCardPanel(), "Welcome");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loginButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(loginTitle);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createVerticalGlue());

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(mainPanel, gbc);
        wrapperPanel.setBackground(new Color(240, 245, 250));

        add(wrapperPanel, BorderLayout.CENTER);
    }
}	