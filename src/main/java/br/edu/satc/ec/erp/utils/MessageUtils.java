package br.edu.satc.ec.erp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class MessageUtils {

    private static void showAlert(Alert alert, String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void sucess(String title, String header, String content) {
        showAlert(new Alert(Alert.AlertType.INFORMATION), title, header, content);
    }

    public static void error(String title, String header, String content) {
        showAlert(new Alert(Alert.AlertType.ERROR), title, header, content);
    }

    public static boolean confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não");
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sim, nao, cancelar);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == sim) {
            return true;
        }

        return false;
    }

}
