package musicplayer;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener; 
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class FXMLMusicPlayerController implements Initializable{
    private static MediaPlayer mediaPlayer; 
    private Media media;
    private Image albumArt;
    private static Status mpStatus;
    private static String newSongID = "";
    
    @FXML
    private Button playANDpause;

    @FXML
    private Button skipForward;

    @FXML
    private Button skipBack;

    @FXML
    private Slider progressBar;
    
    @FXML
    private Slider volumeSlider;

    @FXML
    private static Label nowPlaying;
    
    @FXML
    private static Label artistPlaying;

    @FXML
    private Button searchButton;

    @FXML
    private Label playTime;

    @FXML
    private static Label endDuration;
    
    @FXML
    private static ImageView albumCover;
    
    @FXML
    private Button refresh;

    @FXML
    void refreshButtonAction(ActionEvent event) {
        if(!newSongID.equals("")){
            File search = new File(findSongFile(newSongID));
            if(search.exists()){
                media = new Media(search.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                endDuration.setText(findSongLength(newSongID));
                File albumCoverArt = new File(findAlbumCover(newSongID));
                if(albumCoverArt.exists()){
                    System.out.println("COVER EXISTS");
                    albumArt = new Image (albumCoverArt.toURI().toString());
              albumCover.setImage(albumArt);
                }
              nowPlaying.setText(findSongTitle(newSongID));
              artistPlaying.setText(findArtist(newSongID));
              playANDpause.setText(">");
              newSongID = "";
            }
       }
    }
    
    static void refresh(String songID){
//        mpStatus = mediaPlayer.getStatus();
//        if(mpStatus == Status.PLAYING){
//            mediaPlayer.stop();
//        }
        newSongID = songID;
    }
    
    public static String findSongFile(String songID){
        try {
            String songFile = null;
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT song_file FROM song WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                songFile = rs.getString("song_file");
                return songFile;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static String findAlbumCover(String songID){
        try {
            String albumFile = null;
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT album_cover FROM album INNER JOIN "
                    + "song s on album.album_id = s.album WHERE song_id = " + 
                    songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                albumFile = rs.getString("album_cover");
                return albumFile;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static String findSongTitle(String songID){
        try {
            String songTitle = "";
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT title FROM SONG WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                songTitle = rs.getString("title");
                return songTitle;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static String findArtist(String songID){
        try {
            String songArtist = "";
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT artist_name FROM artist "
                    + "INNER JOIN song s on artist.artist_id = "
                    + "s.artist WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                songArtist = rs.getString("artist_name");
                return songArtist;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static String findSongLength(String songID){
        try {
            String songLength = "";
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT song_length FROM SONG WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            if (rs.next()) {
                songLength = rs.getString("song_length");
                return songLength;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    @FXML
    void playANDpauseButtonAction(ActionEvent event) {
        mpStatus = mediaPlayer.getStatus();
        
        if(mpStatus == Status.PAUSED || mpStatus == Status.STOPPED || mpStatus == Status.READY){
            mediaPlayer.play();
//            albumCover.setImage(albumArt);
            playANDpause.setText("||");
//            endDuration.setText(formatTime(mediaPlayer.getMedia().getDuration()));
        }
        else if(mpStatus == Status.PLAYING){
            mediaPlayer.pause();
            playANDpause.setText(">");
        }        
    }

    
    @FXML
    void searchButtonAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMusicSearch.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Music Search");
            stage.setScene(new Scene(root1));
            stage.show();

        }catch (Exception e){
            System.out.println("Can't load new window.");
        }
    }
    
    @FXML
    void skipBackButtonAction(ActionEvent event) {

    }

    @FXML
    void skipForwardButtonAction(ActionEvent event) {
//        try {
//            Connection conn = DBConnect.connectDB();
//            String sql = "SELECT position FROM queue WHERE song = "
//            sql = "SELECT * FROM songs_vw s \n" +
//            "INNER JOIN queue q on s.song_id = q.song ORDER BY position";
//            ResultSet rsQueue = conn.prepareStatement(sql).executeQuery();
//        }catch (Exception e){
//            
//        }
//        
//        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        progressBar.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
               if (progressBar.isValueChanging()) {
                  mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(progressBar.getValue() / 100.0));
                  
                }
            }
        });
    
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
               if (volumeSlider.isValueChanging()) {
                   mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
               }
            }
        });

    }
                
}