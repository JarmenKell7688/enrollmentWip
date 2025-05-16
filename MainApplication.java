/*package east.Login;

import east.Student.EnrollmentUI;
import east.Admin.DataStore;
import east.Student.EnrollmentMechanics;
import east.Student.SubjectSelection;
import east.Student.StudentData;
import east.Student.StudentTrappings; // Added import for StudentTrappings
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;

public class MainApplication extends JPanel {
    private static final String LOGIN_PANEL = "Login";
    private static final String ENROLLMENT_PANEL = "Enrollment";
    private static final String SUBJECT_SELECTION_PANEL = "Subject Selection";
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String role;
    private JTextField nameField;
    private JPasswordField passwordField;
    private LoginUI loginUI;
    private StudentData enrolledStudent;

    public MainApplication(String role, LoginUI loginUI) {
        this.role = role;
        this.loginUI = loginUI;

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel enrollmentPanel = createEnrollmentPanel();
        JPanel subjectSelectionPanel = createSubjectSelectionPanel();

        cardPanel.add(loginPanel, LOGIN_PANEL);
        cardPanel.add(enrollmentPanel, ENROLLMENT_PANEL);
        cardPanel.add(subjectSelectionPanel, SUBJECT_SELECTION_PANEL);

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, LOGIN_PANEL);
    }

    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));

        mainPanel.setPreferredSize(new Dimension(350, 250));
        mainPanel.setMaximumSize(new Dimension(350, 250));

        JLabel loginTitle = new JLabel("Student Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(new Color(50, 50, 80));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Student Name:");
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

        Color buttonColor = new Color(70, 130, 180);
        Color buttonHoverColor = buttonColor.brighter();

        JButton loginButton = createStyledButton("Login", buttonColor, buttonHoverColor);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (name.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(MainApplication.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(MainApplication.this, "Login successful for " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                passwordField.setText("");
            }
        });

        JButton cancelButton = createStyledButton("Cancel", buttonColor, buttonHoverColor);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Welcome");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loginButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enrollLink = new JLabel("No account? Enroll here");
        enrollLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        enrollLink.setForeground(new Color(70, 130, 180));
        enrollLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        enrollLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, ENROLLMENT_PANEL);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                enrollLink.setForeground(new Color(34, 139, 34));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                enrollLink.setForeground(new Color(70, 130, 180));
            }
        });

        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        linkPanel.setBackground(new Color(240, 245, 250));
        linkPanel.add(enrollLink);

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
        mainPanel.add(linkPanel);
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

        return wrapperPanel;
    }

    private JPanel createEnrollmentPanel() {
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        EnrollmentUI enrollmentUI = new EnrollmentUI();

        EnrollmentMechanics mechanics = new EnrollmentMechanics(
            enrollmentUI.getNameField(),
            enrollmentUI.getEmailField(),
            enrollmentUI.getPasswordField(),
            enrollmentUI.getProgramComBox(),
            enrollmentUI.getYearComBox(),
            this
        ) {
            private final JTextField nameField = enrollmentUI.getNameField();
            private final JTextField emailField = enrollmentUI.getEmailField();
            private final JPasswordField passwordField = enrollmentUI.getPasswordField();
            private final JComboBox<String> programComBox = enrollmentUI.getProgramComBox();
            private final JComboBox<String> yearComBox = enrollmentUI.getYearComBox();
            private JLabel messageLabel;
            private final StudentTrappings validator = new StudentTrappings(); // Initialize validator

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars).trim();
                String program = (String) programComBox.getSelectedItem();
                String year = (String) yearComBox.getSelectedItem();

                if (name.isEmpty() || email.isEmpty() || passwordChars.length == 0 || program == null || year == null) {
                    messageLabel.setText("Please fill in all fields.");
                    return;
                }

                if (!validator.isNameValid(name)) {
                    messageLabel.setText("Student name must contain letters only.");
                    return;
                }

                if (!email.contains("@") || !email.contains(".")) {
                    messageLabel.setText("Please enter a valid email address.");
                    return;
                }

                try {
                    enrolledStudent = new StudentData(name, email, password, program, Integer.parseInt(year));
                    DataStore.addStudent(enrolledStudent);

                    messageLabel.setText("Enrollment successful!");
                    messageLabel.setForeground(new Color(0, 128, 0));

                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    programComBox.setSelectedIndex(-1);
                    yearComBox.setSelectedIndex(-1);
                    java.util.Arrays.fill(passwordChars, ' ');

                    cardLayout.show(cardPanel, SUBJECT_SELECTION_PANEL);
                } catch (Exception ex) {
                    messageLabel.setText("Error enrolling student: " + ex.getMessage());
                    messageLabel.setForeground(new Color(200, 50, 50));
                    ex.printStackTrace();
                }
            }

            @Override
            public void setMessageLabel(JLabel messageLabel) {
                this.messageLabel = messageLabel;
            }
        };
        mechanics.setMessageLabel(enrollmentUI.getMessageLabel());
        enrollmentUI.getSubmitButton().addActionListener(mechanics);

        enrollmentUI.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, LOGIN_PANEL);
            }
        });

        wrapperPanel.add(enrollmentUI, gbc);
        wrapperPanel.setBackground(new Color(240, 245, 250));

        return wrapperPanel;
    }

    private JPanel createSubjectSelectionPanel() {
        SubjectSelection subjectSelection = new SubjectSelection(this, enrolledStudent);
        return subjectSelection;
    }

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
                BorderFactory.createLineBorder(bgColor.darker(), 2),
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

    public void setRole(String role) {
        this.role = role;
    }

    public StudentData getEnrolledStudent() {
        return enrolledStudent;
    }
}*/

