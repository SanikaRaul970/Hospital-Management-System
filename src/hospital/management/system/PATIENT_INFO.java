package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PATIENT_INFO extends JFrame implements ActionListener {

    JTable table;
    JButton btnBack, btnRefresh;

    PATIENT_INFO() {

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1455, 150);
        headerPanel.setBackground(new Color(89, 131, 146));
        add(headerPanel);

        JLabel title = new JLabel("Patient Information - Hospital Management System", SwingConstants.CENTER);
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

        // ===== TABLE =====
        String[] columnNames = {
                "Patient ID",
                "ID Type",
                "ID Number",
                "Full Name",
                "Gender",
                "Room",
                "Time",
                "Mobile Number",
                "Deposit"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(89, 131, 146));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 50, 1250, 400);
        mainPanel.add(scrollPane);

        // Load patient data
        loadPatientData(model);

        // ===== BUTTONS =====
        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(500, 480, 150, 35);
        styleButton(btnRefresh);
        mainPanel.add(btnRefresh);

        btnBack = new JButton("Back to Reception");
        btnBack.setBounds(700, 480, 200, 35);
        styleButton(btnBack);
        mainPanel.add(btnBack);

        btnBack.addActionListener(this);
        btnRefresh.addActionListener(this);

        // ===== FRAME SETTINGS =====
        setTitle("Hospital Management System - Patient Info");
        setSize(1455, 768);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // ✅ Load data from database
    private void loadPatientData(DefaultTableModel model) {
        model.setRowCount(0); // clear table before reloading
        try {
            conn c = new conn();
            String query = "SELECT * FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            while (rs.next()) {
                String patientID = rs.getString("Patient_ID");
                String idType = rs.getString("ID_Type");
                String idNumber = rs.getString("ID");
                String name = rs.getString("Name");
                String gender = rs.getString("Gender");
                String room = rs.getString("Room");
                String time = rs.getString("Time");
                String mobile = rs.getString("Mobile_Number");
                String deposit = rs.getString("Deposit");

                model.addRow(new Object[]{
                        patientID, idType, idNumber, name, gender, room, time, mobile, deposit
                });
            }

            rs.close();
            c.statement.close();
            c.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
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
        if (e.getSource() == btnBack) {
            setVisible(false);
            new Reception();
        } else if (e.getSource() == btnRefresh) {
            loadPatientData((DefaultTableModel) table.getModel());
        }
    }

    public static void main(String[] args) {
        new PATIENT_INFO();
    }
}
