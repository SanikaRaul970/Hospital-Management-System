package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, cancelButton;

    Login() {
        setTitle("Hospital Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(227, 242, 253)); // light blue

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(100, 60, 300, 220);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(178, 223, 219), 2, true));
        add(loginPanel);

        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 121, 107)); // dark teal
        title.setBounds(80, 10, 140, 30);
        loginPanel.add(title);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userLabel.setBounds(30, 50, 100, 25);
        loginPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 50, 150, 25);
        usernameField.setBackground(new Color(236, 253, 245));
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passLabel.setBounds(30, 90, 100, 25);
        loginPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 150, 25);
        passwordField.setBackground(new Color(236, 253, 245));
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(40, 150, 100, 30);
        styleButton(loginButton);
        loginPanel.add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(160, 150, 100, 30);
        styleButton(cancelButton);
        loginPanel.add(cancelButton);

        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(0, 150, 136));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 121, 107));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 150, 136));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            try {
                conn c = new conn();
                String query = "SELECT * FROM login WHERE ID='" + user + "' AND pw='" + pass + "'";
                ResultSet rs = c.statement.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    new Reception();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == cancelButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