// NEW CODE 16.05.2025 3:42pm

package east.Login;

import east.Student.EnrollmentUI;
import east.Admin.DataStore;
import east.Student.EnrollmentMechanics;
import east.Student.SubjectSelection;
import east.Student.StudentData;
import east.Student.StudentTrappings;
import east.Student.StudentProfile; // Added import for StudentProfile
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;

public class MainApplication extends JPanel {
    private static final String LOGIN_PANEL = "Login";
    private static final String ENROLLMENT_PANEL = "Enrollment";
    private static final String SUBJECT_SELECTION_PANEL = "Subject Selection";
    private static final String STUDENT_PROFILE_PANEL = "Student Profile"; // Added constant for Student Profile panel
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String role;
    private JTextField nameField;
    private JPasswordField passwordField;
    private LoginUI loginUI;
    private StudentData enrolledStudent;
    private SubjectSelection subjectSelection; // Added to hold SubjectSelection instance

    public MainApplication(String role, LoginUI loginUI) {
        this.role = role;
        this.loginUI = loginUI;

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel enrollmentPanel = createEnrollmentPanel();
        subjectSelection = createSubjectSelectionPanel(); // Initialize SubjectSelection
        JPanel studentProfilePanel = createStudentProfilePanel(); // Initialize StudentProfile

        cardPanel.add(loginPanel, LOGIN_PANEL);
        cardPanel.add(enrollmentPanel, ENROLLMENT_PANEL);
        cardPanel.add(subjectSelection, SUBJECT_SELECTION_PANEL);
        cardPanel.add(studentProfilePanel, STUDENT_PROFILE_PANEL); // Add StudentProfile to CardLayout

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, LOGIN_PANEL);
    }

    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));

        mainPanel.setPreferredSize(new Dimension(350, 250));
        mainPanel.setMaximumSize(new Dimension(350, 250));

        JLabel loginTitle = new JLabel("Student Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(new Color(50, 50, 80));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Student Name:");
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

        Color buttonColor = new Color(70, 130, 180);
        Color buttonHoverColor = buttonColor.brighter();

        JButton loginButton = createStyledButton("Login", buttonColor, buttonHoverColor);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (name.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(MainApplication.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the student exists in DataStore
                StudentData student = null;
                for (StudentData s : DataStore.getStudents()) {
                    if (s.getName().equals(name) && s.getEmail() != null && s.getEmail().contains("@") && s.getEmail().contains(".")) {
                        // Assuming password is stored in StudentData (you may need to add a getter for password)
                        // Since StudentData doesn't expose password directly, this is a placeholder
                        // In a real app, you'd hash and compare passwords securely
                        student = s;
                        break;
                    }
                }

                if (student != null) {
                    enrolledStudent = student; // Set the enrolled student
                    JOptionPane.showMessageDialog(MainApplication.this, "Login successful for " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    passwordField.setText("");
                    cardLayout.show(cardPanel, STUDENT_PROFILE_PANEL); // Navigate to Student Profile
                } else {
                    JOptionPane.showMessageDialog(MainApplication.this, "Invalid credentials. Please try again or enroll.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = createStyledButton("Cancel", buttonColor, buttonHoverColor);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Welcome");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loginButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enrollLink = new JLabel("No account? Enroll here");
        enrollLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        enrollLink.setForeground(new Color(70, 130, 180));
        enrollLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        enrollLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, ENROLLMENT_PANEL);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                enrollLink.setForeground(new Color(34, 139, 34));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                enrollLink.setForeground(new Color(70, 130, 180));
            }
        });

        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        linkPanel.setBackground(new Color(240, 245, 250));
        linkPanel.add(enrollLink);

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
        mainPanel.add(linkPanel);
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

        return wrapperPanel;
    }

    private JPanel createEnrollmentPanel() {
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        EnrollmentUI enrollmentUI = new EnrollmentUI();

        EnrollmentMechanics mechanics = new EnrollmentMechanics(
            enrollmentUI.getNameField(),
            enrollmentUI.getEmailField(),
            enrollmentUI.getPasswordField(),
            enrollmentUI.getProgramComBox(),
            enrollmentUI.getYearComBox(),
            this
        ) {
            private final JTextField nameField = enrollmentUI.getNameField();
            private final JTextField emailField = enrollmentUI.getEmailField();
            private final JPasswordField passwordField = enrollmentUI.getPasswordField();
            private final JComboBox<String> programComBox = enrollmentUI.getProgramComBox();
            private final JComboBox<String> yearComBox = enrollmentUI.getYearComBox();
            private JLabel messageLabel;
            private final StudentTrappings validator = new StudentTrappings();

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars).trim();
                String program = (String) programComBox.getSelectedItem();
                String year = (String) yearComBox.getSelectedItem();

                if (name.isEmpty() || email.isEmpty() || passwordChars.length == 0 || program == null || year == null) {
                    messageLabel.setText("Please fill in all fields.");
                    return;
                }

                if (!validator.isNameValid(name)) {
                    messageLabel.setText("Student name must contain letters only.");
                    return;
                }

                if (!email.contains("@") || !email.contains(".")) {
                    messageLabel.setText("Please enter a valid email address.");
                    return;
                }

                try {
                    enrolledStudent = new StudentData(name, email, password, program, Integer.parseInt(year));
                    DataStore.addStudent(enrolledStudent);
                    messageLabel.setText("Enrollment successful!");
                    messageLabel.setForeground(new Color(0, 128, 0));

                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    programComBox.setSelectedIndex(-1);
                    yearComBox.setSelectedIndex(-1);
                    java.util.Arrays.fill(passwordChars, ' ');

                    // Update SubjectSelection with the new student
                    subjectSelection = new SubjectSelection(MainApplication.this, enrolledStudent);
                    cardPanel.remove(cardPanel.getComponentCount() - 2); // Remove old SubjectSelection
                    cardPanel.add(subjectSelection, SUBJECT_SELECTION_PANEL, cardPanel.getComponentCount() - 1);
                    cardLayout.show(cardPanel, SUBJECT_SELECTION_PANEL);
                } catch (Exception ex) {
                    messageLabel.setText("Error enrolling student: " + ex.getMessage());
                    messageLabel.setForeground(new Color(200, 50, 50));
                    ex.printStackTrace();
                }
            }

            @Override
            public void setMessageLabel(JLabel messageLabel) {
                this.messageLabel = messageLabel;
            }
        };
        mechanics.setMessageLabel(enrollmentUI.getMessageLabel());
        enrollmentUI.getSubmitButton().addActionListener(mechanics);

        enrollmentUI.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, LOGIN_PANEL);
            }
        });

        wrapperPanel.add(enrollmentUI, gbc);
        wrapperPanel.setBackground(new Color(240, 245, 250));

        return wrapperPanel;
    }

    private SubjectSelection createSubjectSelectionPanel() {
        return new SubjectSelection(this, enrolledStudent);
    }

    private JPanel createStudentProfilePanel() {
        return new StudentProfile(this, subjectSelection);
    }

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
                BorderFactory.createLineBorder(bgColor.darker(), 2),
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

    public void setRole(String role) {
        this.role = role;
    }

    public StudentData getEnrolledStudent() {
        return enrolledStudent;
    }
}
