package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SQLQuery {
    String url = "jdbc:mariadb://localhost:3306/leltar";
    String user = "leltar";
    String password = "leltar";

    public ObservableList<Employee> searchEmployee(String userId, String name, String jobTitle) {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String query = "SELECT userid, name, job_title FROM employee WHERE name LIKE '%" + name + "%' and job_title LIKE '%" + jobTitle + "%' and userid LIKE '%" + userId + "%'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("userid"),
                        rs.getString("name"),
                        rs.getString("job_title")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addEmployee(String name, String jobTitle) {
        String query = "INSERT INTO employee (name, job_title) VALUES ('" + name + "', '" + jobTitle + "')";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PC> searchPc(String pcId, String brand, String version, String owner) {
        ObservableList<PC> list = FXCollections.observableArrayList();
        String query = "SELECT pc.pcid, pc.brand, pc.version, COALESCE(employee.name, '') AS name FROM pc LEFT JOIN employee ON pc.userid = employee.userid WHERE pc.pcid LIKE '%" + pcId + "%' AND pc.brand LIKE '%" + brand + "%' AND pc.version LIKE '%" + version + "%' AND COALESCE(employee.name, '') LIKE '%" + owner + "%'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new PC(
                        rs.getInt("pcid"),
                        rs.getString("brand"),
                        rs.getString("version"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addPC(String brand, String version, String owner) {
        Integer userId = null;
        if (owner != null && !owner.trim().isEmpty()) {
            String ownerQuery = "SELECT userid FROM employee WHERE name = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(ownerQuery)) {

                stmt.setString(1, owner);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    userId = rs.getInt("userid");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String insertQuery = "INSERT INTO pc (brand, version, userid) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, brand);
            stmt.setString(2, version);

            if (userId != null) {
                stmt.setInt(3, userId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addPeripheral(String brand, String version, String pcid) {
            String query = "INSERT INTO peripheral (brand, version, pcid) VALUES ('" + brand + "', '" + version + "', '" + pcid + "')";
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public int getEmployeeCount() {
        int count = 0;
        String query = "SELECT COUNT(userid) FROM employee;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public int getPCCount() {
        int count = 0;
        String query = "SELECT COUNT(pcid) FROM pc;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public int getPeripheralCount() {
        int count = 0;
        String query = "SELECT COUNT(peripheralid) FROM peripheral;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public String exportCSV() {
        StringBuilder result = new StringBuilder();
        String query = "";
        query = "SELECT userid, name, job_title FROM employee";
        result.append("userid;name;job_title\n");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int resid = rs.getInt("userid");
                String resname = rs.getString("name");
                String resjobTitle = rs.getString("job_title");
                result.append(String.format("%d;%s;%s\n", resid, resname, resjobTitle));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        query = "SELECT pcid, brand, version, userid FROM pc;";
        result.append("\n\npcid;brand;version;userid\n");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int resid = rs.getInt("pcid");
                String resbrand = rs.getString("brand");
                String resversion = rs.getString("version");
                String resuserid = rs.getString("userid");
                result.append(String.format("%d;%s;%s;%s\n", resid, resbrand, resversion, resuserid));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        query = "SELECT peripheralid, brand, version, pcid FROM peripheral;";
        result.append("\n\nperipheralid;brand;version;pcid\n");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int resid = rs.getInt("peripheralid");
                String resbrand = rs.getString("brand");
                String resversion = rs.getString("version");
                String respcid = rs.getString("pcid");
                result.append(String.format("%d;%s;%s;%s\n", resid, resbrand, resversion, respcid));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public void deleteEmployee(int id) {
        String nullifyPC = "UPDATE pc SET userid=NULL WHERE userid=?";
        String deleteEmployee = "DELETE FROM employee WHERE userid=?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement stmt = conn.prepareStatement(nullifyPC)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(deleteEmployee)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int id, String name, String jobTitle) {
        String query = "UPDATE employee SET name=?, job_title=? WHERE userid=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, jobTitle);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePC(int id) {
        String nullifyPeripheral = "UPDATE peripheral SET pcid=NULL WHERE pcid=?";
        String deletePC = "DELETE FROM pc WHERE pcid=?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            try (PreparedStatement stmt = conn.prepareStatement(nullifyPeripheral)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(deletePC)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updatePC(int id, String brand, String version, String ownerName) {
        Integer ownerId = null;

        if (ownerName != null && !ownerName.trim().isEmpty()) {
            String ownerQuery = "SELECT userid FROM employee WHERE name=?";
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(ownerQuery)) {

                stmt.setString(1, ownerName.trim());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    ownerId = rs.getInt("userid");
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        String query = "UPDATE pc SET brand=?, version=?, userid=? WHERE pcid=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, brand);
            stmt.setString(2, version);

            if (ownerId != null) {
                stmt.setInt(3, ownerId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, id);
            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}