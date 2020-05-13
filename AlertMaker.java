/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;
import javafx.scene.control.Alert;

/**
 *
 * @author QUIBIN
 */
class AlertMaker {
    public static Alert AlertErrorMaker(String title, String message){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(message);
        a.setHeaderText(null);
        return a;
    }
    public static Alert AlertConfirmationMaker(String title, String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.setContentText(message);
        a.setHeaderText(null);
        return a;
    }
    public static Alert AlertSimpleMaker(String title, String message){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(message);
        a.setHeaderText(null);
        return a;
    }
}
