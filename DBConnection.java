package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/leave_management";
            String user = "root";
            String password = "root123";

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected Successfully!");
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}