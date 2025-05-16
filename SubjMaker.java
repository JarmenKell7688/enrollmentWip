package east.Admin;

import javax.swing.*;

public class SubjMaker {
    public static Integer parseIntegerField(JTextField field, String fieldName, JPanel panel) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, fieldName + " must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static boolean validateInputs(String subject, String time, String sem, String type, String days, String yearLvl, String course, String room) {
        if (subject == null || subject.isEmpty()) return false;
        if (time == null || time.isEmpty()) return false;
        if (sem == null || sem.isEmpty()) return false;
        if (type == null || type.isEmpty()) return false;
        if (days == null || days.isEmpty()) return false;
        if (yearLvl == null || yearLvl.isEmpty()) return false;
        if (course == null || course.isEmpty()) return false;
        if (room == null || room.isEmpty()) return false;
        return true;
    }
}