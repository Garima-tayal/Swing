import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class StudentSwingApp {

    private static final String url = "jdbc:mysql://localhost:3306/student_db";
    private static final String user = "root";
    private static final String pass = "garima";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            JFrame frame = new JFrame("Student Registration/Login");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);

            JButton regBtn = new JButton("Register");
            regBtn.setBounds(50, 30, 200, 30);
            frame.add(regBtn);

            JButton loginBtn = new JButton("Login");
            loginBtn.setBounds(50, 80, 200, 30);
            frame.add(loginBtn);

            frame.setVisible(true);

            regBtn.addActionListener(e -> showRegisterForm(con));

            loginBtn.addActionListener(e -> showLoginForm(con));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showRegisterForm(Connection con) {
        JFrame regFrame = new JFrame("Register");
        regFrame.setSize(300, 300);
        regFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setBounds(10, 20, 80, 25);
        regFrame.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 165, 25);
        regFrame.add(nameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 60, 80, 25);
        regFrame.add(passLabel);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 60, 165, 25);
        regFrame.add(passField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 100, 80, 25);
        regFrame.add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(100, 100, 165, 25);
        regFrame.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 140, 80, 25);
        regFrame.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(100, 140, 165, 25);
        regFrame.add(emailField);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(100, 180, 100, 30);
        regFrame.add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String password = new String(passField.getPassword());
                String phone = phoneField.getText();
                String email = emailField.getText();

                String sql = "INSERT INTO students (name, password, phone_number, email) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, password);
                ps.setString(3, phone);
                ps.setString(4, email);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(regFrame, "Registration Successful!");
                    regFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(regFrame, "Registration Failed.");
                }
                ps.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(regFrame, "Error: " + ex.getMessage());
            }
        });

        regFrame.setVisible(true);
    }

    static void showLoginForm(Connection con) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 220);
        loginFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setBounds(10, 20, 80, 25);
        loginFrame.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 165, 25);
        loginFrame.add(nameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 60, 80, 25);
        loginFrame.add(passLabel);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 60, 165, 25);
        loginFrame.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 100, 100, 30);
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String password = new String(passField.getPassword());

                String sql = "SELECT * FROM students WHERE name = ? AND password = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("phone_number");
                    String email = rs.getString("email");
                    JOptionPane.showMessageDialog(loginFrame,
                        "Login Successful!\nPhone: " + phone + "\nEmail: " + email);
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials.");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(loginFrame, "Error: " + ex.getMessage());
            }
        });

        loginFrame.setVisible(true);
    }
}
