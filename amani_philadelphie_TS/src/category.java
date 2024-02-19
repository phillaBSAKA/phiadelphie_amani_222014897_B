import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class category extends JFrame implements ActionListener {

    // UI components declaration
    private JLabel categoryNameLabel;
    private JTextField categoryNameField;
    private JButton addButton, deleteButton, backButton;
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;

    public category() {
        setTitle("Category");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 1)); // Set GridLayout with 2 rows and 1 column

        categoryNameLabel = new JLabel("Category Name:");
        formPanel.add(categoryNameLabel);

        categoryNameField = new JTextField();
        formPanel.add(categoryNameField);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);


        String[] columnNames = {"category id","Category Name"};
        categoryTableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(categoryTableModel);
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM category");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("category_id"),rs.getString("category_name")};
                model.addRow(row);
            }
            
            categoryTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(categoryTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete category");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        backButton = new JButton("Back to Admin View");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addcategory();
        } else if (e.getSource() == deleteButton) {
            deletecategory();
        } else if (e.getSource() == backButton) {
            new adminview().setVisible(true);
            dispose();
        }
    }


    private void addcategory() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");



            // Prepared statement
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO category (category_name) VALUES (?)");
            stmt.setString(1, categoryNameField.getText());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "category added successfully!");
                categoryNameField.setText("");
                refreshcategoryTable();
            } else {
                JOptionPane.showMessageDialog(this, "Insertion failed. Please try again.");
            }


            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    private void deletecategory() {
        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");


            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow >= 0) {

                int category_id = (int) categoryTable.getValueAt(selectedRow, 0);

                // Prepared statement
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM category WHERE category_id = ?");
                stmt.setInt(1, category_id);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "category deleted successfully!");
                    refreshcategoryTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a category to delete.");
            }


            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void refreshcategoryTable() {
        try {

            String[] columnNames = {"category id","category Name"};
            categoryTableModel.setRowCount(0);





            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM category");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("category_id"),rs.getString("category_name")};
                model.addRow(row);
            }


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        category category = new category();
        category.setVisible(true);
    }
}



