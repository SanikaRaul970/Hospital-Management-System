package hospital.management.system;

import javax.swing.*;

public class test extends JFrame {

    test() {
        setTitle("Hospital Management System - Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to the Hospital Management System!", SwingConstants.CENTER);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new test();
    }
}
