package east.Admin;

import east.Login.LoginUI;
import east.Student.StudentData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EnrolleesUI extends JPanel {
    private DefaultTableModel tableModel;
    private JTable studentTable;
    private LoginUI loginUI;

    public EnrolleesUI(LoginUI loginUI) {
        this.loginUI = loginUI;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();
    }

    private void initializeComponents() {
        // Table setup
        String[] columns = {"Name", "Email", "Program", "Year Level", "Student ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the Student ID column is editable
            }
        };
        studentTable = new JTable(tableModel);

        // Style the table
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        studentTable.setRowHeight(40);
        studentTable.setShowGrid(true);
        studentTable.setGridColor(new Color(200, 200, 200));
        studentTable.setBackground(new Color(245, 245, 245));
        studentTable.setSelectionBackground(new Color(173, 216, 230));
        studentTable.setSelectionForeground(Color.BLACK);

        // Style table header
        JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Alternating row colors
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Load student data
        loadStudentData();

        // Scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Save button
        JButton saveButton = createStyledButton("Save IDs", new Color(0, 128, 0), new Color(34, 139, 34));
        saveButton.addActionListener(e -> saveStudentIDs());
        buttonPanel.add(saveButton);

        // Return button
        JButton returnButton = createStyledButton("Return", new Color(102, 102, 102), new Color(150, 150, 150));
        returnButton.addActionListener(e -> loginUI.getCardLayout().show(loginUI.getCardPanel(), "Admin Choice"));
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadStudentData() {
        tableModel.setRowCount(0); // Clear existing rows
        for (StudentData student : DataStore.getStudents()) {
            tableModel.addRow(new Object[]{
                    student.getName(),
                    student.getEmail(),
                    student.getCourse(),
                    student.getYearLevel(),
                    student.getStudentId() != null ? student.getStudentId() : ""
            });
        }
    }

    private void saveStudentIDs() {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String name = (String) tableModel.getValueAt(row, 0);
            String email = (String) tableModel.getValueAt(row, 1);
            String studentID = (String) tableModel.getValueAt(row, 4);

            if (studentID != null && !studentID.trim().isEmpty()) {
                DataStore.updateStudentId(name, email, studentID.trim());
            }
        }
        JOptionPane.showMessageDialog(this, "Student IDs saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        loadStudentData(); // Refresh the table
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor.darker());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
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
}