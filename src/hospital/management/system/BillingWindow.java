package hospital.management.system;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

        // ===== FETCH DATA =====
        fetchBillingData();

        // ===== AUTO TOTAL CALCULATION =====
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // When Deposit or Additional Charges are changed, recalculate
                if (column == 3 || column == 4) {
                    recalculateRowTotal(row);
                }
            }
        });

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));

        JButton updateBtn = new JButton("Update Billing");
        styleButton(updateBtn);
        updateBtn.addActionListener(e -> saveBillingChanges(false));

        JButton completeBtn = new JButton("Complete Billing");
        styleButton(completeBtn);
        completeBtn.addActionListener(e -> completeSelectedBilling());

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

    // ===== BUTTON STYLE =====
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
            String query = "SELECT ID, Name, Room, Deposit, Additional_Charges, Total_Amount, Billing_Status FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("Name");
                row[2] = rs.getString("Room");
                row[3] = rs.getBigDecimal("Deposit");
                row[4] = rs.getBigDecimal("Additional_Charges");
                row[5] = rs.getBigDecimal("Total_Amount");
                row[6] = rs.getString("Billing_Status");
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching billing data: " + e.getMessage());
        }
    }

    // ===== AUTO RECALCULATE TOTAL =====
    private void recalculateRowTotal(int row) {
        try {
            if (row < 0 || row >= table.getRowCount()) return;

            BigDecimal deposit = safeBigDecimal(table.getValueAt(row, 3));
            BigDecimal additional = safeBigDecimal(table.getValueAt(row, 4));
            BigDecimal total = deposit.add(additional);

            table.setValueAt(total, row, 5);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid number entered. Please enter numeric values only.");
        }
    }

    private BigDecimal safeBigDecimal(Object value) {
        if (value == null || value.toString().trim().isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(value.toString());
    }

    // ===== SAVE BILLING CHANGES =====
    private void saveBillingChanges(boolean showMessageOnly) {
        try {
            conn c = new conn();

            for (int i = 0; i < table.getRowCount(); i++) {
                String id = table.getValueAt(i, 0).toString();
                BigDecimal deposit = safeBigDecimal(table.getValueAt(i, 3));
                BigDecimal additional = safeBigDecimal(table.getValueAt(i, 4));
                BigDecimal total = deposit.add(additional);
                table.setValueAt(total, i, 5);

                String query = "UPDATE patient_info SET Deposit = ?, Additional_Charges = ?, Total_Amount = ?, Billing_Status = ? WHERE ID = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setBigDecimal(1, deposit);
                pst.setBigDecimal(2, additional);
                pst.setBigDecimal(3, total);
                pst.setString(4, table.getValueAt(i, 6).toString());
                pst.setString(5, id);
                pst.executeUpdate();
            }

            if (!showMessageOnly)
                JOptionPane.showMessageDialog(null, "Billing updated successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating billing: " + e.getMessage());
        }
    }

    // ===== COMPLETE SELECTED BILLING =====
    private void completeSelectedBilling() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a patient to complete billing.");
            return;
        }

        saveBillingChanges(true);

        String id = table.getValueAt(selectedRow, 0).toString();

        try {
            conn c = new conn();
            String updateStatus = "UPDATE patient_info SET Billing_Status = 'Completed' WHERE ID = ?";
            PreparedStatement pst = c.connection.prepareStatement(updateStatus);
            pst.setString(1, id);
            pst.executeUpdate();

            table.setValueAt("Completed", selectedRow, 6);

            JOptionPane.showMessageDialog(null, "Billing completed for patient ID: " + id);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error completing billing: " + e.getMessage());
        }
    }

    // ===== FREE ROOM (HISTORY KEPT) =====
    private void freeSelectedRoom() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a patient to free their room.");
            return;
        }

        String id = table.getValueAt(selectedRow, 0).toString();
        String roomNumber = table.getValueAt(selectedRow, 2).toString();

        try {
            conn c = new conn();

            // Keep room number for history, just mark it available
            String query = "UPDATE room SET Availability = 'Available' WHERE Room_No = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, roomNumber);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Room " + roomNumber + " is now available.\nPatient history retained.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error freeing room: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BillingWindow();
    }
}
