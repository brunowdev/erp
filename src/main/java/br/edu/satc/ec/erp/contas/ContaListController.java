package br.edu.satc.ec.erp.contas;

import br.edu.satc.ec.erp.contas.entity.Conta;
import br.edu.satc.ec.erp.contas.persistence.ContaDao;
import br.edu.satc.ec.erp.utils.MessageUtils;
import br.edu.satc.ec.erp.utils.components.MaskField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class ContaListController {

    @FXML
    private TableView<Conta> contaTable;

    @FXML
    private TableColumn<Conta, String> columnCnpj;

    @FXML
    private TableColumn<Conta, String> columnNome;

    @FXML
    private TableColumn<Conta, String> columnSituacao;

    @FXML
    private Label razaoSocialTf;

    @FXML
    private Label nomeFantasiaTf;

    @FXML
    private Label cnpjTf;

    @FXML
    private Label situacaoTf;

    private Stage dialogStage;

    private ContaDao dao;

    @FXML
    private void initialize() {
        this.dao = new ContaDao();
        refresh();
    }

    private String formatCpnj(String cnpj) {
        MaskField formatCnpj = new MaskField();
        formatCnpj.setMask("DD.DDD.DDD/DDDD-DD");
        formatCnpj.setPlainText(cnpj);
        return formatCnpj.getText();
    }

    public void refresh() {

        contaTable.setItems(FXCollections.observableArrayList(dao.getAll()));

        columnCnpj.setCellValueFactory(
                cellData -> new SimpleStringProperty(formatCpnj(cellData.getValue().getCnpj()))
        );

        columnNome.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNomeFantasia()));

        columnSituacao.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSituacao().toString()));

        contaTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarConta(newValue));

    }

    private void selecionarConta(Conta conta) {
        if (Objects.nonNull(conta)) {
            razaoSocialTf.setText(conta.getRazaoSocial());
            nomeFantasiaTf.setText(conta.getNomeFantasia());
            cnpjTf.setDisable(true);
            cnpjTf.setText(formatCpnj(conta.getCnpj()));
            situacaoTf.setText(conta.getSituacao().toString());
        } else {
            razaoSocialTf.setText("");
            nomeFantasiaTf.setText("");
            cnpjTf.setText("");
            situacaoTf.setText("");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setTitle("Gerenciando Contas");
    }

    @FXML
    private void handleNovaConta() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ContaDataModel.class.getResource("/fxml/contas/ContaAddEdit.fxml"));
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

        ContaAddEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
        refresh();
    }

    @FXML
    private void handleEditarConta() {

        int selectedIndex = contaTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            Conta conta = contaTable.getItems().get(selectedIndex);
            ContaDataModel model = new ContaDataModel(conta);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ContaDataModel.class.getResource("/fxml/contas/ContaAddEdit.fxml"));
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

            ContaAddEditController controller = loader.getController();
            controller.setConta(model);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            refresh();

        } else {
            MessageUtils.error("Nenhum registro selecionado",
                    "Nenhum registro selecionado para editar.",
                    "Selecione um registro antes de executar esta ação.");

        }

    }

    @FXML
    private void handleExcluir() {
        int selectedIndex = contaTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            Conta conta = contaTable.getItems().get(selectedIndex);

            boolean continuar = MessageUtils.confirmationDialog("Sua atenção é necessária",
                    String.format("Deseja realmente excluir a conta %s?", conta.getRazaoSocial()),
                    "Esta alteração não poderá ser desfeita.");

            if (continuar) {
                if (dao.delete(conta.getId())) {
                    contaTable.getItems().remove(selectedIndex);
                    MessageUtils.sucess("Sucesso ao excluir conta", "Conta excluída com sucesso.", String.format("Conta removida: %d", conta.getId()));
                }
            }

        } else {
            MessageUtils.error("Nenhum registro selecionado",
                    "Nenhum registro selecionado para exclusão.",
                    "Selecione um registro antes de executar esta ação.");

        }
    }


}
