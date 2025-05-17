package east.Login;

import east.Admin.*;
import east.Teacher.*;
import javax.swing.*;
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
    private AdminSecurityPanel adminSecurityPanel;
    private EnrolleesUI enrolleesPanel;
    private TeacherMaker teacherMakerPanel;
    private TeacherUI teacherLoginPanel;

    public LoginUI() {
        setTitle("Dashboard System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(240, 245, 250));
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        welcomePanel.setPreferredSize(new Dimension(350, 200));
        welcomePanel.setMaximumSize(new Dimension(350, 200));

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

        loginButton = createStyledButton("Proceed", new Color(70, 130, 180), new Color(70, 130, 180).brighter());
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(loginTitle);
        welcomePanel.add(Box.createVerticalStrut(15));

        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.X_AXIS));
        rolePanel.setBackground(new Color(240, 245, 250));
        rolePanel.add(roleLabel);
        rolePanel.add(Box.createHorizontalStrut(10));
        rolePanel.add(roleCombox);
        welcomePanel.add(rolePanel);
        welcomePanel.add(Box.createVerticalStrut(30));
        welcomePanel.add(loginButton);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(welcomePanel, gbc);

        studentPanel = new MainApplication("Student", this);
        adminPanel = new AdminUI();
        adminChoicePanel = new AdminChoice(this);
        adminSecurityPanel = new AdminSecurityPanel(this);
        enrolleesPanel = new EnrolleesUI(this);
        teacherMakerPanel = new TeacherMaker(this);
        teacherLoginPanel = new TeacherUI(studentPanel, this);

        cardPanel.add(wrapperPanel, "Welcome");
        cardPanel.add(studentPanel, "Student Dashboard");
        cardPanel.add(teacherLoginPanel, "Teacher Login");
        cardPanel.add(adminPanel, "Admin Interface");
        cardPanel.add(adminChoicePanel, "Admin Choice");
        cardPanel.add(adminSecurityPanel, "Admin Security");
        cardPanel.add(enrolleesPanel, "Enrollees");
        cardPanel.add(teacherMakerPanel, "Teacher Maker");

        loginButton.addActionListener(new LoginMechanics(roleCombox, cardLayout, cardPanel, this));

        add(cardPanel);
        cardLayout.show(cardPanel, "Welcome");

        setLocationRelativeTo(null);
        setVisible(true);
    }

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
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
                button.repaint();
            }
            @Override
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
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}