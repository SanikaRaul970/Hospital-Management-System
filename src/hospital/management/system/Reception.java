package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reception extends JFrame {

    Reception() {

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1455, 150);
        headerPanel.setBackground(new Color(89, 131, 146));
        add(headerPanel);

        JLabel title = new JLabel("Reception Dashboard - Hospital Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 40, 850, 50);
        headerPanel.add(title);

        // Doctor image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/dr.png"));
        Image img1 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel doctorImage = new JLabel(new ImageIcon(img1));
        doctorImage.setBounds(30, -10, 200, 200);
        headerPanel.add(doctorImage);

        // Ambulance image
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icon/amb.png"));
        Image img2 = i2.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JLabel ambulanceImage = new JLabel(new ImageIcon(img2));
        ambulanceImage.setBounds(1220, 25, 200, 100);
        headerPanel.add(ambulanceImage);



        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 150, 1455, 618);
        mainPanel.setBackground(new Color(163, 203, 204));
        add(mainPanel);

        JLabel welcome = new JLabel("Welcome to the Hospital Reception", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(new Color(34, 68, 79));
        welcome.setBounds(0, 40, 1455, 40);
        mainPanel.add(welcome);

        // ===== BUTTON GRID =====
        String[] buttonNames = {
                "Add Patient", "View Patients", "Update Patient Info",
                "Patient Info", "Department", "Book Appointment",
                "Check Room Availability", "View Doctors", "Discharge Summary",
                "Billing", "View All Employees", "Logout"
        };

        int buttonWidth = 250;
        int buttonHeight = 55;
        int hGap = 70; // horizontal gap
        int vGap = 30; // vertical gap
        int buttonsPerRow = 3;

        // Calculate total grid width and height
        int totalWidth = buttonsPerRow * buttonWidth + (buttonsPerRow - 1) * hGap;
        int totalHeight = ((int) Math.ceil(buttonNames.length / (double) buttonsPerRow)) * buttonHeight
                + (((int) Math.ceil(buttonNames.length / (double) buttonsPerRow)) - 1) * vGap;

        // Calculate starting point to center the grid
        int startX = (mainPanel.getWidth() - totalWidth) / 2;
        int startY = (mainPanel.getHeight() - totalHeight) / 2 + 40;

        int x = startX, y = startY;
        int count = 0;

        for (String name : buttonNames) {
            JButton btn = new JButton(name);
            btn.setBounds(x, y, buttonWidth, buttonHeight);
            btn.setBackground(new Color(42, 157, 143));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(25, 111, 101));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(42, 157, 143));
                }
            });

            btn.addActionListener(e -> {
                String btnText = btn.getText();

                switch (btnText) {
                    case "Add Patient":
                        new NEW_PATIENT();
                        setVisible(false);
                        break;

                    case "View Patients":
                    case "Patient Info":   // Both open the same patient view
                        new View_Patient();
                        setVisible(false);
                        break;

                    case "Update Patient Info":
                        new Update_Patient(); // You need to create this class
                        setVisible(false);
                        break;

                    case "Billing":
                        new BillingWindow();
                        setVisible(false);
                        break;

                    case "Check Room Availability":
                        new View_Room();
                        setVisible(false);
                        break;
                    case "Department":
                        new Department();
                        setVisible(false);
                        break;

                    case "Book Appointment":
                        new Book_Appointment();
                        setVisible(false);
                        break;

                    case "View Doctors":
                        new View_Doctor();
                        setVisible(false);
                        break;

                    case "Discharge Summary":
                        new Discharge_Summary();
                        setVisible(false);
                        break;

                    case "View All Employees":
                        new View_Employee();
                        setVisible(false);
                        break;


                    case "Logout":
                        dispose();
                        JOptionPane.showMessageDialog(null, "Logged out successfully!");
                        break;

                    // Add other buttons here as needed
                    default:
                        JOptionPane.showMessageDialog(null, btnText + " is not implemented yet.");
                }
            });


            mainPanel.add(btn);
            count++;
            x += buttonWidth + hGap;
            if (count % buttonsPerRow == 0) {
                x = startX;
                y += buttonHeight + vGap;
            }
        }

        // ===== FRAME SETTINGS =====
        setTitle("Hospital Management System - Reception Dashboard");
        setSize(1455, 768);
        setLayout(null);
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}
