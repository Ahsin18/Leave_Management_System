package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.DBConnection;

import java.sql.*;

public class ViewLeavesFrame extends JFrame
{
    JTable  table;
    DefaultTableModel model;

    public ViewLeavesFrame()
    {
        setTitle("View Leave History");
        setSize(600,400);
        setLayout(null); 


        model = new DefaultTableModel();
        table = new JTable(model);


        model.addColumn("ID");
        model.addColumn("From Date");
        model.addColumn("To Date");
        model.addColumn("Type");
        model.addColumn("Reason");

        JScrollPane sp= new JScrollPane(table);
        sp.setBounds(20,20,540,300);
        add(sp);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBounds(200, 320, 180, 35);
        add(deleteBtn);

        deleteBtn.addActionListener(e -> deleteLeave());
        loadData();
        setVisible(true);



    }
    


   void loadData()
   {
    try{
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM leaves";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while(rs.next())
        {
                        model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("from_date"),
                        rs.getString("to_date"),
                        rs.getString("leave_type"),
                        rs.getString("reason")
                });
            }

    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
   }

void deleteLeave() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM leaves WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            pst.executeUpdate();

            // remove from table UI
            model.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Deleted Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

