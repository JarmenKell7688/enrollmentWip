package east.Student;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import east.Admin.DataStore;
import east.Login.MainApplication;

public class EnrollmentMechanics implements ActionListener {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> programComBox, yearComBox;
    private JLabel messageLabel;
    private StudentTrappings validator;
    private MainApplication mainApp;

    public EnrollmentMechanics(JTextField nameField, JTextField emailField, JPasswordField passwordField,
                               JComboBox<String> programComBox, JComboBox<String> yearComBox,
                               MainApplication mainApp) {
        this.nameField = nameField;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.programComBox = programComBox;
        this.yearComBox = yearComBox;
        this.validator = new StudentTrappings();
        this.mainApp = mainApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars).trim();
        String program = (String) programComBox.getSelectedItem();
        String year = (String) yearComBox.getSelectedItem();

        // Check if any field is empty
        if (name.isEmpty() || email.isEmpty() || passwordChars.length == 0 || program == null || year == null) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        // Validate name using StudentTrappings
        if (!validator.isNameValid(name)) {
            messageLabel.setText("Student name must contain letters only.");
            return;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            messageLabel.setText("Please enter a valid email address.");
            return;
        }

        try {
            // Save student to DataStore
            DataStore.addStudent(new StudentData(name, email, password, program, Integer.parseInt(year)));
            
            // Show success message within the panel
            messageLabel.setText("Enrollment successful!");
            messageLabel.setForeground(new Color(0, 128, 0)); // Green for success

            // Clear fields
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            programComBox.setSelectedIndex(-1);
            yearComBox.setSelectedIndex(-1);
            java.util.Arrays.fill(passwordChars, ' ');

            // Navigate to Subject Selection panel after successful enrollment
            mainApp.getCardLayout().show(mainApp.getCardPanel(), "Subject Selection");
        } catch (Exception ex) {
            messageLabel.setText("Error enrolling student: " + ex.getMessage());
            messageLabel.setForeground(new Color(200, 50, 50)); // Red for error
            ex.printStackTrace();
        }
    }

    public void setMessageLabel(JLabel messageLabel) {
        this.messageLabel = messageLabel;
    }
}