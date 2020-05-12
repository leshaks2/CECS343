/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author QUIBIN
 */
public class MusicPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMusicPlayer.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    String DB_URL = "jdbc:derby://localhost:1527/musicdb";
    Connection conn = null;
    PreparedStatement pstmt;
    try {
            System.out.println("Connecting to the Database");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_URL, "app", "app");
            System.out.println("Database Connection Open");
            String sql = "SELECT title FROM song INNER JOIN artist a on song.artist = a.artist_id WHERE a.artist_name = 'BROCKHAMPTON'"; 
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString("title"));
            }
        } catch (Exception e){
            System.out.println(e);
        }
    launch(args);
    }
}
