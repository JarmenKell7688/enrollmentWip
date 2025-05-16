package east.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.*;

public class EnrollmentUI extends JPanel {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> programComBox, yearComBox;
    private JLabel enrollmentTitle, passwordLabel, nameLabel, emailLabel, programLabel, yearLabel, messageLabel;
    private JButton submitButton, cancelButton;

    public EnrollmentUI() {
        // Use GridBagLayout for the panel to center the content
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Increase the preferred size of mainPanel for a larger enrollment section
        mainPanel.setPreferredSize(new Dimension(500, 400));
        mainPanel.setMaximumSize(new Dimension(500, 400));

        // Components
        enrollmentTitle = new JLabel("Student Enrollment");
        enrollmentTitle.setFont(new Font("Arial", Font.BOLD, 24));
        enrollmentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel("Student name:");
        nameLabel.setPreferredSize(new Dimension(100, 25));
        nameLabel.setMaximumSize(new Dimension(100, 25));
        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(350, 25));
        nameField.setMaximumSize(new Dimension(350, 25));

        emailLabel = new JLabel("Email:");
        emailLabel.setPreferredSize(new Dimension(100, 25));
        emailLabel.setMaximumSize(new Dimension(100, 25));
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(350, 25));
        emailField.setMaximumSize(new Dimension(350, 25));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 25));
        passwordLabel.setMaximumSize(new Dimension(100, 25));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(350, 25));
        passwordField.setMaximumSize(new Dimension(350, 25));
        
        programLabel = new JLabel("Program");
        programLabel.setPreferredSize(new Dimension(100, 25));
        programLabel.setMaximumSize(new Dimension(100, 25));
        String[] programOptions = {"BSIT", "BSA", "BSN"};
        programComBox = new JComboBox<>(programOptions);
        programComBox.setSelectedIndex(-1);
        programComBox.setPreferredSize(new Dimension(350, 25));
        programComBox.setMaximumSize(new Dimension(350, 25));

        yearLabel = new JLabel("Year Level");
        yearLabel.setPreferredSize(new Dimension(100, 25));
        yearLabel.setMaximumSize(new Dimension(100, 25));
        String[] yearLevels = {"1", "2", "3", "4"};
        yearComBox = new JComboBox<>(yearLevels);
        yearComBox.setSelectedIndex(-1);
        yearComBox.setPreferredSize(new Dimension(350, 25));
        yearComBox.setMaximumSize(new Dimension(350, 25));

        // Message label for displaying feedback
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(new Color(200, 50, 50));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        Color buttonColor = new Color(70, 130, 180);
        Color buttonHoverColor = buttonColor.brighter();

        submitButton = createStyledButton("Submit", buttonColor, buttonHoverColor);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancelButton = createStyledButton("Cancel", buttonColor, buttonHoverColor);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add title to main panel
        mainPanel.add(enrollmentTitle);
        mainPanel.add(Box.createVerticalStrut(20));

        // Name row
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.setBackground(new Color(240, 245, 250));
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(10));
        namePanel.add(nameField);
        namePanel.setMaximumSize(new Dimension(500, 25));
        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        // Email row
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
        emailPanel.setBackground(new Color(240, 245, 250));
        emailPanel.add(emailLabel);
        emailPanel.add(Box.createHorizontalStrut(10));
        emailPanel.add(emailField);
        emailPanel.setMaximumSize(new Dimension(500, 25));
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(emailPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Password row
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setBackground(new Color(240, 245, 250));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(10));
        passwordPanel.add(passwordField);
        passwordPanel.setMaximumSize(new Dimension(500, 25));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Program row
        JPanel programPanel = new JPanel();
        programPanel.setLayout(new BoxLayout(programPanel, BoxLayout.X_AXIS));
        programPanel.setBackground(new Color(240, 245, 250));
        programPanel.add(programLabel);
        programPanel.add(Box.createHorizontalStrut(10));
        programPanel.add(programComBox);
        programPanel.setMaximumSize(new Dimension(500, 25));
        programPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(programPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Year row
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.X_AXIS));
        yearPanel.setBackground(new Color(240, 245, 250));
        yearPanel.add(yearLabel);
        yearPanel.add(Box.createHorizontalStrut(10));
        yearPanel.add(yearComBox);
        yearPanel.setMaximumSize(new Dimension(500, 25));
        yearPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(yearPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Button panel to hold Submit and Cancel side by side
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(cancelButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);

        // Add message label below buttons
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(messageLabel);

        // To center the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(mainPanel, gbc);
    }

    // Getter methods to access components for mechanics
    public JTextField getNameField() { return nameField; }
    public JTextField getEmailField() { return emailField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JComboBox<String> getProgramComBox() { return programComBox; }
    public JComboBox<String> getYearComBox() { return yearComBox; }
    public JButton getSubmitButton() { return submitButton; }
    public JButton getCancelButton() { return cancelButton; }
    public JLabel getMessageLabel() { return messageLabel; }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2d.setColor(hoverColor);
                } else {
                    g2d.setColor(bgColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor.darker());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker()),
                BorderFactory.createEmptyBorder(7, 15, 7, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                button.repaint();
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.repaint();
            }
        });

        return button;
    }
}