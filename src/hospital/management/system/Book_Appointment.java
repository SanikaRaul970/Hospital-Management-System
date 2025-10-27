package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Book_Appointment extends JFrame implements ActionListener {
    JTextField txtPatientID, txtDoctor, txtDate, txtTime, txtReason;
    JButton btnBook;

    Book_Appointment() {
        setTitle("Book Appointment");
        setLayout(null);

        JLabel lblPatientID = new JLabel("Patient ID:");
        lblPatientID.setBounds(80, 80, 120, 30);
        add(lblPatientID);
        txtPatientID = new JTextField();
        txtPatientID.setBounds(220, 80, 200, 30);
        add(txtPatientID);

        JLabel lblDoctor = new JLabel("Doctor Name:");
        lblDoctor.setBounds(80, 130, 120, 30);
        add(lblDoctor);
        txtDoctor = new JTextField();
        txtDoctor.setBounds(220, 130, 200, 30);
        add(txtDoctor);

        JLabel lblDate = new JLabel("Date (YYYY-MM-DD):");
        lblDate.setBounds(80, 180, 150, 30);
        add(lblDate);
        txtDate = new JTextField();
        txtDate.setBounds(220, 180, 200, 30);
        add(txtDate);

        JLabel lblTime = new JLabel("Time (HH:MM):");
        lblTime.setBounds(80, 230, 120, 30);
        add(lblTime);
        txtTime = new JTextField();
        txtTime.setBounds(220, 230, 200, 30);
        add(txtTime);

        JLabel lblReason = new JLabel("Reason:");
        lblReason.setBounds(80, 280, 120, 30);
        add(lblReason);
        txtReason = new JTextField();
        txtReason.setBounds(220, 280, 200, 30);
        add(txtReason);

        btnBook = new JButton("Book Appointment");
        btnBook.setBounds(180, 340, 200, 40);
        btnBook.setBackground(new Color(42, 157, 143));
        btnBook.setForeground(Color.WHITE);
        btnBook.addActionListener(this);
        add(btnBook);

        setSize(550, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String pid = txtPatientID.getText();
        String doc = txtDoctor.getText();
        String date = txtDate.getText();
        String time = txtTime.getText();
        String reason = txtReason.getText();

        try {
            conn c = new conn();
            String query = "INSERT INTO appointment (Patient_ID, Doctor_Name, Date, Time, Reason) VALUES ('" + pid + "','" + doc + "','" + date + "','" + time + "','" + reason + "')";
            c.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
            setVisible(false);
            new Reception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
