package br.edu.satc.ec.erp.app;

import br.edu.satc.ec.erp.login.LoginManager;
import br.edu.satc.ec.erp.persistence.connection.ConnectionUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

import static br.edu.satc.ec.erp.utils.LoggerUtils.info;
import static br.edu.satc.ec.erp.utils.LoggerUtils.severe;
import static java.util.Objects.nonNull;

public class Main extends Application {

    private Connection connection;
    public static Main app;

    @Override
    public void stop() throws Exception {

        if (nonNull(connection)) {
            info("Tentando fechar conexão...");
            connection.close();
            info("Conexão fechada.");
        }

        super.stop();
    }

    @Override
    public void init() throws Exception {

        app = this;

        info("Tentando abrir conexão com banco de dados...");
        this.connection = ConnectionUtils.getConnection();

        if (nonNull(connection)) {
            info("Conexão aberta com sucesso.");
        } else {
            severe("Ocorreu um erro ao abrir conexão.");
            stop();
            System.exit(0);
        }

        super.init();
    }

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Login");
        stage.setWidth(320);
        stage.setHeight(200);
        Scene scene = new Scene(new StackPane());
        stage.setResizable(false);

        LoginManager loginManager = new LoginManager(scene);
        loginManager.setDialogStage(stage);
        loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.show();
    }

}
