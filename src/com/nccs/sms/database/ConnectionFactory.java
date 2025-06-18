package com.nccs.sms.database;
import java.sql.*;
public class ConnectionFactory {
    Connection con;
    Statement stmt;
    ResultSet rs;
    boolean flag = false;
    
    public ConnectionFactory(){
         try {
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
                stmt= con.createStatement();
        }catch(Exception e) {
            e.printStackTrace(); 
        }
    }
    
    public boolean checkLogin(String username, String password){
        try{
            String query = "Select * from user where username = '" +username +"' and password = '"+password+"'";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                return true;
            }
        }catch(Exception e) {
            e.printStackTrace(); 
        }
        return false;
   }
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return con;
    }
}
