package com.nccs.sms.dao;

import com.nccs.sms.database.ConnectionFactory;
import com.nccs.sms.dto.StudentDTO;
import com.nccs.sms.dto.UserDTO;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentDAO {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String[] searchColumnNames = {"ID", "Full Name", "Date of Birth", "Gender", "Address", "Contact"};
    
    public StudentDAO(){
        con = new ConnectionFactory().getConnection();
    }
    
    public void addstudent(StudentDTO student){
        String query="Insert into user VALUES(?,?,?,?,?,?)";
        try {
            pstmt= con.prepareStatement(query);
            pstmt.setInt(1,student.getId());
            pstmt.setString(2,student.getFullname());
            pstmt.setString(3,student.getDob());
            pstmt.setString(4,student.getGender());
            pstmt.setString(5,student.getAddress());
            pstmt.setString(6,student.getContact());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record Inserted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
    
    public ResultSet getQueryResult() {
        String sql = "SELECT * FROM student";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public DefaultTableModel buildTableModel (ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i<= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new  Vector<Object>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(rs.getObject(i));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
    
    public StudentDTO editStudent(JTable table) {
        StudentDTO studentEdit = new StudentDTO();
        studentEdit.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
        studentEdit.setFullname((String) table.getValueAt(table.getSelectedRow(), 1));
        studentEdit.setDob((String) table.getValueAt(table.getSelectedRow(), 2));
        studentEdit.setGender((String) table.getValueAt(table.getSelectedRow(), 3));
        studentEdit.setAddress((String) table.getValueAt(table.getSelectedRow(), 4));
        studentEdit.setContact((String) table.getValueAt(table.getSelectedRow(), 5));
        return studentEdit;
    }
    
    public void updateStudent(StudentDTO studentUpdate) {
        try {
            String query = "Update student set  fullname= ?, dob = ?, gender =?  address = ?, contact = ? where id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(6, studentUpdate.getId());
            pstmt.setString(1, studentUpdate.getFullname());
            pstmt.setString(2, studentUpdate.getDob());
            pstmt.setString(3, studentUpdate.getGender());
            pstmt.setString(4, studentUpdate.getAddress());
            pstmt.setString(5, studentUpdate.getContact());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "One record updated.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void delete(String value) {
        try {
            String query = "DELETE FROM student where id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "One record deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //for search panel

    /**
     *
     * @return
     */
    public String returnQueryToGetColumnNames() {
        String query = "SELECT id, fullname, dob, gender, address, contact FROM student";
        return query;
    }
    
    //for JComboBox
    public Vector<String> getColumnName(String query) {
        Vector<String> columnNames = new Vector<String>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column =1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }
    //JComboBox ends here
    
    public DefaultTableModel searchStudent(String name, String value) throws Exception {
        String sql = "";
        if ("id".equalsIgnoreCase(name)) {
            sql = "SELECT id, fullname, dob, gender, address, contact FROM student WHERE " + name + "='" +Integer.parseInt(value) + "'";
        } else{
            sql ="SELECT id, fullname, dob, gender, address, contact FROM student WHERE " + name + "='" + value + "'";
        }
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(searchColumnNames[column - 1]);
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        if(!rs.next()) {
            JOptionPane.showMessageDialog(null, "No search found.");
        } else{
            rs.beforeFirst();
            while(rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        }
        return new DefaultTableModel (data, columnNames);
    }
    
    //for loading tables in search panel table
    public Vector<String> getCName() {
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("ID");
        columnNames.add("Full Name");
        columnNames.add("Date of Birth");
        columnNames.add("Gender");
        columnNames.add("Address");
        columnNames.add("Contact");
        return columnNames;
    }
    
}
