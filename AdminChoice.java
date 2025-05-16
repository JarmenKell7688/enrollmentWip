package east.Admin;

import east.Login.LoginUI;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminChoice extends JPanel {
    private LoginUI loginUI;

    public AdminChoice(LoginUI loginUI) {
        this.loginUI = loginUI;
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        JPanel choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        choicesPanel.setBackground(new Color(240, 245, 250));
        choicesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
                    "Choose an Action",
                    TitledBorder.CENTER,
                    TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 14),
                    new Color(70, 100, 130)
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JButton subjectMakerButton = loginUI.createStyledButton("Subject Maker", new Color(0, 128, 0), new Color(34, 139, 34));
        JButton assignTeacherButton = loginUI.createStyledButton("Assign Teacher", new Color(0, 100, 0), new Color(0, 150, 0));
        JButton enrolleesButton = loginUI.createStyledButton("Enrollees", new Color(0, 102, 204), new Color(51, 153, 255));

        subjectMakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        assignTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enrolleesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        choicesPanel.add(subjectMakerButton);
        choicesPanel.add(Box.createVerticalStrut(10));
        choicesPanel.add(assignTeacherButton);
        choicesPanel.add(Box.createVerticalStrut(10));
        choicesPanel.add(enrolleesButton);

        JButton returnButton = loginUI.createStyledButton("Return", new Color(102, 102, 102), new Color(150, 150, 150));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        subjectMakerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Admin Interface");
            }
        });

        assignTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Teacher Maker");
            }
        });

        enrolleesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Enrollees");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Welcome");
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 245, 250));
        add(Box.createVerticalGlue());
        add(choicesPanel);
        add(Box.createVerticalStrut(20));
        add(returnButton);
        add(Box.createVerticalGlue());
    }

    private void setupLayout() {
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}