package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLTest {
    String url = "jdbc:mariadb://localhost:3306/leltar";
    String user = "leltar";
    String password = "leltar";
    public String search(String name, String jobTitle){
        String query;
        StringBuilder result = new StringBuilder();
        if (name.isEmpty() && jobTitle.isEmpty()){
            query = "SELECT userid, name, job_title FROM employee";
        } else if (name.isEmpty() && !jobTitle.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE job_title LIKE '%"+jobTitle+"%'";
        } else if (!name.isEmpty() && jobTitle.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%'";
        } else {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%'";
        }
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to MariaDB successfully!\n");

            while (rs.next()) {
                int resid = rs.getInt("userid");
                String resname = rs.getString("name");
                String resjobTitle = rs.getString("job_title");
                result.append(String.format("%d;%s;%s\n", resid, resname, resjobTitle));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
