
import java.sql.*;

public class EmployeeFetcher {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/EXP_7"; 
        String user = "root"; 
        String password = "rahul"; 

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Employee Records:");
            System.out.println("----------------------------");
            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") + 
                                   ", Name: " + rs.getString("Name") + 
                                   ", Salary: " + rs.getDouble("Salary"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
