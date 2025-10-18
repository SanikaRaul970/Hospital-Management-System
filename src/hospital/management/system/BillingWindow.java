package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class BillingWindow extends JFrame {

    JTable table;
    DefaultTableModel model;

    public BillingWindow() {
        setTitle("Billing Management");
        setSize(900, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Columns
        String[] columns = {"ID", "Name", "Room", "Deposit", "Additional Charges", "Total Amount"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make Deposit and Additional Charges editable
                return column == 3 || column == 4;
            }
        };

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchBillingData();

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();

        JButton updateBtn = new JButton("Update Billing");
        styleButton(updateBtn);
        updateBtn.addActionListener(e -> saveBillingChanges(false));

        JButton completeBtn = new JButton("Complete Billing / Free Room");
        styleButton(completeBtn);
        completeBtn.addActionListener(e -> saveBillingChanges(true));

        JButton backBtn = new JButton("Back to Reception");
        styleButton(backBtn);
        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        buttonPanel.add(updateBtn);
        buttonPanel.add(completeBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(42, 157, 143));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(25, 111, 101));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(42, 157, 143));
            }
        });
    }

    private void fetchBillingData() {
        try {
            conn c = new conn();
            String query = "SELECT ID, Name, Room, Deposit FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            model.setRowCount(0); // clear table

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Room");
                row[3] = rs.getBigDecimal("Deposit");
                row[4] = BigDecimal.ZERO; // Additional Charges editable in table
                row[5] = rs.getBigDecimal("Deposit"); // Total = Deposit + Additional Charges
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching billing data: " + e.getMessage());
        }
    }

    private void saveBillingChanges(boolean freeRoom) {
        try {
            conn c = new conn();

            for (int i = 0; i < table.getRowCount(); i++) {
                String id = table.getValueAt(i, 0).toString();
                BigDecimal deposit = new BigDecimal(table.getValueAt(i, 3).toString());
                BigDecimal additional = new BigDecimal(table.getValueAt(i, 4).toString());
                BigDecimal total = deposit.add(additional);
                table.setValueAt(total, i, 5); // update Total Amount column in table

                String query;
                if (freeRoom) {
                    // Set Room = NULL to free it
                    query = "UPDATE patient_info SET Deposit = ?, Room = NULL WHERE ID = ?";
                } else {
                    query = "UPDATE patient_info SET Deposit = ? WHERE ID = ?";
                }

                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setBigDecimal(1, deposit);
                pst.setString(2, id);
                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, freeRoom ? "Billing completed and room freed!" : "Billing updated successfully!");
            fetchBillingData();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating billing: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BillingWindow();
    }
}
