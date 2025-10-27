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
        setSize(1000, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ===== TABLE MODEL =====
        String[] columns = {
                "ID", "Name", "Room", "Deposit",
                "Additional Charges", "Total Amount", "Billing Status"
        };

        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow Deposit and Additional Charges to be edited
                return column == 3 || column == 4;
            }
        };

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchBillingData();

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));

        JButton updateBtn = new JButton("Update Billing");
        styleButton(updateBtn);
        updateBtn.addActionListener(e -> saveBillingChanges(false));

        JButton completeBtn = new JButton("Complete Billing");
        styleButton(completeBtn);
        completeBtn.addActionListener(e -> completeBilling());

        JButton freeRoomBtn = new JButton("Free Room");
        styleButton(freeRoomBtn);
        freeRoomBtn.addActionListener(e -> freeSelectedRoom());

        JButton backBtn = new JButton("Back to Reception");
        styleButton(backBtn);
        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        buttonPanel.add(updateBtn);
        buttonPanel.add(completeBtn);
        buttonPanel.add(freeRoomBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ===== BUTTON STYLE METHOD =====
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

    // ===== FETCH BILLING DATA =====
    private void fetchBillingData() {
        try {
            conn c = new conn();
            String query = "SELECT ID, Name, Room, Deposit FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            model.setRowCount(0); // clear table before loading

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Room");
                row[3] = rs.getBigDecimal("Deposit");
                row[4] = BigDecimal.ZERO; // additional charges
                row[5] = rs.getBigDecimal("Deposit"); // total = deposit + additional
                row[6] = "Pending"; // billing status
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching billing data: " + e.getMessage());
        }
    }

    // ===== UPDATE BILLING VALUES =====
    private void saveBillingChanges(boolean showMessageOnly) {
        try {
            conn c = new conn();

            for (int i = 0; i < table.getRowCount(); i++) {
                String id = table.getValueAt(i, 0).toString();
                BigDecimal deposit = new BigDecimal(table.getValueAt(i, 3).toString());
                BigDecimal additional = new BigDecimal(table.getValueAt(i, 4).toString());
                BigDecimal total = deposit.add(additional);
                table.setValueAt(total, i, 5);

                String query = "UPDATE patient_info SET Deposit = ? WHERE ID = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setBigDecimal(1, deposit);
                pst.setString(2, id);
                pst.executeUpdate();
            }

            if (!showMessageOnly) {
                JOptionPane.showMessageDialog(null, "Billing updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating billing: " + e.getMessage());
        }
    }

    // ===== COMPLETE BILLING =====
    private void completeBilling() {
        saveBillingChanges(true); // Save any recent edits first

        try {
            conn c = new conn();

            for (int i = 0; i < table.getRowCount(); i++) {
                String id = table.getValueAt(i, 0).toString();

                // Mark billing status as completed
                table.setValueAt("Completed", i, 6);
            }

            JOptionPane.showMessageDialog(null, "Billing completed! You can now free the room if required.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error completing billing: " + e.getMessage());
        }
    }

    // ===== FREE SELECTED ROOM =====
    private void freeSelectedRoom() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a patient to free their room.");
            return;
        }

        String id = table.getValueAt(selectedRow, 0).toString();

        try {
            conn c = new conn();
            String query = "UPDATE patient_info SET Room = NULL WHERE ID = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, id);
            pst.executeUpdate();

            table.setValueAt(null, selectedRow, 2); // Clear room in table
            table.setValueAt("Completed", selectedRow, 6); // Ensure status shows completed

            JOptionPane.showMessageDialog(null, "Room freed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error freeing room: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BillingWindow();
    }
}
