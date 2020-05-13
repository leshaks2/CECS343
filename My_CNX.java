/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matth
 */
public class My_CNX {
    
    public static Connection getConnection(){
          Connection cnx = null;
        try {
            cnx = DriverManager.getConnection("jdbc:derby://localhost:1527/musicDB","app","app");
            return cnx;
        } catch (SQLException ex) {
            Logger.getLogger(My_CNX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
