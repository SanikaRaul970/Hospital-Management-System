package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Discharge_Summary extends JFrame {
    Discharge_Summary() {
        setTitle("Discharge Summary");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable();
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM discharge_summary");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();

            // Column names
            String[] columnNames = new String[columns];
            for (int i = 1; i <= columns; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }

            // Row data
            java.util.List<String[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            // Convert to 2D array
            String[][] tableData = data.toArray(new String[0][]);
            table.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));
        setVisible(true);
    }

    public static void main(String[] args) {
        new Discharge_Summary();
    }
}
