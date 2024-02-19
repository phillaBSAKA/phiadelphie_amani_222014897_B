import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class viewcontactus extends JFrame implements ActionListener {

    private JTable feedbackTable;
    private JButton  backButton;

    public viewcontactus() {
        setTitle("FEEDBACK");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null);


        String[] columnNames = {"NAME", "EMAIL", "PHONE NUMBER", "MESSAGE"};


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amani_philadelphie_ts", "222014897", "222014897");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contactus");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getString("name"), rs.getString("email"), rs.getString("phone_number"),rs.getString("message")};
                model.addRow(row);
            }


            feedbackTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(feedbackTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }


        JPanel buttonPanel = new JPanel();
        
       


        backButton = new JButton("back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {

            new adminview().setVisible(true);
            dispose();

        }
    }

    public static void main(String[] args) {
        viewcontactus viewcontactus = new viewcontactus();
        viewcontactus.setVisible(true);
    }

}


