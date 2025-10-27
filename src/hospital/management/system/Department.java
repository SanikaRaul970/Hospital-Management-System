package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Department extends JFrame {

    Department() {
        setTitle("Department Details");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(89, 131, 146));
        headerPanel.setPreferredSize(new Dimension(900, 70));
        headerPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Department Details", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.CENTER);

        // ===== TABLE =====
        JTable table = new JTable();
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM department");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);

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

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Department();
    }
}
