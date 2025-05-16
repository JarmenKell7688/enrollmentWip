package east.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdminTrappings {
    private JComboBox<String> classType, yearLevel, semester, courseChoice;
    private JTextField edpField, subjectField, unitsField, roomField, timeField, dayField;
    private DefaultTableModel tableModel;
    private JTable subjectTable;
    private JPanel mainPanel;

    public AdminTrappings() {
        initializeComponents();
        setupLayout();
        loadSubjects();
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

        edpField = new JTextField(15);
        timeField = new JTextField(15);
        subjectField = new JTextField(15);
        unitsField = new JTextField(15);
        roomField = new JTextField(15);
        dayField = new JTextField(15);
        courseChoice = new JComboBox<>(new String[]{"BSIT", "BSIS", "BSA"});
        classType = new JComboBox<>(new String[]{"LEC", "LAB"});
        yearLevel = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        semester = new JComboBox<>(new String[]{"1", "2"});

        for (JTextField field : new JTextField[]{edpField, timeField, subjectField, unitsField, roomField, dayField}) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            field.setBackground(new Color(240, 245, 250));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            field.setPreferredSize(new Dimension(300, 40));
        }

        for (JComboBox<String> box : new JComboBox[]{courseChoice, classType, yearLevel, semester}) {
            box.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            box.setBackground(new Color(240, 245, 250));
            box.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(150, 180, 210), 2, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            box.setPreferredSize(new Dimension(300, 40));
            box.setSelectedIndex(-1);
        }
    }

    private void setupLayout() {
        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(220, 230, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        inputPanel.setBackground(new Color(220, 230, 240));
        JLabel[] labels = {
                new JLabel("EDP Code:"),
                new JLabel("Year Level:"),
                new JLabel("Semester:"),
                new JLabel("Subject:"),
                new JLabel("Units:"),
                new JLabel("Type:"),
                new JLabel("Day:"),
                new JLabel("Time:"),
                new JLabel("Room:"),
                new JLabel("Courses:")
        };
        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 20));
            label.setForeground(new Color(50, 50, 80));
        }

        inputPanel.add(labels[0]); inputPanel.add(edpField);
        inputPanel.add(labels[1]); inputPanel.add(yearLevel);
        inputPanel.add(labels[2]); inputPanel.add(semester);
        inputPanel.add(labels[3]); inputPanel.add(subjectField);
        inputPanel.add(labels[4]); inputPanel.add(unitsField);
        inputPanel.add(labels[5]); inputPanel.add(classType);
        inputPanel.add(labels[6]); inputPanel.add(dayField);
        inputPanel.add(labels[7]); inputPanel.add(timeField);
        inputPanel.add(labels[8]); inputPanel.add(roomField);
        inputPanel.add(labels[9]); inputPanel.add(courseChoice);

        JScrollPane tableScrollPane = new JScrollPane(subjectTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 2));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void loadSubjects() {
        tableModel.setRowCount(0);
        for (DataStore.SubjectData subject : DataStore.getSubjects()) {
            tableModel.addRow(new Object[]{
                    subject.getEdp(), subject.getYearLevel(), subject.getSemester(), subject.getSubjectName(),
                    subject.getType(), subject.getUnits(), subject.getDays(), subject.getTime(), subject.getRoom(), subject.getCourse()
            });
        }
    }

    public void handleAdd() {
        String edpStr = edpField.getText().trim();
        String subject = subjectField.getText().trim();
        String time = timeField.getText().trim();
        String days = dayField.getText().trim();
        String sem = (String) semester.getSelectedItem();
        String type = (String) classType.getSelectedItem();
        String yearLvl = (String) yearLevel.getSelectedItem();
        String course = (String) courseChoice.getSelectedItem();
        String room = roomField.getText().trim();

        if (edpStr.isEmpty() || yearLvl == null || sem == null || subject.isEmpty() || unitsField.getText().trim().isEmpty() ||
                type == null || days.isEmpty() || time.isEmpty() || room.isEmpty() || course == null) {
            JOptionPane.showMessageDialog(mainPanel, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer edp = SubjMaker.parseIntegerField(edpField, "EDP Code", mainPanel);
        Integer units = SubjMaker.parseIntegerField(unitsField, "Units", mainPanel);
        Integer yearLevelInt;
        try {
            yearLevelInt = Integer.parseInt(yearLvl);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainPanel, "Year Level must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (edp == null || units == null) return;

        if (!SubjMaker.validateInputs(subject, time, sem, type, days, yearLvl, course, room)) {
            JOptionPane.showMessageDialog(mainPanel, "Please fill in all fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DataStore.doesSubjectExist(edp)) {
            JOptionPane.showMessageDialog(mainPanel, "A subject with EDP Code " + edp + " already exists.", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DataStore.addSubject(new DataStore.SubjectData(
                edp, yearLevelInt, sem, subject, type, units, days, time, room, course
        ));
        loadSubjects();
        clearFields();
        JOptionPane.showMessageDialog(mainPanel, "Subject added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleDelete() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "Delete selected subject?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int edp = (int) tableModel.getValueAt(selectedRow, 0);
                DataStore.deleteSubject(edp);
                tableModel.removeRow(selectedRow);
                clearFields();
                JOptionPane.showMessageDialog(mainPanel, "Subject deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "No row selected.", "Delete Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        edpField.setText("");
        subjectField.setText("");
        timeField.setText("");
        unitsField.setText("");
        roomField.setText("");
        dayField.setText("");
        yearLevel.setSelectedIndex(-1);
        semester.setSelectedIndex(-1);
        classType.setSelectedIndex(-1);
        courseChoice.setSelectedIndex(-1);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}