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
import java.util.ArrayList;
import java.util.List;

public class SubjectSelection extends JPanel {
    private final MainApplication mainApp;
    private final StudentData student;
    private DefaultTableModel tableModel;
    private JTable subjectTable;
    private List<Integer> selectedEdpCodes;

    public SubjectSelection(MainApplication mainApp, StudentData student) {
        this.mainApp = mainApp;
        this.student = student;
        this.selectedEdpCodes = new ArrayList<>(student.getEnrolledSubjectEdps());
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));

        initializeComponents();

        // Refresh the table when the panel becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadSubjects();
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
                int edpCode = (int) table.getValueAt(row, 0);
                if (selectedEdpCodes.contains(edpCode)) {
                    c.setBackground(new Color(144, 238, 144)); // Light green for selected subjects
                } else if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 230, 230) : Color.WHITE);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        loadSubjects();

        JScrollPane tableScrollPane = new JScrollPane(subjectTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(200, 210, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton selectButton = new JButton("Select Subject");
        selectButton.addActionListener(e -> {
            int selectedRow = subjectTable.getSelectedRow();
            if (selectedRow >= 0) {
                int edpCode = (int) tableModel.getValueAt(selectedRow, 0);
                if (selectedEdpCodes.contains(edpCode)) {
                    selectedEdpCodes.remove(Integer.valueOf(edpCode));
                } else {
                    selectedEdpCodes.add(edpCode);
                }
                // Update student's enrolled subjects immediately
                student.getEnrolledSubjectEdps().clear();
                student.getEnrolledSubjectEdps().addAll(selectedEdpCodes);
                DataStore.updateStudent(student);
                subjectTable.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a subject from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(selectButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            // Revert changes by resetting selectedEdpCodes and updating DataStore
            selectedEdpCodes.clear();
            selectedEdpCodes.addAll(student.getEnrolledSubjectEdps());
            student.getEnrolledSubjectEdps().clear();
            student.getEnrolledSubjectEdps().addAll(selectedEdpCodes);
            DataStore.updateStudent(student);
            subjectTable.repaint();
            mainApp.getCardLayout().show(mainApp.getCardPanel(), "Student Profile");
        });
        buttonPanel.add(cancelButton);

        JButton proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(e -> {
            mainApp.getCardLayout().show(mainApp.getCardPanel(), "Student Profile");
            SwingUtilities.invokeLater(() -> {
                // Refresh StudentProfile table
                Component[] components = mainApp.getCardPanel().getComponents();
                for (Component c : components) {
                    if (c instanceof StudentProfile) {
                        ((StudentProfile) c).refreshTable();
                        break;
                    }
                }
            });
        });
        buttonPanel.add(proceedButton);

        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSubjects() {
        tableModel.setRowCount(0);
        for (DataStore.SubjectData subject : DataStore.getSubjects()) {
            if (subject.getYearLevel() == student.getYearLevel() && subject.getCourse().equals(student.getCourse())) {
                tableModel.addRow(new Object[]{
                    subject.getEdp(), subject.getYearLevel(), subject.getSemester(),
                    subject.getSubjectName(), subject.getType(), subject.getUnits(),
                    subject.getDays(), subject.getTime(), subject.getRoom(), subject.getCourse()
                });
            }
        }
    }

    public List<Integer> getSelectedEdpCodes() {
        return new ArrayList<>(selectedEdpCodes);
    }
}