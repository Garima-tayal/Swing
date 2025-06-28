import java.sql.*;
import java.util.Scanner;

public class StudentApp {

    private static final String url = "jdbc:mysql://localhost:3306/student_db";
    private static final String user = "root";
    private static final String pass = "garima";  

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- STUDENT SYSTEM ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        register(con, sc);
                        break;
                    case 2:
                        login(con, sc);
                        break;
                    case 3:
                        System.out.println("Exiting...Thank You!");
                        con.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void register(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Username: ");
            String name = sc.next();
            System.out.print("Enter Password: ");
            String password = sc.next();
            System.out.print("Enter  phone_number: "); 
            String phone_number = sc.next();
            System.out.print("Enter Email: ");
            String email = sc.next();

            String sql = "INSERT INTO students (name, password, phone_number, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, phone_number);
            ps.setString(4, email);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Registration Successful!");
            } else {
                System.out.println("Registration Failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void login(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Username: ");
            String name = sc.next();
            System.out.print("Enter Password: ");
            String password = sc.next();

            String sql = "SELECT * FROM students WHERE name = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Profile Information ---");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("phone_number: " + rs.getInt("phone_number"));
                System.out.println("Email: " + rs.getString("email"));
            } else {
                System.out.println("Invalid name or Password.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

