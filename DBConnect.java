package musicplayer;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author QUIBIN
 */
public class DBConnect {
    public static Connection connectDB(){
    try {
        String DB_URL = "jdbc:derby://localhost:1527/musicdb";
        System.out.println("Connecting to the Database");
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection conn = DriverManager.getConnection(DB_URL, "app", "app");
        System.out.println("Database Connection Open");
        return conn;
    } catch (Exception e){
        System.out.println(e);
    }
    return null;
    }
}
