import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class adminview extends JFrame implements ActionListener {

    private JTable viewbookingsTable;
    private JButton categoryButton, stockformButton, viewcontactusButton,delButton;

    public adminview() {
        setTitle("Admin Dashboard");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);


        String[] columnNames = {"ID", "booking date", "details", "quantity"};


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookings");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("user_id"), rs.getDate("booking_date"), rs.getString("details"),rs.getInt("quantity")};
                model.addRow(row);
            }


            viewbookingsTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(viewbookingsTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }

        // Create buttons
        JPanel buttonPanel = new JPanel();
        categoryButton = new JButton("categories");
        categoryButton.addActionListener(this);
        buttonPanel.add(categoryButton);

        stockformButton = new JButton("Add stock");
        stockformButton.addActionListener(this);
        buttonPanel.add(stockformButton);

        viewcontactusButton = new JButton("Messages");
        viewcontactusButton.addActionListener(this);
        buttonPanel.add(viewcontactusButton);
        
        delButton = new JButton("Delete");
        delButton.addActionListener(this);
        buttonPanel.add(delButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == categoryButton) {

            category category = new category();
            category.setVisible(true);
            dispose();

        } else if (e.getSource() == stockformButton) {

            stockform stockform = new stockform();
            stockform.setVisible(true);
            dispose();

        } else if (e.getSource() == viewcontactusButton) {

            viewcontactus viewcontactus = new viewcontactus();
            viewcontactus.setVisible(true);
            dispose();

        }else if (e.getSource() == delButton) {
        	
        	int selectedRow = viewbookingsTable.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer)viewbookingsTable.getValueAt(selectedRow, 0);
                Date date = (Date) viewbookingsTable.getValueAt(selectedRow, 1);
                //String message = (String) feedbackTable.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the record for " + id + " " + date + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookings WHERE user_id = ?");
                        stmt.setInt(1, id);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            // Refresh the table after deletion
                            
                            new adminview().setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to delete record.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        conn.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }

    public static void main(String[] args) {
        adminview adminview = new adminview();
        adminview.setVisible(true);
    }

}

