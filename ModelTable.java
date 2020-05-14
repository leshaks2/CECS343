/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

/**
 *
 * @author QUIBIN
 */
public class ModelTable {
    String songID, album, title, artist, genre, songLength;

    public ModelTable(String songID, String album, String title, String artist, String genre, String songLength) {
        this.songID = songID;
        this.album = album;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.songLength = songLength;
    }

    public String getSongID() {
        return songID;
    }

    public void setSong_id(String songID) {
        this.songID = songID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }
    
    
}
