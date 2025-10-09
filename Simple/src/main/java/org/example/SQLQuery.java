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
        String query = "SELECT pc.pcid, pc.brand, pc.version, employee.name FROM pc INNER JOIN employee ON pc.userid = employee.userid WHERE pc.pcid LIKE '%"+pcId+"%' AND pc.brand LIKE '%"+brand+"%' AND pc.version LIKE '%"+version+"%' AND employee.name LIKE '%"+owner+"%'";
        StringBuilder result = new StringBuilder();
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
    public void addPC(String pcId, String brand, String version, String owner){
        String ownerQuery = "SELECT userid FROM employee WHERE name ='"+owner+"'";
        String resultID = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(ownerQuery);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int resid = rs.getInt("userid");
                resultID = Integer.toString(resid);
            }
            System.out.println("Connected to MariaDB successfully!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String query =  "INSERT INTO pc (brand, version, userid) VALUES ('"+ brand +"', '"+ version +"', '"+ resultID +"')";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to MariaDB successfully!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
