/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicloginregister;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author matth
 */
public class My_CNX {
    
    public static Connection getConnection(){
        Connection cnx = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/music_login", "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return cnx;
    }
}
