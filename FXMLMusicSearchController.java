package musicplayer;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        queueSongID.setCellValueFactory(new PropertyValueFactory<>("song_id"));
        queueAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        queueSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        queueArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        queueGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        queueSongLength.setCellValueFactory(new PropertyValueFactory<>("song_length"));
    }    
    ObservableList<ModelTable> searchList = FXCollections.observableArrayList();
    ObservableList<ModelTable> queueList = FXCollections.observableArrayList();
    ArrayList<String> songIDQueue = new ArrayList<>();
    static String selectedForAddition;
    static ModelTable selectedForQueue;
    
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<ModelTable> songResult;

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
    private TableView<ModelTable> queue;

    @FXML
    private TableColumn<ModelTable, String> queueSongID;

    @FXML
    private TableColumn<ModelTable, String> queueAlbum;

    @FXML
    private TableColumn<ModelTable, String> queueSongTitle;

    @FXML
    private TableColumn<ModelTable, String> queueArtist;

    @FXML
    private TableColumn<ModelTable, String> queueGenre;

    @FXML
    private TableColumn<ModelTable, String> queueSongLength;

    
    @FXML
    private Button queueButton;


    @FXML
    public void handleAddToQueue(ActionEvent event) {
        selectedForQueue = songResult.getSelectionModel().getSelectedItem();
        
        if(selectedForQueue == null){
            Alert error = AlertMaker.AlertErrorMaker("No Song Selected", "Please Select a Song to Add to Queue");
            error.showAndWait();
        }
        else{            
            selectedForQueue = songResult.getSelectionModel().getSelectedItem();
            queueList.clear();
            queueList.add(new ModelTable(selectedForQueue.getSongID(), selectedForQueue.getAlbum(),  
            selectedForQueue.getTitle(), selectedForQueue.getArtist(),
            selectedForQueue.getGenre(), selectedForQueue.getSongLength()));
            queue.getItems().add(queueList.get(0));
        }
    }
    
    @FXML
    void handlePlaySong(ActionEvent event) {
        selectedForAddition = queue.getSelectionModel().getSelectedItem().getSongID();
        
        if(selectedForAddition == null){
            Alert error = AlertMaker.AlertErrorMaker("No Song Selected", "Please Select a Song to Play");
            error.showAndWait();
        }
        else{
            String songFile = FXMLMusicPlayerController.findSongFile(selectedForAddition);
            String albumCover = FXMLMusicPlayerController.findAlbumCover(selectedForAddition);
            String songName = queue.getSelectionModel().getSelectedItem().getTitle();
            String songLen = queue.getSelectionModel().getSelectedItem().getSongLength();
            String artistName = queue.getSelectionModel().getSelectedItem().getArtist();
            FXMLMusicPlayerController.newSong(songFile, albumCover, songName, artistName, songLen);
        }
    }
    
    @FXML
    void searchButtonAction(ActionEvent event) {
        if(searchBar.getText().equals("")){
            searchList.clear();
            Alert a = AlertMaker.AlertErrorMaker("Search Missing", "Please Specify a Search");
            a.showAndWait();
        }
        else{
            try {
                searchList.clear();
                Connection conn = DBConnect.connectDB();
                String sql = "SELECT * FROM SONGS_VW WHERE lower(SONGS_VW.title) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.artist) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.album) LIKE '%" + searchBar.getText().toLowerCase() + "%' OR "
                        + "lower(SONGS_VW.genre) LIKE '%" + searchBar.getText().toLowerCase() + "%'";
                ResultSet rs = conn.prepareStatement(sql).executeQuery();

                int rsCount = 0;
                
                while (rs.next()) {
                    searchList.add(new ModelTable(rs.getString("song_id"), rs.getString("album"),  
                    rs.getString("title"), rs.getString("artist"),
                    rs.getString("genre"), rs.getString("song_length")));
                    rsCount ++;
                }
                if (rsCount == 0){
                    Alert a = AlertMaker.AlertErrorMaker("Search Not Found", "Please Search Again");
                    a.showAndWait();
                }
             
            } catch (Exception e) {
                e.printStackTrace();
            }
        songResult.setItems(searchList);
        }
    }    
}
