package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLQuery {
    String url = "jdbc:mariadb://localhost:3306/leltar";
    String user = "leltar";
    String password = "leltar";
    public String searchEmployee(String userId, String name, String jobTitle){
        String query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%' and userid LIKE '%"+userId+"%'";;
        StringBuilder result = new StringBuilder();
        /*if (name.isEmpty() && jobTitle.isEmpty() && userId.isEmpty()){
            query = "SELECT userid, name, job_title FROM employee";
        } else if (name.isEmpty() && !jobTitle.isEmpty()) {
            if (userId.isEmpty()){
                query = "SELECT userid, name, job_title FROM employee WHERE job_title LIKE '%"+jobTitle+"%'";
            } else {
                query = "SELECT userid, name, job_title FROM employee WHERE job_title LIKE '%"+jobTitle+"%' and userid LIKE '%"+userId+"%'";
            }
        } else if (!name.isEmpty() && jobTitle.isEmpty()) {
            if (userId.isEmpty()){
                query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%'";
            } else {
                query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and userid LIKE '%"+userId+"%'";
            }
        } else if (name.isEmpty() && jobTitle.isEmpty() && !userId.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE userid LIKE '%"+userId+"%'";
        } else if (!name.isEmpty() && !jobTitle.isEmpty() && userId.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%'";
        } else {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%' and userid LIKE '%"+userId+"%'";
        }*/
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
    public void addEmployee(String name, String jobTitle){
        String query =  "INSERT INTO employee (name, job_title) VALUES ('"+ name +"', '"+ jobTitle +"')";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to MariaDB successfully!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String searchPc(String pcId, String brand, String version, String owner){
        // Select pc.pcid, pc.brand, pc.version, employee.name from pc inner join employee on pc.userid = employee.userid;
        String query = "";
        StringBuilder result = new StringBuilder();
        /*if (pcId.isEmpty() && brand.isEmpty() && version.isEmpty() && owner.isEmpty()){
            query = "Select pc.pcid, pc.brand, pc.version, employee.name from pc inner join employee on pc.userid = employee.userid";
        } else if (name.isEmpty() && !jobTitle.isEmpty()) {
            if (userId.isEmpty()){
                query = "SELECT userid, name, job_title FROM employee WHERE job_title LIKE '%"+jobTitle+"%'";
            } else {
                query = "SELECT userid, name, job_title FROM employee WHERE job_title LIKE '%"+jobTitle+"%' and userid LIKE '%"+userId+"%'";
            }
        } else if (!name.isEmpty() && jobTitle.isEmpty()) {
            if (userId.isEmpty()){
                query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%'";
            } else {
                query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and userid LIKE '%"+userId+"%'";
            }
        } else if (name.isEmpty() && jobTitle.isEmpty() && !userId.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE userid LIKE '%"+userId+"%'";
        } else if (!name.isEmpty() && !jobTitle.isEmpty() && userId.isEmpty()) {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%'";
        } else {
            query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%"+name+"%' and job_title LIKE '%"+jobTitle+"%' and userid LIKE '%"+userId+"%'";
        }*/
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to MariaDB successfully!\n");

            while (rs.next()) {
                int resid = rs.getInt("pcid");
                String resbrand = rs.getString("brand");
                String resjobTitle = rs.getString("version");
                String resname = rs.getString("name");
                result.append(String.format("%d;%s;%s;%s\n", resid, resbrand, resjobTitle, resname));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
