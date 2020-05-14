/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author QUIBIN
 */
public class FXMLMusicSearchController implements Initializable {
    
    /**
     * Initializes the controller class.
     * @param url
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
    
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    ArrayList<String> songIDQueue = new ArrayList<>();
    static String selectedForAddition;
    
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<ModelTable> SongResult;

    @FXML
    private TableColumn<ModelTable, String> songID;

    @FXML
    private TableColumn<ModelTable, String> songTitle;

    @FXML
    private TableColumn<ModelTable, String> artist;

    @FXML
    private TableColumn<ModelTable, String> genre;

    @FXML
    private TableColumn<ModelTable, String> album;

    @FXML
    private TableColumn<ModelTable, String> songLength;
    
    @FXML
    private Button queueButton;

    @FXML
    void queueButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMusicQueue.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Music Queue");
            stage.setScene(new Scene(root1));
            stage.show();

        }catch (Exception e){
            System.out.println("Can't load new window.");
        }
    }

    @FXML
    public void handleAddToQueue(ActionEvent event) {
        selectedForAddition = SongResult.getSelectionModel().getSelectedItem().getSong_id();
        
        if(selectedForAddition == null){
            Alert error = AlertMaker.AlertErrorMaker("No Song Selected", "Please Select a Song to Add to Queue");
            error.showAndWait();
        }
        else{
            songIDQueue.add(selectedForAddition);
            FXMLMusicQueueController.addToSongQueue(songIDQueue);
        }
//        Alert confirm = AlertMaker.AlertConfirmationMaker("Add to Queue", "Are you sure you want to add ? " 
//                + selectedForAddition.getTitle() + " to queue?");
//        Optional<ButtonType> answer = confirm.showAndWait();
//        if(answer.get() == ButtonType.OK){
//            FXMLMusicQueueController.addToQueue(selectedForAddition);
//        }
//        else {
//            Alert simple = AlertMaker.AlertSimpleMaker("Addition Cancelled", "Song Addition Cancelled");
//            simple.showAndWait();
//        }
    }
    
    
    @FXML
    void handlePlaySong(ActionEvent event) {
        selectedForAddition = SongResult.getSelectionModel().getSelectedItem().getSong_id();
        
        if(selectedForAddition == null){
            Alert error = AlertMaker.AlertErrorMaker("No Song Selected", "Please Select a Song to Play");
            error.showAndWait();
        }
        else{
            String songFile = FXMLMusicPlayerController.findSongFile(selectedForAddition);
            String albumCover = FXMLMusicPlayerController.findAlbumCover(selectedForAddition);
            String songName = SongResult.getSelectionModel().getSelectedItem().title;
            String songLen = SongResult.getSelectionModel().getSelectedItem().song_length;
            String artistName = SongResult.getSelectionModel().getSelectedItem().artist;
            FXMLMusicPlayerController.newSong(songFile, albumCover, songName, artistName, songLen);
        }
    }
    
    @FXML
    void searchButtonAction(ActionEvent event) {
        if(searchBar.getText().equals("")){
            oblist.clear();
            Alert a = AlertMaker.AlertErrorMaker("Search Missing", "Please Specify a Search");
            a.showAndWait();
        }
        else{
            try {
                oblist.clear();
                Connection conn = DBConnect.connectDB();
                String sql = "SELECT * FROM SONGS_VW WHERE lower(SONGS_VW.title) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.artist) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.album) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.genre) LIKE '%" + searchBar.getText().toLowerCase() + "%'";
                ResultSet rs = conn.prepareStatement(sql).executeQuery();

                int rsCount = 0;
                
                while (rs.next()) {
//                    System.out.println(rs.getString("song_id"));
//                    System.out.println(rs.getString("album"));
//                    System.out.println(rs.getString("title"));
//                    System.out.println(rs.getString("artist"));
//                    System.out.println(rs.getString("genre"));
//                    System.out.println(rs.getString("song_length"));
                    oblist.add(new ModelTable(rs.getString("song_id"), rs.getString("album"),  
                    rs.getString("title"), rs.getString("artist"),
                    rs.getString("genre"), rs.getString("song_length")));
                    rsCount ++;
                }
                
                if (rsCount == 0){
                    Alert a = AlertMaker.AlertErrorMaker("Search Not Found", "Please Search Again");
                    a.showAndWait();
                }
             
            } catch (Exception e) {
                System.out.println(e);
            }
        SongResult.setItems(oblist);
        }
    }    
}
