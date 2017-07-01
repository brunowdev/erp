package br.edu.satc.ec.erp.produtos;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.categoriasproduto.persistence.CategoriaProdutoDao;
import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.produtos.entity.Produto;
import br.edu.satc.ec.erp.produtos.persistence.ProdutoDao;
import br.edu.satc.ec.erp.utils.FormConstraints;
import br.edu.satc.ec.erp.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class ProdutoAddEditController {

    @FXML
    private TextField nome;

    @FXML
    private TextArea descricao;

    @FXML
    private ComboBox<Situacao> situacaoCb;

    @FXML
    private TextField quantidade;

    @FXML
    private TextField valor;

    @FXML
    private ComboBox<CategoriaProduto> categoria;

    private Stage dialogStage;
    private ProdutoDataModel model;
    private boolean editando = false;

    @FXML
    private void initialize() {
        situacaoCb.setItems(FXCollections.observableArrayList(Arrays.asList(Situacao.values())));
        situacaoCb.getSelectionModel().selectFirst();

        CategoriaProdutoDao categoriaDao = new CategoriaProdutoDao();
        categoria.setItems(FXCollections.observableArrayList(categoriaDao.getAll()));
        categoria.getSelectionModel().selectFirst();

        FormConstraints.maxField(nome, 50);
        FormConstraints.maxField(descricao, 5000);
        FormConstraints.monetaryField(valor);
        FormConstraints.monetaryField(quantidade);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        if (nonNull(model) && nonNull(model.getId())) {
            editando = true;
            dialogStage.setTitle("Editando produto");
        } else {
            model = new ProdutoDataModel();
            editando = false;
            dialogStage.setTitle("Cadastrando produto");
        }

    }

    public void setProduto(ProdutoDataModel model) {
        this.model = model;
        this.nome.setText(model.getNome());
        this.descricao.setText(model.getDescricao());
        this.situacaoCb.getSelectionModel().select(model.getSituacao());
        this.quantidade.setText(model.getQuantidade().toString());
        this.valor.setText(model.getValor().toString());
        this.categoria.getSelectionModel().select(new CategoriaProduto(model.getCategoria()));
    }

    private String extractRawMonetaryValue(String value) {
        return value.replace(".", "").replace(",", ".");
    }

    @FXML
    private void handleOk() {

        ProdutoDao dao = new ProdutoDao();

        if (isInputValid()) {
            model.setNome(nome.getText());
            model.setDescricao(descricao.getText());
            model.setQuantidade(new BigDecimal(extractRawMonetaryValue(quantidade.getText())));
            model.setValor(new BigDecimal(extractRawMonetaryValue(valor.getText())));
            model.setSituacao(situacaoCb.getSelectionModel().getSelectedItem());
            model.setCategoria(categoria.getSelectionModel().getSelectedItem().getId());

            Produto produto = new Produto(model.getId(),
                    model.getNome(),
                    model.getDescricao(),
                    model.getQuantidade(),
                    model.getValor(), new CategoriaProduto(model.getCategoria()),
                    model.getSituacao());

            produto = dao.merge(produto);

            if (Objects.nonNull(produto)) {
                MessageUtils.sucess("Sucesso ao salvar",
                        "Produto salvo com sucesso.",
                        String.format("Código do produto: %d", produto.getId()));
            } else {
                MessageUtils.error("Erro ao salvar",
                        "Ocorreu um erro ao salvar produto.",
                        "Contacte o administrador.");
            }

            this.model = new ProdutoDataModel();
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
            errorMessage += "Nome inválido!\n";
        }
        if (quantidade.getText() == null || quantidade.getText().length() == 0) {
            errorMessage += "Quantidade inválida!\n";
        }
        if (valor.getText() == null || valor.getText().length() == 0) {
            errorMessage += "Valor unitário inválido!\n";
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
