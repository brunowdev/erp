package br.edu.satc.ec.erp.login;

import br.edu.satc.ec.erp.login.persistence.LoginDao;
import br.edu.satc.ec.erp.utils.FormConstraints;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * Created by bruno.470113 on 19/06/2017.
 */
public class LoginController {

    @FXML
    private TextField user;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        FormConstraints.maxField(password, 10);
        FormConstraints.maxField(user, 20);
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(action -> {
            String sessionID = authorize();
            if (sessionID != null) {
                loginManager.authenticated(sessionID);
            }
        });
    }

    private String authorize() {

        LoginDao dao = new LoginDao();

        final boolean auth = dao.login(user.getText(), password.getText());

        final String token = auth ? generateSessionID() : null;

        if (isNull(token)) {
            exibirAlertaAcessoNegado();
        }

        return token;
    }

    public void exibirAlertaAcessoNegado() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Acesso negado");
        alert.setHeaderText("Usuário e/ou senha inválidos.");
        alert.setContentText("Se você está com problemas para acessar o sistema, entre em contato com o administrador.");
        alert.showAndWait();
    }

    private String generateSessionID() {
        return UUID.randomUUID().toString();
    }
}
