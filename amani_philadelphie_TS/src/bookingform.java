import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class bookingform extends JFrame implements ActionListener {
    JLabel titleLabel,useridLabel,bookingdateLabel, detailsLabel,quantityLabel;
    JTextField useridTextField, bookingdateTextField,detailsTextField,quantityTextField;
    JButton submitButton, viewusersButton, contactusButton,viewstockButton;
    JTextArea stockTextArea,usersTextArea;
    private JTable dataTable;

    bookingform() {
    	
    	String[] columnNames = {"ID", "Name", "Description", "Price", "Quantity"};
        displayitems(columnNames);
        
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        // Set the bounds for the scroll pane to position it on the frame
        scrollPane.setBounds(350, 20, 400, 300);

        
        add(scrollPane);
    	
        titleLabel = new JLabel("INPUT ITEMS YOU WANT");
 	    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
 	    titleLabel.setBounds(60, 10, 320, 20);
        
        useridLabel = new JLabel("user id:");
        useridLabel.setBounds(20, 20, 80, 30);
        useridTextField = new JTextField();
        useridTextField.setBounds(100, 20, 200, 30);

        bookingdateLabel = new JLabel("booking date:");
        bookingdateLabel.setBounds(20, 80, 80, 30);
        bookingdateTextField = new JTextField();
        bookingdateTextField.setBounds(100, 80, 200, 30);

        detailsLabel = new JLabel("booking details:");
        detailsLabel.setBounds(20, 140, 80, 30);
        detailsTextField = new JTextField();
        detailsTextField.setBounds(100, 140, 200, 30);

        quantityLabel = new JLabel("quantity:");
        quantityLabel.setBounds(20, 200, 80, 30);
        quantityTextField = new JTextField();
        quantityTextField.setBounds(100, 200, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 250, 80, 30);
        submitButton.addActionListener(this);

        viewusersButton = new JButton("View users ");
        viewusersButton.setBounds(20, 340, 120, 30);
        viewusersButton.addActionListener(this);

        contactusButton = new JButton("contact us");
        contactusButton.setBounds(180, 340, 120, 30);
        contactusButton.addActionListener(this);

        viewstockButton = new JButton("available stock");
        viewstockButton.setBounds(320, 340, 120, 30);
        viewstockButton.addActionListener(this);

        

        add(useridLabel);
        add(useridTextField);
        add(bookingdateLabel);
        add(bookingdateTextField);
        add(detailsLabel);
        add(detailsTextField);
        add(quantityLabel);
        add(quantityTextField);
        add(submitButton);
        add(viewusersButton);
        add(contactusButton);
        add(viewstockButton);
        

        setSize(900, 450);
        setLayout(null);
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
    }
    
    private void displayitems(String[] columnNames) {
  	  try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tsupplier", "222014897", "222014897");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stock");
            
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.setRowCount(0); // Clear existing data from the table

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("category_id"),
                    rs.getString("Name"),
                   
                   
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    
                };
                model.addRow(row);
            }
            
            dataTable = new JTable(model);
            // Add the JTable to a JScrollPane
               JScrollPane scrollPane = new JScrollPane(dataTable);

               // Add the JScrollPane to the JFrame
               add(scrollPane, BorderLayout.CENTER);
               
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
  	
  }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userid = useridTextField.getText();
            String bookingdate = bookingdateTextField.getText();
            String details = detailsTextField.getText();
            String quantity = quantityTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tsupplier", "222014897", "222014897");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO bookings (user_id, booking_date,details,quantity) VALUES (?, ?,?,?)");
                stmt.setString(1, userid);
                stmt.setString(2, bookingdate);
                stmt.setString(3, details);
                stmt.setString(4, quantity);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Request submitted successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == viewusersButton) {
        	 try {
                 Class.forName("com.mysql.cj.jdbc.Driver");
                 Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost:3306/tsupplier", "222014897", "222014897");
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT user_id, username FROM users");
                 
                 StringBuilder result = new StringBuilder();
                 
                 while (rs.next()) {
                     result.append(rs.getString(1) + " " + rs.getString(2) + "\n");
                 }
                 
                 con.close();
                 
                 JOptionPane.showMessageDialog(null, result.toString(), "Database Result", JOptionPane.INFORMATION_MESSAGE);
             } catch (Exception ex) {
                System.out.println(ex);
            }

        } else if (e.getSource() == viewstockButton) {
            displayitems(null);
            
            new bookingform().setVisible(true);
            dispose();
        } else if (e.getSource() == contactusButton) {
            new contactus().setVisible(true);
            dispose();

        }
    }

    public static void main(String[] args) {
        new bookingform();
    }
}
