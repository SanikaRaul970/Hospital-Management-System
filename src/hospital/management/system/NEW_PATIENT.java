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

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1455, 150);
        headerPanel.setBackground(new Color(89, 131, 146));
        add(headerPanel);

        JLabel title = new JLabel("Add New Patient - Hospital Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 40, 850, 50);
        headerPanel.add(title);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 150, 1455, 618);
        mainPanel.setBackground(new Color(163, 203, 204));
        add(mainPanel);

        JLabel heading = new JLabel("Patient Registration Form", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(34, 68, 79));
        heading.setBounds(0, 30, 1455, 40);
        mainPanel.add(heading);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBounds(420, 100, 600, 420);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);

        int labelX = 50, fieldX = 220, y = 40, gap = 45;

        // ID
        JLabel lblID = new JLabel("ID Type");
        lblID.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblID.setBounds(labelX, y, 150, 25);
        formPanel.add(lblID);

        comboID = new JComboBox<>(new String[]{"Aadhar Card", "Voter ID", "Passport", "Driving License"});
        comboID.setBounds(fieldX, y, 300, 25);
        comboID.setBackground(new Color(236, 253, 245));
        formPanel.add(comboID);
        y += gap;

        // ID Number
        JLabel lblNumber = new JLabel("ID Number");
        lblNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNumber.setBounds(labelX, y, 150, 25);
        formPanel.add(lblNumber);

        txtNumber = new JTextField();
        txtNumber.setBounds(fieldX, y, 300, 25);
        txtNumber.setBackground(new Color(236, 253, 245));
        formPanel.add(txtNumber);
        y += gap;

        // Name
        JLabel lblName = new JLabel("Full Name");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblName.setBounds(labelX, y, 150, 25);
        formPanel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(fieldX, y, 300, 25);
        txtName.setBackground(new Color(236, 253, 245));
        formPanel.add(txtName);
        y += gap;

        // Gender
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblGender.setBounds(labelX, y, 150, 25);
        formPanel.add(lblGender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBackground(Color.WHITE);
        female.setBackground(Color.WHITE);
        male.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        female.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        male.setBounds(fieldX, y, 100, 25);
        female.setBounds(fieldX + 120, y, 100, 25);
        formPanel.add(male);
        formPanel.add(female);
        y += gap;

        // Room
        JLabel lblRoom = new JLabel("Room Number");
        lblRoom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRoom.setBounds(labelX, y, 150, 25);
        formPanel.add(lblRoom);

        comboRoom = new JComboBox<>();
        comboRoom.setBounds(fieldX, y, 300, 25);
        comboRoom.setBackground(new Color(236, 253, 245));
        formPanel.add(comboRoom);
        y += gap;

        // Load available rooms from DB
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT room_no FROM Room WHERE availability='Available'");
            while (rs.next()) {
                comboRoom.addItem(rs.getString("room_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Time
        JLabel lblTimeText = new JLabel("Time");
        lblTimeText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTimeText.setBounds(labelX, y, 150, 25);
        formPanel.add(lblTimeText);

        lblTime = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTime.setBounds(fieldX, y, 300, 25);
        formPanel.add(lblTime);
        y += gap;

        // Mobile Number
        JLabel lblMobile = new JLabel("Mobile Number");
        lblMobile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMobile.setBounds(labelX, y, 150, 25);
        formPanel.add(lblMobile);

        txtMobile = new JTextField();
        txtMobile.setBounds(fieldX, y, 300, 25);
        txtMobile.setBackground(new Color(236, 253, 245));
        formPanel.add(txtMobile);
        y += gap;

        // Deposit
        JLabel lblDeposit = new JLabel("Deposit Amount");
        lblDeposit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDeposit.setBounds(labelX, y, 150, 25);
        formPanel.add(lblDeposit);

        txtDeposit = new JTextField();
        txtDeposit.setBounds(fieldX, y, 300, 25);
        txtDeposit.setBackground(new Color(236, 253, 245));
        formPanel.add(txtDeposit);
        y += gap + 10;

        // Buttons
        btnAdd = new JButton("Add Patient");
        btnAdd.setBounds(150, y, 130, 35);
        styleButton(btnAdd);
        formPanel.add(btnAdd);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(310, y, 130, 35);
        styleButton(btnCancel);
        formPanel.add(btnCancel);

        btnBack = new JButton("Back to Reception");
        btnBack.setBounds(220, y + 60, 200, 35);
        styleButton(btnBack);
        formPanel.add(btnBack);

        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);
        btnBack.addActionListener(this);

        // ✅ Important for scroll to work
        formPanel.setPreferredSize(new Dimension(580, y + 180));

        // ===== FRAME SETTINGS =====
        setTitle("Hospital Management System - New Patient");
        setSize(1455, 768);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
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
            String idType = (String) comboID.getSelectedItem();
            String number = txtNumber.getText();
            String name = txtName.getText();
            String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "");
            String room = (String) comboRoom.getSelectedItem();
            String mobile = txtMobile.getText();
            String deposit = txtDeposit.getText();

            if (name.isEmpty() || number.isEmpty() || gender.isEmpty() || mobile.isEmpty() || deposit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            try {
                conn c = new conn();
                String query = "INSERT INTO patient_info (ID, Number, Name, Gender, Room, Time, Mobile_Number, Deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, idType);
                pst.setString(2, number);
                pst.setString(3, name);
                pst.setString(4, gender);
                pst.setString(5, room);
                pst.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                pst.setString(7, mobile);
                pst.setBigDecimal(8, new java.math.BigDecimal(deposit));
                pst.executeUpdate();

                // Update room availability
                String updateRoom = "UPDATE Room SET availability='Occupied' WHERE room_no=?";
                PreparedStatement pst2 = c.connection.prepareStatement(updateRoom);
                pst2.setString(1, room);
                pst2.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Patient added successfully!");
                setVisible(false);
                new Reception();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }

        } else if (e.getSource() == btnCancel) {
            // ✅ Clear form only
            clearForm();

        } else if (e.getSource() == btnBack) {
            // ✅ Go back to Reception
            setVisible(false);
            new Reception();
        }
    }

    // ✅ Clear all input fields
    private void clearForm() {
        comboID.setSelectedIndex(0);
        txtNumber.setText("");
        txtName.setText("");
        male.setSelected(false);
        female.setSelected(false);
        comboRoom.setSelectedIndex(0);
        lblTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        txtMobile.setText("");
        txtDeposit.setText("");
    }

    public static void main(String[] args) {
        new NEW_PATIENT();
    }
}
