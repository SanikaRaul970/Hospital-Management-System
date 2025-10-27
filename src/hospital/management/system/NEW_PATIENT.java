package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NEW_PATIENT extends JFrame implements ActionListener {

    JComboBox<String> comboID, comboRoom;
    JTextField txtNumber, txtName, txtMobile, txtDeposit;
    JRadioButton male, female;
    JLabel lblTime;
    JButton btnAdd, btnCancel, btnBack;

    NEW_PATIENT() {

        setTitle("Add New Patient - Hospital Management System");
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(163, 203, 204));

        JLabel title = new JLabel("Add New Patient Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(290, 20, 400, 40);
        add(title);

        JLabel lblIDType = new JLabel("ID Type:");
        lblIDType.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIDType.setBounds(150, 100, 150, 30);
        add(lblIDType);

        String[] idTypes = {"Aadhar Card", "Passport", "Voter ID", "Driving License"};
        comboID = new JComboBox<>(idTypes);
        comboID.setBounds(300, 100, 200, 30);
        add(comboID);

        JLabel lblIDNumber = new JLabel("ID Number:");
        lblIDNumber.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIDNumber.setBounds(150, 150, 150, 30);
        add(lblIDNumber);

        txtNumber = new JTextField();
        txtNumber.setBounds(300, 150, 200, 30);
        add(txtNumber);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setBounds(150, 200, 150, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(300, 200, 200, 30);
        add(txtName);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGender.setBounds(150, 250, 150, 30);
        add(lblGender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(300, 250, 80, 30);
        female.setBounds(380, 250, 100, 30);
        male.setBackground(new Color(163, 203, 204));
        female.setBackground(new Color(163, 203, 204));
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        add(male);
        add(female);

        JLabel lblRoom = new JLabel("Room Number:");
        lblRoom.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRoom.setBounds(150, 300, 150, 30);
        add(lblRoom);

        comboRoom = new JComboBox<>();
        comboRoom.setBounds(300, 300, 200, 30);
        add(comboRoom);

        // Load available rooms into dropdown
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT room_no FROM room WHERE LOWER(availability) = 'available'");
            boolean found = false;
            while (rs.next()) {
                comboRoom.addItem(rs.getString("room_no"));
                found = true;
            }
            if (!found) {
                comboRoom.addItem("No Rooms Available");
                comboRoom.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading room data: " + e.getMessage());
        }

        JLabel lblTimeLabel = new JLabel("Check-in Time:");
        lblTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTimeLabel.setBounds(150, 350, 150, 30);
        add(lblTimeLabel);

        lblTime = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTime.setBounds(300, 350, 300, 30);
        add(lblTime);

        JLabel lblMobile = new JLabel("Mobile Number:");
        lblMobile.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMobile.setBounds(150, 400, 150, 30);
        add(lblMobile);

        txtMobile = new JTextField();
        txtMobile.setBounds(300, 400, 200, 30);
        add(txtMobile);

        JLabel lblDeposit = new JLabel("Deposit Amount:");
        lblDeposit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDeposit.setBounds(150, 450, 150, 30);
        add(lblDeposit);

        txtDeposit = new JTextField();
        txtDeposit.setBounds(300, 450, 200, 30);
        add(txtDeposit);

        // ===== Buttons =====
        btnAdd = new JButton("Add Patient");
        btnAdd.setBounds(430, 500, 150, 35);
        styleButton(btnAdd);
        add(btnAdd);

        btnCancel = new JButton("Clear Form");
        btnCancel.setBounds(600, 500, 150, 35);
        styleButton(btnCancel);
        add(btnCancel);

        btnBack = new JButton("Back to Reception");
        btnBack.setBounds(150, 500, 200, 35);
        styleButton(btnBack);
        add(btnBack);

        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);
        btnBack.addActionListener(this);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(42, 157, 143));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(25, 111, 101));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(42, 157, 143));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addPatient();
        } else if (e.getSource() == btnCancel) {
            clearForm();
        } else if (e.getSource() == btnBack) {
            new Reception();
            dispose();
        }
    }

    private void addPatient() {
        try {
            String idType = (String) comboID.getSelectedItem();
            String idNumber = txtNumber.getText();
            String name = txtName.getText();
            String gender = male.isSelected() ? "Male" : "Female";
            String room = (String) comboRoom.getSelectedItem();
            String time = lblTime.getText();
            String mobile = txtMobile.getText();
            String deposit = txtDeposit.getText();

            if (idNumber.isEmpty() || name.isEmpty() || mobile.isEmpty() || deposit.isEmpty() || room == null) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!");
                return;
            }

            conn c = new conn();
            String query = "INSERT INTO patient_info (ID_Type, ID, Number, Name, Gender, Room, Time, Mobile_Number, Deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, idType);
            pst.setString(2, idNumber);
            pst.setString(3, idNumber);
            pst.setString(4, name);
            pst.setString(5, gender);
            pst.setString(6, room);
            pst.setString(7, time);
            pst.setString(8, mobile);
            pst.setString(9, deposit);
            pst.executeUpdate();

            // Mark room as occupied
            String updateRoom = "UPDATE room SET availability = 'Occupied' WHERE room_no = ?";
            PreparedStatement pst2 = c.connection.prepareStatement(updateRoom);
            pst2.setString(1, room);
            pst2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Patient Added Successfully!");
            clearForm();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage());
        }
    }

    private void clearForm() {
        comboID.setSelectedIndex(0);
        txtNumber.setText("");
        txtName.setText("");
        txtMobile.setText("");
        txtDeposit.setText("");
        male.setSelected(false);
        female.setSelected(false);
        comboRoom.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new NEW_PATIENT();
    }
}
