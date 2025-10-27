package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class View_Doctor extends JFrame {
    View_Doctor() {
        setTitle("Doctors Information");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTable table = new JTable();

        try {
            conn c = new conn();

            // Join doctor + department tables
            String query = "SELECT d.Doctor_ID, d.Name AS Doctor_Name, dep.Department_Name, d.Qualification " +
                    "FROM doctor d " +
                    "JOIN department dep ON d.Department_ID = dep.Department_ID";
            ResultSet rs = c.statement.executeQuery(query);

            // Build table model manually
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
            table.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Back button
        JButton backBtn = new JButton("Back to Reception");
        backBtn.setBackground(new Color(89, 131, 146));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.addActionListener(e -> {
            setVisible(false);
            new Reception();
        });
        add(backBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new View_Doctor();
    }
}
