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
import javafx.stage.Stage;

public class FXMLMusicPlayerController implements Initializable{
    
    static private MediaPlayer mediaPlayer; 
    static private Media media;
    static private Image albumArt;
    private Status mpStatus;
    
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
//       File search = new File("C:\\Users\\joshu\\Desktop\\iridescence\\" + searchBar.getText() + ".mp3");
//            if(search.exists()){            
//               songValid.setText("O");
//               media = new Media(search.toURI().toString());
//               mediaPlayer = new MediaPlayer(media);
//               songPlaying = searchBar.getText();
//               File albumCoverArt = new File("C:\\Users\\joshu\\Desktop\\iridescence\\iridescence.png");
//            if(albumCoverArt.exists()){
//               albumArt = new Image (albumCoverArt.toURI().toString());
//               //mediaPlayer.setAutoPlay(true);
//               //nowPlaying.setText("Now Playing... " + searchBar.getText());
//            }
//            else{
//               songValid.setText("X");
//            }
//        }
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
    
    public static String findSongFile(String songID){
        try {
            String songFile = null;
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT song_file FROM song WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                songFile = rs.getString("song_file");
            }
            return songFile;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static String findAlbumCover(String songID){
        try {
            String albumFile = null;
            Connection conn = DBConnect.connectDB();
            String sql = "SELECT album_cover \n" +
            "FROM album\n" +
            "INNER JOIN song s on album.album_id = s.album\n" +
            "WHERE song_id = " + songID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                albumFile = rs.getString("album_cover");
            }
            return albumFile;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    public static void newSong(String songFile, String album, String artistName, String title, String songLength){
        System.out.println(songFile);
        System.out.println(album);
        System.out.println(artistName);
        System.out.println(title);
        System.out.println(songLength);
        File search = new File(songFile);
        if(search.exists()){
            System.out.println("SEARCH EXISTS");
            media = new Media(search.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
//            endDuration.setText(songLength);
            File albumCoverArt = new File(album);
            if(albumCoverArt.exists()){
                System.out.println("COVER EXISTS");
                albumArt = new Image (albumCoverArt.toURI().toString());
//          albumCover.setImage(albumArt);
            }
//          nowPlaying.setText(title);
//          artistPlaying.setText(artistName);
            mediaPlayer.stop();
            mediaPlayer.setAutoPlay(true);
        }    
        else{
            
        }
    }
    
    
    @FXML
    void skipBackButtonAction(ActionEvent event) {

    }

    @FXML
    void skipForwardButtonAction(ActionEvent event) {

    }
    
    String formatTime(Duration duration){
        int time = (int)Math.floor(duration.toSeconds());
        System.out.println("musicplayer.FXMLMusicPlayerController.formatTime()");
        int minutes = (int)Math.floor(time/60);
        int seconds = time % 60;
        if (seconds < 10) {
            return String.format("%d:0%d", minutes, seconds);
        }
        return String.format("%d:%d", minutes, seconds);
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