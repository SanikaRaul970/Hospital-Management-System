package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;

public class Register extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton registerButton, cancelButton;

    public Register() {
        setTitle("User Registration");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(227, 242, 253));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 40, 300, 200);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(178, 223, 219), 2, true));
        add(panel);

        JLabel title = new JLabel("Register New User", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(30, 10, 240, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 60, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(110, 60, 150, 25);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 100, 80, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 100, 150, 25);
        panel.add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(40, 150, 100, 30);
        styleButton(registerButton);
        panel.add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(160, 150, 100, 30);
        styleButton(cancelButton);
        panel.add(cancelButton);

        registerButton.addActionListener(this);
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
        if (e.getSource() == registerButton) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try {
                conn c = new conn();
                String query = "INSERT INTO login (ID, pw) VALUES (?, ?)";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, user);
                pst.setString(2, pass);
                pst.executeUpdate();
                pst.close();
                c.connection.close();

                JOptionPane.showMessageDialog(this, "User registered successfully!");
                new Login();
                setVisible(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } else if (e.getSource() == cancelButton) {
            new Login();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
