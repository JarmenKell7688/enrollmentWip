package east.Teacher;

import east.Admin.DataStore;
import east.Login.MainApplication;
import east.Login.LoginUI;
import east.Student.StudentData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TeacherDashboard extends JPanel {
    private final MainApplication mainApp;
    private final LoginUI loginUI;
    private final DataStore.TeacherData teacher;
    private DefaultTableModel gradingTableModel;
    private JTable gradingTable;
    private JTextField gradeField;
    private JComboBox<String> studentCombo;

    public TeacherDashboard(MainApplication mainApp, LoginUI loginUI, DataStore.TeacherData teacher) {
        this.mainApp = mainApp;
        this.loginUI = loginUI;
        this.teacher = teacher;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();
    }

    private void initializeComponents() {
        // North: Header with welcome message and logout button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Reformat the teacher's name by removing titles like "Prof."
        String displayName = teacher.name;
        if (displayName.startsWith("Prof.")) {
            displayName = displayName.substring("Prof.".length()).trim();
        }

        JLabel welcomeLabel = new JLabel("Faculty Dashboard");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = mainApp.createStyledButton("Logout", new Color(178, 34, 34), new Color(220, 20, 60));
        logoutButton.addActionListener(e -> loginUI.getCardLayout().show(loginUI.getCardPanel(), "Teacher Login"));
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Center: Directly add the Grading panel
        JPanel gradingPanel = createGradingPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(gradingPanel, BorderLayout.CENTER);
    }

    private JPanel createGradingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));

        // Input form for grading
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        inputPanel.setBackground(new Color(240, 245, 250));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentCombo = new JComboBox<>();
        studentCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        studentCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel gradeLabel = new JLabel("Grade (1.0-5.0):");
        gradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gradeField = new JTextField(15);
        gradeField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gradeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton assignGradeButton = mainApp.createStyledButton("Assign Grade", new Color(0, 128, 0), new Color(34, 139, 34));
        assignGradeButton.addActionListener(e -> handleAssignGrade());

        inputPanel.add(studentLabel);
        inputPanel.add(studentCombo);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel());
        inputPanel.add(assignGradeButton);

        // Grading table
        String[] columns = {"Student ID", "Name", "Subject", "Grade", "Remarks"};
        gradingTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gradingTable = new JTable(gradingTableModel);
        gradingTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gradingTable.setRowHeight(40);
        gradingTable.setShowGrid(true);
        gradingTable.setGridColor(new Color(200, 200, 200));
        gradingTable.setBackground(new Color(245, 245, 245));
        gradingTable.setSelectionBackground(new Color(173, 216, 230));
        gradingTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = gradingTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        gradingTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(173, 216, 230) : Color.WHITE);
                c.setForeground(isSelected ? Color.BLACK : new Color(52, 73, 94));
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        refreshGradingTable();
        populateStudentCombo();

        JScrollPane tableScrollPane = new JScrollPane(gradingTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void populateStudentCombo() {
        studentCombo.removeAllItems();
        if (teacher.getSubjectEdp() == null) return;
        for (StudentData student : DataStore.getStudents()) {
            if (student.getEnrolledSubjectEdps().contains(teacher.getSubjectEdp())) {
                studentCombo.addItem(student.getName() + " (" + (student.getStudentId() != null ? student.getStudentId() : "No ID") + ")");
            }
        }
    }

    private void refreshGradingTable() {
        gradingTableModel.setRowCount(0);
        if (teacher.getSubjectEdp() == null) return;

        // Retrieve the subject name for the teacher's subject EDP
        String subjectDisplay = "None";
        for (DataStore.SubjectData subject : DataStore.getSubjects()) {
            if (subject.getEdp() == teacher.getSubjectEdp()) {
                subjectDisplay = subject.getEdp() + " - " + subject.getSubjectName();
                break;
            }
        }

        for (StudentData student : DataStore.getStudents()) {
            if (student.getEnrolledSubjectEdps().contains(teacher.getSubjectEdp())) {
                Vector<Object> row = new Vector<>();
                row.add(student.getStudentId() != null ? student.getStudentId() : "No ID");
                row.add(student.getName());
                row.add(subjectDisplay);
                Double grade = student.getGrades().get(teacher.getSubjectEdp());
                row.add(grade != null ? String.format("%.1f", grade) : "Not Graded");
                row.add(grade != null ? (grade >= 1.0 && grade <= 3.0 ? "PASSED" : "FAILED") : "N/A");
                gradingTableModel.addRow(row);
            }
        }
    }

    private void handleAssignGrade() {
        String selectedStudent = (String) studentCombo.getSelectedItem();
        String gradeText = gradeField.getText().trim();

        if (selectedStudent == null || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student and enter a grade.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double grade;
        try {
            grade = Double.parseDouble(gradeText);
            if (grade < 1.0 || grade > 5.0) {
                JOptionPane.showMessageDialog(this, "Grade must be between 1.0 and 5.0.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the grade.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String studentName = selectedStudent.substring(0, selectedStudent.indexOf(" ("));
        for (StudentData student : DataStore.getStudents()) {
            if (student.getName().equals(studentName) && student.getEnrolledSubjectEdps().contains(teacher.getSubjectEdp())) {
                student.setGrade(teacher.getSubjectEdp(), grade);
                DataStore.saveStudentsToFile();
                refreshGradingTable();
                gradeField.setText("");
                JOptionPane.showMessageDialog(this, "Grade assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Student not found or not enrolled in your subject.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}