/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author QUIBIN
 */
public class FXMLMusicQueueController implements Initializable {
    static ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
//    static ArrayList<String> songQueue = new ArrayList<String>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        songID.setCellValueFactory(new PropertyValueFactory<>("song_id"));
        album.setCellValueFactory(new PropertyValueFactory<>("album"));
        songTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        songLength.setCellValueFactory(new PropertyValueFactory<>("song_length"));
    }    
    
//    public static ArrayList<String> addToQueue(String song){
//        songQueue.add(song);
//        return songQueue;
//    }
//    
    public static void addToSongQueue(String songID){
        try{
            int queueNumber;
            Connection conn = DBConnect.connectDB();
                String sql = "SELECT MAX(POSITION) FROM QUEUE";
                ResultSet rs = conn.prepareStatement(sql).executeQuery();
                if(rs.next()) {
                    queueNumber = rs.getInt(1);
                    queueNumber ++;
                }
                else{
                    queueNumber = 1;
                }
                sql = "INSERT INTO QUEUE(position, song) VALUES (" + queueNumber + ", "
                + songID + ")";
                conn.prepareStatement(sql).executeUpdate();
                sql = "SELECT * FROM songs_vw s \n" +
                "INNER JOIN queue q on s.song_id = q.song ORDER BY position";
                rs = conn.prepareStatement(sql).executeQuery();
                while (rs.next()){
                    oblist.add(new ModelTable(rs.getString("song_id"), rs.getString("album"),  
                    rs.getString("title"), rs.getString("artist"),
                    rs.getString("genre"), rs.getString("song_length")));
                }
                System.out.println(oblist.get(0).title);
                songQueueTable.setItems(oblist);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
        
    
    @FXML
    private static TableView<ModelTable> songQueueTable;
    
    @FXML
    private TableColumn<ModelTable, String> songID;

    @FXML
    private TableColumn<ModelTable, String> album;

    @FXML
    private TableColumn<ModelTable, String> songTitle;

    @FXML
    private TableColumn<ModelTable, String> artist;

    @FXML
    private TableColumn<ModelTable, String> genre;

    @FXML
    private TableColumn<ModelTable, String> songLength;

    @FXML
    private Label songPlaying;

    @FXML
    private Label nextSong;
}
