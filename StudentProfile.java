package east.Student;

import east.Admin.DataStore;
import east.Login.MainApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentProfile extends JPanel {
    private MainApplication mainApp;
    private SubjectSelection subjectSelection;
    private DefaultTableModel tableModel;
    private JTable enrolledSubjectsTable;

    public StudentProfile(MainApplication mainApp, SubjectSelection subjectSelection) {
        this.mainApp = mainApp;
        this.subjectSelection = subjectSelection;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();
    }

    private void initializeComponents() {
        // Fetch the enrolled student
        east.Student.StudentData student = mainApp.getEnrolledStudent();

        // Card panel for student info
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw shadow
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);

                // Draw card background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 15, 15);

                // Draw border
                g2d.setColor(new Color(150, 180, 210));
                g2d.drawRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 15, 15);
                g2d.dispose();
            }
        };
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background for custom painting
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        cardPanel.setPreferredSize(new Dimension(0, 250)); // Fixed height for the card

        // Title label
        JLabel titleLabel = new JLabel("Student Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(15));

        // Student information labels with consistent styling
        Font infoFont = new Font("Segoe UI", Font.PLAIN, 16);
        if (student == null) {
            JLabel noDataLabel = new JLabel("No student data available.");
            noDataLabel.setFont(infoFont);
            noDataLabel.setForeground(new Color(200, 50, 50));
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(noDataLabel);
        } else {
            JLabel nameLabel = new JLabel("Name: " + student.getName());
            nameLabel.setFont(infoFont);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(nameLabel);
            cardPanel.add(Box.createVerticalStrut(10));

            JLabel emailLabel = new JLabel("Email: " + student.getEmail());
            emailLabel.setFont(infoFont);
            emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(emailLabel);
            cardPanel.add(Box.createVerticalStrut(10));

            JLabel courseLabel = new JLabel("Course: " + student.getCourse());
            courseLabel.setFont(infoFont);
            courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(courseLabel);
            cardPanel.add(Box.createVerticalStrut(10));

            JLabel yearLabel = new JLabel("Year Level: " + student.getYearLevel());
            yearLabel.setFont(infoFont);
            yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(yearLabel);
            cardPanel.add(Box.createVerticalStrut(10));

            JLabel studentIdLabel = new JLabel("Student ID: " + (student.getStudentId() != null ? student.getStudentId() : "Not Assigned"));
            studentIdLabel.setFont(infoFont);
            studentIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(studentIdLabel);
        }

        // Wrapper panel to center the card with padding
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(new Color(200, 210, 220));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        wrapperPanel.add(cardPanel, gbc);

        // Table for enrolled subjects
        String[] columns = {"EDP Code", "Year Level", "Semester", "Subject", "Type", "Units", "Days", "Time", "Room", "Course"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        enrolledSubjectsTable = new JTable(tableModel);

        enrolledSubjectsTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        enrolledSubjectsTable.setRowHeight(40);
        enrolledSubjectsTable.setShowGrid(true);
        enrolledSubjectsTable.setGridColor(new Color(200, 200, 200));
        enrolledSubjectsTable.setBackground(new Color(245, 245, 245));
        enrolledSubjectsTable.setSelectionBackground(new Color(173, 216, 230));
        enrolledSubjectsTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = enrolledSubjectsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        enrolledSubjectsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 230, 230) : Color.WHITE);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        // Load the selected subject
        DataStore.SubjectData selectedSubject = subjectSelection.getSelectedSubject();
        if (selectedSubject != null) {
            tableModel.addRow(new Object[]{
                selectedSubject.getEdp(), selectedSubject.getYearLevel(), selectedSubject.getSemester(),
                selectedSubject.getSubjectName(), selectedSubject.getType(), selectedSubject.getUnits(),
                selectedSubject.getDays(), selectedSubject.getTime(), selectedSubject.getRoom(), selectedSubject.getCourse()
            });
        }

        JScrollPane tableScrollPane = new JScrollPane(enrolledSubjectsTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainApp.getCardLayout().show(mainApp.getCardPanel(), "Login"); // Navigate to Login panel
            }
        });
        buttonPanel.add(returnButton);

        // Add components to the main panel
        add(wrapperPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}