package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLTest {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/leltar";
        String user = "leltar";
        String password = "leltar";

        String query = "SELECT userid, name, job_title FROM employee";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to MariaDB successfully!\n");

            while (rs.next()) {
                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String jobTitle = rs.getString("job_title");
                System.out.printf("ID: %d | Name: %s | Job: %s%n", id, name, jobTitle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
