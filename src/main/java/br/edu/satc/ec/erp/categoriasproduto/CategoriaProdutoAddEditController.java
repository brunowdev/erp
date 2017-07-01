package br.edu.satc.ec.erp.categoriasproduto;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.categoriasproduto.persistence.CategoriaProdutoDao;
import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.utils.FormConstraints;
import br.edu.satc.ec.erp.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class CategoriaProdutoAddEditController {

    @FXML
    private TextField nome;

    @FXML
    private TextField descricao;

    @FXML
    private ComboBox<Situacao> situacao;

    private Stage dialogStage;
    private CategoriaProdutoDataModel model;
    private boolean editando = false;

    @FXML
    private void initialize() {
        situacao.setItems(FXCollections.observableArrayList(Arrays.asList(Situacao.values())));
        situacao.getSelectionModel().selectFirst();

        FormConstraints.maxField(nome, 50);
        FormConstraints.maxField(descricao, 5000);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        if (nonNull(model) && nonNull(model.getId())) {
            editando = true;
            dialogStage.setTitle("Editando categoria");
        } else {
            model = new CategoriaProdutoDataModel();
            editando = false;
            dialogStage.setTitle("Cadastrando categoria");
        }

    }

    public void setCategoria(CategoriaProdutoDataModel model) {
        this.model = model;
        nome.setText(model.getNome());
        descricao.setText(model.getDescricao());
        situacao.getSelectionModel().select(model.getSituacao());
    }

    @FXML
    private void handleOk() {

        CategoriaProdutoDao dao = new CategoriaProdutoDao();

        if (isInputValid()) {
            model.setNome(nome.getText());
            model.setDescricao(descricao.getText());
            model.setSituacao(situacao.getSelectionModel().getSelectedItem());

            CategoriaProduto categoriaProduto = new CategoriaProduto(model.getId(), model.getNome(), model.getDescricao(), model.getSituacao());

            categoriaProduto = dao.merge(categoriaProduto);

            if (Objects.nonNull(categoriaProduto)) {
                MessageUtils.sucess("Sucesso ao salvar",
                        "Categoria salva com sucesso.",
                        String.format("Código da Categoria: %d", categoriaProduto.getId()));
            } else {
                MessageUtils.error("Erro ao salvar",
                        "Ocorreu um erro ao salvar Categoria.",
                        "Contacte o administrador.");
            }

            this.model = new CategoriaProdutoDataModel();
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nome.getText() == null || nome.getText().length() == 0) {
            errorMessage += "Nome Social inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campos Inválidos");
            alert.setHeaderText("Por favor, corrija os campos inválidos");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
