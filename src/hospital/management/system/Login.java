package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    JTextField textField;
    JPasswordField jPasswordField;
    JButton loginButton, cancelButton;
    JPanel loginPanel;

    Login() {
        setTitle("Hospital Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- Background ---
        getContentPane().setBackground(new Color(227, 242, 253)); // light blue

        // --- Login Card Panel ---
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(100, 60, 300, 220);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(178, 223, 219), 2, true));
        add(loginPanel);

        // --- Title ---
        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 121, 107)); // dark teal
        title.setBounds(80, 10, 140, 30);
        loginPanel.add(title);

        // --- Username Label ---
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userLabel.setForeground(new Color(51, 51, 51));
        userLabel.setBounds(30, 50, 100, 25);
        loginPanel.add(userLabel);

        // --- Username Field ---
        textField = new JTextField();
        textField.setBounds(120, 50, 150, 25);
        textField.setBackground(new Color(236, 253, 245));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginPanel.add(textField);

        // --- Password Label ---
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passLabel.setForeground(new Color(51, 51, 51));
        passLabel.setBounds(30, 90, 100, 25);
        loginPanel.add(passLabel);

        // --- Password Field ---
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(120, 90, 150, 25);
        jPasswordField.setBackground(new Color(236, 253, 245));
        jPasswordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginPanel.add(jPasswordField);

        // --- Login Button ---
        loginButton = new JButton("Login");
        loginButton.setBounds(40, 150, 100, 30);
        styleButton(loginButton);
        loginPanel.add(loginButton);

        // --- Cancel Button ---
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(160, 150, 100, 30);
        styleButton(cancelButton);
        loginPanel.add(cancelButton);

        // --- Button Actions ---
        loginButton.addActionListener(e -> {
            String user = textField.getText();
            String pass = new String(jPasswordField.getPassword());
            if (user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // --- Button Styling Helper ---
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(0, 150, 136));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 121, 107));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 150, 136));
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
