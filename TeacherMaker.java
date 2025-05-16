package east.Admin;

import east.Login.LoginUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeacherMaker extends JPanel {
    private JTextField teacherIdField, teacherNameField, teacherPasswordField;
    private DefaultTableModel tableModel;
    private JTable teacherTable;
    private LoginUI loginUI;

    public TeacherMaker(LoginUI loginUI) {
        this.loginUI = loginUI;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();
    }

    private void initializeComponents() {
        // Input panel for teacher data
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        inputPanel.setBackground(new Color(220, 230, 240));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Teacher ID:");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        idLabel.setForeground(new Color(50, 50, 80));
        teacherIdField = new JTextField(15);
        teacherIdField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        teacherIdField.setBackground(new Color(240, 245, 250));
        teacherIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        teacherIdField.setPreferredSize(new Dimension(300, 40));

        JLabel nameLabel = new JLabel("Teacher Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(new Color(50, 50, 80));
        teacherNameField = new JTextField(15);
        teacherNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        teacherNameField.setBackground(new Color(240, 245, 250));
        teacherNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        teacherNameField.setPreferredSize(new Dimension(300, 40));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        passwordLabel.setForeground(new Color(50, 50, 80));
        teacherPasswordField = new JTextField(15);
        teacherPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        teacherPasswordField.setBackground(new Color(240, 245, 250));
        teacherPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        teacherPasswordField.setPreferredSize(new Dimension(300, 40));

        inputPanel.add(idLabel);
        inputPanel.add(teacherIdField);
        inputPanel.add(nameLabel);
        inputPanel.add(teacherNameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(teacherPasswordField);

        // Table setup
        String[] columns = {"ID", "Teacher Name", "Password"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table is non-editable
            }
        };
        teacherTable = new JTable(tableModel);

        // Style the table
        teacherTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        teacherTable.setRowHeight(40);
        teacherTable.setShowGrid(true);
        teacherTable.setGridColor(new Color(200, 200, 200));
        teacherTable.setBackground(new Color(245, 245, 245));
        teacherTable.setSelectionBackground(new Color(173, 216, 230));
        teacherTable.setSelectionForeground(Color.BLACK);

        // Style table header
        JTableHeader header = teacherTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Alternating row colors
        teacherTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Load teacher data
        loadTeacherData();

        // Scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(teacherTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Save button
        JButton saveButton = loginUI.createStyledButton("Save", new Color(0, 128, 0), new Color(34, 139, 34));
        saveButton.addActionListener(e -> handleSave());
        buttonPanel.add(saveButton);

        // Delete Selected button
        JButton deleteButton = loginUI.createStyledButton("Delete Selected", new Color(178, 34, 34), new Color(220, 20, 60));
        deleteButton.addActionListener(e -> handleDelete());
        buttonPanel.add(deleteButton);

        // Return button
        JButton returnButton = loginUI.createStyledButton("Return", new Color(102, 102, 102), new Color(150, 150, 150));
        returnButton.addActionListener(e -> loginUI.getCardLayout().show(loginUI.getCardPanel(), "Admin Choice"));
        buttonPanel.add(returnButton);

        // Add components to the main panel
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTeacherData() {
        tableModel.setRowCount(0); // Clear existing rows
        for (DataStore.TeacherData teacher : DataStore.getTeachers()) {
            tableModel.addRow(new Object[]{teacher.id, teacher.name, teacher.password});
        }
    }

    private void handleSave() {
        String teacherId = teacherIdField.getText().trim();
        String teacherName = teacherNameField.getText().trim();
        String teacherPassword = teacherPasswordField.getText().trim();

        // Validate inputs
        if (teacherId.isEmpty() || teacherName.isEmpty() || teacherPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate ID
        if (DataStore.doesTeacherExist(teacherId)) {
            JOptionPane.showMessageDialog(this, "Teacher ID already exists", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add to DataStore
        DataStore.addTeacher(new DataStore.TeacherData(teacherId, teacherName, teacherPassword));
        loadTeacherData();
        clearFields();
        JOptionPane.showMessageDialog(this, "Teacher saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleDelete() {
        int selectedRow = teacherTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected teacher?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                DataStore.deleteTeacher(id);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No row selected.", "Delete Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        teacherIdField.setText("");
        teacherNameField.setText("");
        teacherPasswordField.setText("");
    }

    public JPanel getMainPanel() {
        return this;
    }
}