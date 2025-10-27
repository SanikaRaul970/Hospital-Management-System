package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Update_Patient extends JFrame implements ActionListener {

    JButton btnSave, btnEditBilling, btnBack;
    JTextField txtPatientID, txtName, txtMobile, txtRoom, txtDeposit;
    JComboBox<String> genderBox;

    public Update_Patient() {
        setTitle("Update Patient Information");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== HEADER =====
        JLabel heading = new JLabel("Update Patient Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBounds(220, 20, 400, 40);
        add(heading);

        // ===== FORM FIELDS =====
        JLabel lblID = new JLabel("Patient ID:");
        lblID.setBounds(100, 100, 120, 25);
        add(lblID);

        txtPatientID = new JTextField();
        txtPatientID.setBounds(250, 100, 200, 25);
        add(txtPatientID);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(100, 140, 120, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(250, 140, 200, 25);
        add(txtName);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(100, 180, 120, 25);
        add(lblGender);

        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setBounds(250, 180, 200, 25);
        add(genderBox);

        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(100, 220, 120, 25);
        add(lblMobile);

        txtMobile = new JTextField();
        txtMobile.setBounds(250, 220, 200, 25);
        add(txtMobile);

        JLabel lblRoom = new JLabel("Room:");
        lblRoom.setBounds(100, 260, 120, 25);
        add(lblRoom);

        txtRoom = new JTextField();
        txtRoom.setBounds(250, 260, 200, 25);
        add(txtRoom);

        JLabel lblDeposit = new JLabel("Deposit:");
        lblDeposit.setBounds(100, 300, 120, 25);
        add(lblDeposit);

        txtDeposit = new JTextField();
        txtDeposit.setBounds(250, 300, 200, 25);
        add(txtDeposit);

        // ===== BUTTONS =====
        btnSave = new JButton("Save Update");
        btnSave.setBounds(100, 360, 130, 30);
        btnSave.setBackground(new Color(34, 139, 34));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(this);
        add(btnSave);

        btnEditBilling = new JButton("Edit Billing");
        btnEditBilling.setBounds(260, 360, 130, 30);
        btnEditBilling.setBackground(new Color(70, 130, 180));
        btnEditBilling.setForeground(Color.WHITE);
        btnEditBilling.addActionListener(this);
        add(btnEditBilling);

        btnBack = new JButton("Back to Reception");
        btnBack.setBounds(420, 360, 180, 30);
        btnBack.setBackground(new Color(178, 34, 34));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            JOptionPane.showMessageDialog(this, "Patient details updated successfully (to be implemented).");
            // TODO: Add SQL update logic here
        } else if (e.getSource() == btnEditBilling) {
            JOptionPane.showMessageDialog(this, "Edit Billing functionality coming soon.");
            // TODO: Open Billing window or allow editing billing info
        } else if (e.getSource() == btnBack) {
            setVisible(false);
            new Reception(); // Opens Reception window
        }
    }

    public static void main(String[] args) {
        new Update_Patient();
    }
}
