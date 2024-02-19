import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registration extends JFrame implements ActionListener {

    private JLabel titleLabel ;
    private JLabel usernameLabel,emailLabel,firstnameLabel,lastnameLabel,passwordLabel,phoneLAbel,addressLAbel;
    private JTextField usernameField,emailField,firstnameField,lastnameField,passwordField,phoneField,addressField;



    private JButton registerButton,loginButton;


    public registration() {
        setLayout(null);

        
        setTitle("Registration Form");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        titleLabel = new JLabel("register to Timber and wood supply  system");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setBounds(40, 20, 320, 30);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 70, 80, 25);
        add(usernameLabel);

        emailLabel = new JLabel("email:");
        emailLabel.setBounds(40, 110, 80, 25);
        add(emailLabel);

        firstnameLabel = new JLabel("firstname:");
        firstnameLabel.setBounds(40, 150, 80, 25);
        add(firstnameLabel);

        lastnameLabel = new JLabel("lastname:");
        lastnameLabel.setBounds(40, 190, 80, 25);
        add(lastnameLabel);

        passwordLabel = new JLabel("password:");
        passwordLabel.setBounds(40, 230, 80, 25);
        add(passwordLabel);

        phoneLAbel = new JLabel("number:");
        phoneLAbel.setBounds(40, 270, 80, 25);
        add(phoneLAbel);

        addressLAbel = new JLabel("address:");
        addressLAbel.setBounds(40, 310, 80, 25);
        add(addressLAbel);


        usernameField = new JTextField();
        usernameField.setBounds(130, 70, 200, 25);
        add(usernameField);

        emailField = new JTextField();
        emailField.setBounds(130, 110, 200, 25);
        add(emailField);

        firstnameField = new JTextField();
        firstnameField.setBounds(130, 150, 200, 25);
        add(firstnameField);

       lastnameField = new JTextField();
        lastnameField.setBounds(130, 190, 200, 25);
        add(lastnameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 230, 200, 25);
        add(passwordField);

        phoneField = new JTextField();
        phoneField.setBounds(130, 270, 200, 25);
        add(phoneField);

        addressField = new JTextField();
        addressField.setBounds(130, 310, 200, 25);
        add(addressField);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 360, 100, 30);
        registerButton.addActionListener(this);
        add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 400, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);
        
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();

        } else if (e.getSource() == loginButton) {
            new login().setVisible(true);

        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String first_name = firstnameField.getText();
        String last_name = lastnameField.getText();
        String password = passwordField.getText();
        String phone_number = phoneField.getText();
        String address = addressField.getText();


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tsupplier", "222014897", "222014897");


            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username,email, password,first_name,last_name,phone_number,address) VALUES (?, ?,?,?,?,?,?)");
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, first_name);
            stmt.setString(5, last_name);
            stmt.setString(6, phone_number);
            stmt.setString(7, address);


            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new login().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }


            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

   

    public static void main(String[] args) {
        registration form = new registration();
        form.setVisible(true);
    }
}

