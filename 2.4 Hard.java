import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/EXP_7";
    private static final String USER = "root";
    private static final String PASSWORD = "rahul"; // Replace with your MySQL password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1 -> addStudent(scanner);
                    case 2 -> viewStudents();
                    case 3 -> updateStudent(scanner);
                    case 4 -> deleteStudent(scanner);
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Establish Database Connection
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add a new student
    private static void addStudent(Scanner scanner) throws SQLException {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();

        String query = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, dept);
            pstmt.setDouble(3, marks);
            pstmt.executeUpdate();
            System.out.println(" Student added successfully.");
        }
    }

    // View all students
    private static void viewStudents() throws SQLException {
        String query = "SELECT * FROM Student";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n--- Student List ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("ID: " + rs.getInt("StudentID") + ", Name: " + rs.getString("Name") +
                        ", Department: " + rs.getString("Department") + ", Marks: " + rs.getDouble("Marks"));
            }
            if (!hasData) System.out.println("No students found.");
        }
    }

    // Update an existing student
    private static void updateStudent(Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter New Marks: ");
        double marks = scanner.nextDouble();

        String query = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, dept);
            pstmt.setDouble(3, marks);
            pstmt.setInt(4, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" Student updated successfully.");
            } else {
                System.out.println(" Student ID not found.");
            }
        }
    }

    // Delete a student
    private static void deleteStudent(Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM Student WHERE StudentID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Student deleted successfully.");
            } else {
                System.out.println(" Student ID not found.");
            }
        }
    }
}
