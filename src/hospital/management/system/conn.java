package hospital.management.system;

import java.sql.*;

public class conn {
    Connection connection;
    Statement statement;

    public conn() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital_management_system",
                    "root",
                    "Sani@108"
            );
            statement = connection.createStatement();
            connection.setAutoCommit(true); // ✅ Important
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
