package east.Admin;

import east.Login.LoginUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminUI extends JPanel {
    private AdminTrappings trappings;

    public AdminUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 210, 220));

        try {
            // Initialize AdminTrappings and add its main panel
            trappings = new AdminTrappings();
            add(trappings.getMainPanel(), BorderLayout.CENTER);

            // Add a button panel with redesigned buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
            buttonPanel.setBackground(new Color(200, 210, 220));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

            // Add Subject button
            JButton addButton = createStyledButton("Add Subject", new Color(0, 128, 0), // Green
                    new Color(34, 139, 34)); // Darker green hover
            addButton.addActionListener(e -> trappings.handleAdd());
            buttonPanel.add(addButton);

            // Delete Selected button
            JButton deleteButton = createStyledButton("Delete Selected", new Color(178, 34, 34), // Red
                    new Color(220, 20, 60)); // Darker red hover
            deleteButton.addActionListener(e -> trappings.handleDelete());
            buttonPanel.add(deleteButton);

            // Return button (renamed from Logout)
            JButton returnButton = createStyledButton("Return", new Color(102, 102, 102), // Gray
                    new Color(150, 150, 150)); // Darker gray hover
            returnButton.addActionListener(e -> {
                LoginUI loginUI = (LoginUI) SwingUtilities.getWindowAncestor(AdminUI.this);
                loginUI.getCardLayout().show(loginUI.getCardPanel(), "Admin Choice");
            });
            buttonPanel.add(returnButton);

            add(buttonPanel, BorderLayout.SOUTH);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing Admin Trappings: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
                g2d.setColor(bgColor.darker()); // Match the darker border style of the "Log in" button
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2), // Thicker border to match image
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