package east.Teacher;

import east.Admin.DataStore;
import east.Login.MainApplication;
import east.Login.LoginUI;
import javax.swing.*;
import java.awt.event.*;

public class TeacherMechanics implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JLabel messageLabel;
    private final MainApplication mainApp;
    private final LoginUI loginUI;

    public TeacherMechanics(JTextField nameField, JPasswordField passwordField, JLabel messageLabel,
                           MainApplication mainApp, LoginUI loginUI) {
        this.nameField = nameField;
        this.passwordField = passwordField;
        this.messageLabel = messageLabel;
        this.mainApp = mainApp;
        this.loginUI = loginUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        DataStore.TeacherData authenticatedTeacher = null;
        for (DataStore.TeacherData teacher : DataStore.getTeachers()) {
            if (teacher.name.equals(name) && teacher.password.equals(password)) {
                authenticatedTeacher = teacher;
                break;
            }
        }

        if (authenticatedTeacher != null) {
            mainApp.setRole("Teacher");
            JOptionPane.showMessageDialog(null, "Login successful for " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
            passwordField.setText("");
            TeacherDashboard dashboard = new TeacherDashboard(mainApp, loginUI, authenticatedTeacher);
            loginUI.getCardPanel().add(dashboard, "Teacher Dashboard");
            loginUI.getCardLayout().show(loginUI.getCardPanel(), "Teacher Dashboard");
            loginUI.getCardPanel().revalidate();
            loginUI.getCardPanel().repaint();
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }
}