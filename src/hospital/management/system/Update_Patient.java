package hospital.management.system;

import javax.swing.*;
import java.awt.*;

public class Update_Patient extends JFrame {

    public Update_Patient() {
        setTitle("Update Patient Information");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Update Patient Form Coming Soon", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBounds(50, 150, 500, 50);
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Update_Patient();
    }
}
