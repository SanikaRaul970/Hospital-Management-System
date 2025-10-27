package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class View_Employee extends JFrame {
    View_Employee() {
        setTitle("All Hospital Employees");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(89, 131, 146));
        JLabel title = new JLabel("Hospital Employees & Doctors", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // ===== MAIN PANEL (Split view for doctors + employees) =====
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // ---- Doctors Section ----
        JPanel doctorPanel = new JPanel(new BorderLayout());
        JLabel doctorLabel = new JLabel("Doctors", JLabel.CENTER);
        doctorLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        doctorLabel.setForeground(new Color(34, 68, 79));
        doctorPanel.add(doctorLabel, BorderLayout.NORTH);

        JTable doctorTable = new JTable();
        try {
            conn c = new conn();
            String query = "SELECT d.Doctor_ID, d.Name AS Doctor_Name, dep.Department_Name, d.Qualification " +
                    "FROM doctor d JOIN department dep ON d.Department_ID = dep.Department_ID";
            ResultSet rs = c.statement.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            String[] columnNames = new String[columns];
            for (int i = 1; i <= columns; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }

            java.util.List<String[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            String[][] tableData = data.toArray(new String[0][]);
            doctorTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));

        } catch (Exception e) {
            e.printStackTrace();
        }

        doctorPanel.add(new JScrollPane(doctorTable), BorderLayout.CENTER);

        // ---- Other Employees Section ----
        JPanel empPanel = new JPanel(new BorderLayout());
        JLabel empLabel = new JLabel("Other Employees", JLabel.CENTER);
        empLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        empLabel.setForeground(new Color(34, 68, 79));
        empPanel.add(empLabel, BorderLayout.NORTH);

        JTable empTable = new JTable();
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM employee");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            String[] columnNames = new String[columns];
            for (int i = 1; i <= columns; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }

            java.util.List<String[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            String[][] tableData = data.toArray(new String[0][]);
            empTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));

        } catch (Exception e) {
            e.printStackTrace();
        }

        empPanel.add(new JScrollPane(empTable), BorderLayout.CENTER);

        // Add both sections to main panel
        mainPanel.add(doctorPanel);
        mainPanel.add(empPanel);
        add(mainPanel, BorderLayout.CENTER);

        // ===== FOOTER (Back Button) =====
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JButton backBtn = new JButton("Back to Reception");
        backBtn.setBackground(new Color(89, 131, 146));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backBtn.addActionListener(e -> {
            setVisible(false);
            new Reception();
        });
        footerPanel.add(backBtn);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
