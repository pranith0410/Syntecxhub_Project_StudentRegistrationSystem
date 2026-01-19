import java.sql.*;
import java.util.Scanner;

public class StudentRegistrationSystem {

    static final String URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "studentuser";
    static final String PASSWORD = "student123";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Student Registration System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student Course");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ADD STUDENT
    static void addStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            String query = "INSERT INTO students VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, course);

            ps.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW STUDENTS
    static void viewStudents() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\nID\tName\tEmail\t\tCourse");
            System.out.println("--------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("email") + "\t" +
                        rs.getString("course")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // UPDATE STUDENT
    static void updateStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Student ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new Course: ");
            String course = sc.nextLine();

            String query = "UPDATE students SET course=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Student updated successfully!");
            else
                System.out.println("Student not found!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DELETE STUDENT
    static void deleteStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Student ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Student deleted successfully!");
            else
                System.out.println("Student not found!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
