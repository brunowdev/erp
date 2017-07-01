package br.edu.satc.ec.erp.login;

import br.edu.satc.ec.erp.app.Main;
import br.edu.satc.ec.erp.categoriasproduto.CategoriaProdutoDataModel;
import br.edu.satc.ec.erp.categoriasproduto.CategoriaProdutoListController;
import br.edu.satc.ec.erp.contas.ContaDataModel;
import br.edu.satc.ec.erp.contas.ContaListController;
import br.edu.satc.ec.erp.produtos.ProdutoListController;
import br.edu.satc.ec.erp.utils.MessageUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by bruno.470113 on 19/06/2017.
 */
public class MainViewController {

    @FXML
    private Button logoutButton;

    @FXML
    private Label sessionLabel;

    private Stage dialogStage;

    private LoginManager loginManager;

    @FXML
    public void initialize() {
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
    }

    public void setManager(LoginManager manager) {
        this.loginManager = manager;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setTitle("Menu principal");
    }

    @FXML
    private void handleLogout() {
        loginManager.logout();
    }

    @FXML
    private void handleSobre() {
        MessageUtils.sucess("Sobre", "ERP", "Criado por Bruno Bitencour Luiz");
    }

    @FXML
    private void handleSair() {
        try {
            Main.app.stop();
        } catch (Exception e) {
            MessageUtils.error("Erro ao fechar aplicação.",
                    "Ocorreu um erro ao fechar aplicação.",
                    "A finalização será forçada.");
        }
        System.exit(0);
    }

    @FXML
    private void handleContas() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ContaDataModel.class.getResource("/fxml/contas/ContaList.fxml"));
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        ContaListController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
    }

    @FXML
    private void handleCategoriasProduto() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CategoriaProdutoDataModel.class.getResource("/fxml/categoriasproduto/CategoriaProdutoList.fxml"));
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CategoriaProdutoListController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
    }

    @FXML
    private void handleProdutos() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CategoriaProdutoDataModel.class.getResource("/fxml/produtos/ProdutoList.fxml"));
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        ProdutoListController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
    }


}
