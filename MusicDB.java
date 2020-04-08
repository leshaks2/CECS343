/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicdb;

import java.sql.*;
//import java.util.Scanner;

public class MusicDB {

    static String DB_URL = "jdbc:mysql://localhost:3306/musicdb";

    public static void main(String[] args) {
        // TODO code application logic here
        Connection conn = null; //initialize the connection
        PreparedStatement pstmt; //initialize the prepared statement that we're using
        
        try{
            //generic status statement 
            System.out.println("Connecting to database...");
            
            //create class for MySQL JDBC driver 
            Class.forName("com.mysql.jdbc.Driver");
            //login to DB
            //had to download and use version 8.0.19 of connector/j
            //utilizing admin account (root) since it is localhost
            conn = DriverManager.getConnection(DB_URL, "root","root");

            //generic status statement
            System.out.println("Database connection open...");
            
            //login validation statement using prepared statements
            //search for user in db
            String sql = "select login_name from user_login where login_name = ?"; 
            pstmt = conn.prepareStatement(sql);
            //use setString to change ? to username from login form
            pstmt.setString(1, "VALUE FROM USERNAME CONTROL");
            //run query to detemine if valid user
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                //if a valid user check password
                sql = "select login_name from user_login where login_name = ? and password = ?"; 
                pstmt = conn.prepareStatement(sql);
                //use setString to change ? to username and password from login form
                pstmt.setString(1, "VALUE FROM USERNAME CONTROL");
                pstmt.setString(2, "VALUE FROM PASSWORD CONTROL");
                
                rs = pstmt.executeQuery();
                if(rs.next()){
                    //login and move to music player
                } else {
                    //invalid password - display message
                }
            } else {
                //not a valid user and display message stating this
            }
            
            //sample insert statement
            sql = "insert into user_login (login_name, password) values (?, ?)"; 
            pstmt = conn.prepareStatement(sql);
            //use setString to change ? to username and password from login form
            pstmt.setString(1, "VALUE FROM USERNAME CONTROL");
            pstmt.setString(2, "VALUE FROM PASSWORD CONTROL");
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
}
