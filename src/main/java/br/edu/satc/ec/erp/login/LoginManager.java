package br.edu.satc.ec.erp.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Created by bruno.470113 on 19/06/2017.
 */
public class LoginManager {
    private Scene scene;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public void authenticated(String sessionID) {
        showMainView(sessionID);
    }

    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/login/login.fxml")
            );
            scene.setRoot(loader.load());
            LoginController controller =
                    loader.getController();

            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(SEVERE, null, ex);
        }
    }

    private void showMainView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/Menu.fxml")
            );
            scene.setRoot(loader.load());
            MainViewController controller =
                    loader.getController();
            controller.setManager(this);
            controller.setDialogStage(dialogStage);

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(SEVERE, null, ex);
        }
    }
}
