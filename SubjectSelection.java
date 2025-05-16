/*package east.Student;

import east.Admin.DataStore;
import east.Login.MainApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubjectSelection extends JPanel {
    private DefaultTableModel tableModel;
    private JTable subjectTable;
    private MainApplication mainApp;
    private StudentData student;
    private JLabel noSubjectsLabel; // Added label for no subjects message

    public SubjectSelection(MainApplication mainApp, StudentData student) {
        this.mainApp = mainApp;
        this.student = student;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();

        // Add ComponentListener to refresh data when the panel becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }
        });
    }

    private void initializeComponents() {
        String[] columns = {"EDP Code", "Year Level", "Semester", "Subject", "Type", "Units", "Days", "Time", "Room", "Course"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        subjectTable = new JTable(tableModel);

        subjectTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subjectTable.setRowHeight(40);
        subjectTable.setShowGrid(true);
        subjectTable.setGridColor(new Color(200, 200, 200));
        subjectTable.setBackground(new Color(245, 245, 245));
        subjectTable.setSelectionBackground(new Color(173, 216, 230));
        subjectTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = subjectTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        subjectTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane tableScrollPane = new JScrollPane(subjectTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));
        add(tableScrollPane, BorderLayout.CENTER);

        // Add a label for when no subjects are available
        noSubjectsLabel = new JLabel("No subjects available for your course and year level.");
        noSubjectsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        noSubjectsLabel.setForeground(new Color(200, 50, 50)); // Red color for visibility
        noSubjectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noSubjectsLabel.setVisible(false); // Initially hidden
        add(noSubjectsLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton selectButton = createStyledButton("Select Subject", new Color(0, 128, 0), new Color(34, 139, 34));
        selectButton.addActionListener(e -> handleSelect());
        buttonPanel.add(selectButton);

        JButton returnButton = createStyledButton("Return", new Color(102, 102, 102), new Color(150, 150, 150));
        returnButton.addActionListener(e -> mainApp.getCardLayout().show(mainApp.getCardPanel(), "Enrollment"));
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadSubjectData(); // Initial load
    }

    private void loadSubjectData() {
        tableModel.setRowCount(0);
        // Update student from mainApp to ensure we have the latest enrolled student
        this.student = mainApp.getEnrolledStudent();
        boolean hasMatchingSubjects = false;
        if (student != null) {
            for (DataStore.SubjectData subject : DataStore.getSubjects()) {
                if (subject.getCourse().equals(student.getCourse()) &&
                    subject.getYearLevel() == student.getYearLevel()) {
                    tableModel.addRow(new Object[]{
                            subject.getEdp(), subject.getYearLevel(), subject.getSemester(), subject.getSubjectName(),
                            subject.getType(), subject.getUnits(), subject.getDays(), subject.getTime(), subject.getRoom(), subject.getCourse()
                    });
                    hasMatchingSubjects = true;
                }
            }
        }
        // Show or hide the "no subjects" message based on whether matching subjects were found
        noSubjectsLabel.setVisible(!hasMatchingSubjects);
        subjectTable.setVisible(hasMatchingSubjects);
    }

    private void refresh() {
        loadSubjectData();
    }

    private void handleSelect() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow >= 0) {
            String subject = (String) tableModel.getValueAt(selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Selected subject: " + subject, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No subject selected.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
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
*/

//New Code
package east.Student;

import east.Admin.DataStore;
import east.Login.MainApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubjectSelection extends JPanel {
    private DefaultTableModel tableModel;
    private JTable subjectTable;
    private MainApplication mainApp;
    private StudentData student;
    private JLabel noSubjectsLabel; // Added label for no subjects message

    public SubjectSelection(MainApplication mainApp, StudentData student) {
        this.mainApp = mainApp;
        this.student = student;
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));
        initializeComponents();

        // Add ComponentListener to refresh data when the panel becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }
        });
    }

    private void initializeComponents() {
        String[] columns = {"EDP Code", "Year Level", "Semester", "Subject", "Type", "Units", "Days", "Time", "Room", "Course"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        subjectTable = new JTable(tableModel);

        subjectTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subjectTable.setRowHeight(40);
        subjectTable.setShowGrid(true);
        subjectTable.setGridColor(new Color(200, 200, 200));
        subjectTable.setBackground(new Color(245, 245, 245));
        subjectTable.setSelectionBackground(new Color(173, 216, 230));
        subjectTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = subjectTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        subjectTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane tableScrollPane = new JScrollPane(subjectTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));
        add(tableScrollPane, BorderLayout.CENTER);

        // Add a label for when no subjects are available
        noSubjectsLabel = new JLabel("No subjects available for your course and year level.");
        noSubjectsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        noSubjectsLabel.setForeground(new Color(200, 50, 50)); // Red color for visibility
        noSubjectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noSubjectsLabel.setVisible(false); // Initially hidden
        add(noSubjectsLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton selectButton = createStyledButton("Select Subject", new Color(0, 128, 0), new Color(34, 139, 34));
        selectButton.addActionListener(e -> handleSelect());
        buttonPanel.add(selectButton);

        JButton returnButton = createStyledButton("Return", new Color(102, 102, 102), new Color(150, 150, 150));
        returnButton.addActionListener(e -> mainApp.getCardLayout().show(mainApp.getCardPanel(), "Enrollment"));
        buttonPanel.add(returnButton);

        JButton proceedButton = createStyledButton("Proceed", new Color(0, 128, 0), new Color(34, 139, 34));
        proceedButton.addActionListener(e -> mainApp.getCardLayout().show(mainApp.getCardPanel(), "Student Profile"));
        buttonPanel.add(proceedButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadSubjectData(); // Initial load
    }

    private void loadSubjectData() {
        tableModel.setRowCount(0);
        // Update student from mainApp to ensure we have the latest enrolled student
        this.student = mainApp.getEnrolledStudent();
        boolean hasMatchingSubjects = false;
        if (student != null) {
            for (DataStore.SubjectData subject : DataStore.getSubjects()) {
                if (subject.getCourse().equals(student.getCourse()) &&
                    subject.getYearLevel() == student.getYearLevel()) {
                    tableModel.addRow(new Object[]{
                            subject.getEdp(), subject.getYearLevel(), subject.getSemester(), subject.getSubjectName(),
                            subject.getType(), subject.getUnits(), subject.getDays(), subject.getTime(), subject.getRoom(), subject.getCourse()
                    });
                    hasMatchingSubjects = true;
                }
            }
        }
        // Show or hide the "no subjects" message based on whether matching subjects were found
        noSubjectsLabel.setVisible(!hasMatchingSubjects);
        subjectTable.setVisible(hasMatchingSubjects);
    }

    private void refresh() {
        loadSubjectData();
    }

    private void handleSelect() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow >= 0) {
            String subject = (String) tableModel.getValueAt(selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Selected subject: " + subject, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No subject selected.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
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

