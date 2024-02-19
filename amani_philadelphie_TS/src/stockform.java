import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class stockform extends JFrame implements ActionListener {
    JLabel catidLabel,nameLabel,descriptionLabel,priceLabel, quantityLabel;
    JTextField catidTextField,nameTextField, descriptionTextField, priceTextField, quantityTextField;
    JButton submitButton, backButton;
   

    stockform() {
    	catidLabel = new JLabel("cat id:");
        catidLabel.setBounds(20, 20, 80, 40);
        catidTextField = new JTextField();
        catidTextField.setBounds(100, 20, 200, 40);

        nameLabel = new JLabel("item name:");
        nameLabel.setBounds(20, 100, 80, 40);
        nameTextField = new JTextField();
        nameTextField.setBounds(100, 100, 200, 40);

        descriptionLabel = new JLabel("description:");
        descriptionLabel.setBounds(20, 180, 80, 40);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(100, 180, 200, 40);

        priceLabel = new JLabel("price:");
        priceLabel.setBounds(20, 260, 80, 40);
        priceTextField = new JTextField();
        priceTextField.setBounds(100, 260, 200, 40);

        quantityLabel = new JLabel("quantity:");
        quantityLabel.setBounds(20, 340, 80, 40);
        quantityTextField = new JTextField();
        quantityTextField.setBounds(100, 340, 200, 40);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 390, 80, 30);
        submitButton.addActionListener(this);


        backButton = new JButton("Back");
        backButton.setBounds(220, 390, 80, 30);
        backButton.addActionListener(this);


        add(catidLabel);
        add(catidTextField);
        add(nameLabel);
        add(nameTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(priceLabel);
        add(priceTextField);
        add(quantityLabel);
        add(quantityTextField);
        add(submitButton);
        add(backButton);


        setSize(500, 650);
        setLayout(null);
        setVisible(true);
        setTitle("ADD TO STOCK FORM");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {

            String cat_id = catidTextField.getText();
            String name = nameTextField.getText();
            String description = descriptionTextField.getText();
            String price = priceTextField.getText();
            String quantity = quantityTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO stock (category_id,name,description,price,quantity) VALUES (?,?,?,?,?)");
                stmt.setString(1, cat_id);
                stmt.setString(2, name);
                stmt.setString(3, description);
                stmt.setString(4, price);
                stmt.setString(5, quantity);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "New stock submitted successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == backButton) {
            new adminview().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        new stockform();
    }
}


