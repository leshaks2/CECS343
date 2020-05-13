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
    String song_id, album, title, artist, genre, song_length;

    public ModelTable(String song_id, String album, String title, String artist, String genre, String song_length) {
        this.song_id = song_id;
        this.album = album;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.song_length = song_length;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
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

    public String getSong_length() {
        return song_length;
    }

    public void setSong_length(String song_length) {
        this.song_length = song_length;
    }
    
    
}
