package east.Login;

import east.Admin.AdminChoice;
import east.Admin.AdminUI;
import east.Admin.EnrolleesUI;
import east.Admin.TeacherMaker;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private JButton loginButton;
    private JLabel loginTitle, roleLabel;
    private JComboBox<String> roleCombox;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private MainApplication studentPanel;
    private AdminUI adminPanel;
    private AdminChoice adminChoicePanel;
    private EnrolleesUI enrolleesPanel;
    private TeacherMaker teacherMakerPanel;

    public LoginUI() {
        // Frame setup
        setTitle("Dashboard System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // Set a larger size to accommodate SubjectMaker

        // Create a panel with CardLayout to hold the pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Main panel with vertical BoxLayout (Welcome panel)
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(240, 245, 250));
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 1), // Plain border without title
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        welcomePanel.setPreferredSize(new Dimension(350, 200));
        welcomePanel.setMaximumSize(new Dimension(350, 200));

        // Components
        loginTitle = new JLabel("Login Panel");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginTitle.setForeground(new Color(50, 50, 80));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        roleLabel = new JLabel("Select Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setPreferredSize(new Dimension(100, 25));
        roleLabel.setMaximumSize(new Dimension(100, 25));
        roleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        roleCombox = new JComboBox<>(new String[]{"student", "teacher", "admin"});
        roleCombox.setPreferredSize(new Dimension(200, 25));
        roleCombox.setMaximumSize(new Dimension(200, 25));
        roleCombox.setAlignmentX(Component.LEFT_ALIGNMENT);
        roleCombox.setSelectedIndex(-1);

        // Use createStyledButton for the loginButton
        loginButton = createStyledButton("Log in", new Color(70, 130, 180), new Color(70, 130, 180).brighter());
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add title to welcome panel
        welcomePanel.add(loginTitle);
        welcomePanel.add(Box.createVerticalStrut(15));

        // Role row
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.X_AXIS));
        rolePanel.setBackground(new Color(240, 245, 250));
        rolePanel.add(roleLabel);
        rolePanel.add(Box.createHorizontalStrut(10));
        rolePanel.add(roleCombox);
        welcomePanel.add(rolePanel);
        welcomePanel.add(Box.createVerticalStrut(30));
        welcomePanel.add(loginButton);

        // Center the welcomePanel
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(welcomePanel, gbc);

        // Create role-specific panels
        studentPanel = new MainApplication("Student", this); // Pass LoginUI instance
        adminPanel = new AdminUI();
        adminChoicePanel = new AdminChoice(this); // Pass LoginUI instance for card navigation
        enrolleesPanel = new EnrolleesUI(this); // Add Enrollees panel
        teacherMakerPanel = new TeacherMaker(this); // Add TeacherMaker panel
        JPanel teacherPanel = new JPanel();
        teacherPanel.add(new JLabel("Teacher interface not yet implemented."));

        // Add panels to CardLayout
        cardPanel.add(wrapperPanel, "Welcome");
        cardPanel.add(studentPanel, "Student Dashboard");
        cardPanel.add(teacherPanel, "Teacher Interface");
        cardPanel.add(adminPanel, "Admin Interface");
        cardPanel.add(adminChoicePanel, "Admin Choice");
        cardPanel.add(enrolleesPanel, "Enrollees");
        cardPanel.add(teacherMakerPanel, "Teacher Maker"); // Add TeacherMaker to CardLayout

        // Add action listener for login button
        loginButton.addActionListener(new LoginMechanics(roleCombox, cardLayout, cardPanel, this));

        // Add cardPanel to frame
        add(cardPanel);

        // Show the Welcome panel initially
        cardLayout.show(cardPanel, "Welcome");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to create styled buttons with curvy edges
    public JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Curvy edges with radius 20
                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor.darker());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Curvy border with radius 20
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

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public MainApplication getStudentPanel() {
        return studentPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}