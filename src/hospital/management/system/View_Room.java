package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class View_Room extends JFrame implements ActionListener {

    JTable table;
    JButton backButton, refreshButton, freeAllButton;
    String[] columnNames = {"Room Number", "Availability", "Status", "Price"};

    View_Room() {

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1455, 150);
        headerPanel.setBackground(new Color(89, 131, 146));
        add(headerPanel);

        JLabel title = new JLabel("Room Availability - Hospital Management System", SwingConstants.CENTER);
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

        JLabel heading = new JLabel("Current Room Status", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(34, 68, 79));
        heading.setBounds(0, 30, 1455, 40);
        mainPanel.add(heading);

        // ===== TABLE =====
        Object[][] data = fetchRoomData();
        table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);

        // Color-coded rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String avail = (String) table.getValueAt(row, 1);
                if ("Available".equalsIgnoreCase(avail)) {
                    c.setBackground(new Color(198, 239, 206));
                } else {
                    c.setBackground(new Color(255, 199, 206));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 120, 950, 350);
        mainPanel.add(scrollPane);

        // ===== BACK BUTTON =====
        backButton = new JButton("Back to Reception");
        backButton.setBounds(580, 500, 180, 40);
        styleButton(backButton);
        mainPanel.add(backButton);
        backButton.addActionListener(this);

        // ===== REFRESH BUTTON =====
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(780, 500, 120, 40);
        styleButton(refreshButton);
        mainPanel.add(refreshButton);
        refreshButton.addActionListener(e -> refreshTable());

        // ===== FREE ALL ROOMS BUTTON =====
        freeAllButton = new JButton("Free All Rooms");
        freeAllButton.setBounds(920, 500, 160, 40);
        styleButton(freeAllButton);
        mainPanel.add(freeAllButton);
        freeAllButton.addActionListener(e -> freeAllRooms());

        // ===== FRAME SETTINGS =====
        setTitle("Hospital Management System - Room Availability");
        setSize(1455, 768);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // ===== FETCH ROOM DATA =====
    private Object[][] fetchRoomData() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM Room");

            List<Object[]> list = new ArrayList<>();

            while (rs.next()) {
                String roomNo = rs.getString("room_no");
                String avail = rs.getString("availability");
                String status = avail.equalsIgnoreCase("Available") ? "✅ Free" : "❌ Occupied";
                String price = rs.getString("price");
                list.add(new Object[]{roomNo, avail, status, price});
            }

            Object[][] data = new Object[list.size()][4];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i);
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading room data: " + e.getMessage());
            return new Object[0][0];
        }
    }

    // ===== REFRESH TABLE =====
    private void refreshTable() {
        Object[][] newData = fetchRoomData();
        table.setModel(new DefaultTableModel(newData, columnNames));
    }

    // ===== FREE ALL ROOMS =====
    private void freeAllRooms() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to free all rooms?",
                "Confirm Action", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                conn c = new conn();
                String query = "UPDATE room SET availability = 'Available'";
                c.statement.executeUpdate(query);

                JOptionPane.showMessageDialog(this, "✅ All rooms have been freed successfully!");
                refreshTable();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error freeing rooms: " + e.getMessage());
            }
        }
    }

    // ===== STYLE BUTTON =====
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
        if (e.getSource() == backButton) {
            setVisible(false);
            new Reception();
        }
    }

    public static void main(String[] args) {
        new View_Room();
    }
}
