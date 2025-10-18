package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class View_Patient extends JFrame {

    JTable table;
    DefaultTableModel model;

    public View_Patient() {
        setTitle("Patient Information");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Table columns
        String[] columns = {"ID", "Number", "Name", "Gender", "Room", "Time", "Mobile Number", "Deposit"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchPatientData(); // load data from DB

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back to Reception");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(42, 157, 143));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(25, 111, 101));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(42, 157, 143));
            }
        });

        backBtn.addActionListener(e -> {
            new Reception(); // go back to Reception dashboard
            dispose();        // close current window
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchPatientData() {
        try {
            conn c = new conn(); // your DB connection class
            String query = "SELECT * FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("Number");
                row[2] = rs.getString("Name");
                row[3] = rs.getString("Gender");
                row[4] = rs.getString("Room");
                row[5] = rs.getString("Time");
                row[6] = rs.getString("Mobile_NUmber");
                row[7] = rs.getString("Deposit");

                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching patient data");
        }
    }

    public static void main(String[] args) {
        new View_Patient();
    }
}
