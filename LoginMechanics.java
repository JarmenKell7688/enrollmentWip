package east.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMechanics implements ActionListener {
    private JComboBox<String> roleCombox;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginUI loginUI;

    public LoginMechanics(JComboBox<String> roleCombox, CardLayout cardLayout, JPanel cardPanel, LoginUI loginUI) {
        this.roleCombox = roleCombox;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.loginUI = loginUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedRole = (String) roleCombox.getSelectedItem();
        if (selectedRole != null) {
            switch (selectedRole.toLowerCase()) {
                case "student":
                    cardLayout.show(cardPanel, "Student Dashboard");
                    break;
                case "teacher":
                    cardLayout.show(cardPanel, "Teacher Interface");
                    break;
                case "admin":
                    cardLayout.show(cardPanel, "Admin Choice");
                    break;
                default:
                    JOptionPane.showMessageDialog(loginUI, "Please select a valid role.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            roleCombox.setSelectedIndex(-1); // Reset combo box after selection
        }
    }
}