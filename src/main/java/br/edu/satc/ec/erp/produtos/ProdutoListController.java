package br.edu.satc.ec.erp.produtos;

import br.edu.satc.ec.erp.produtos.entity.Produto;
import br.edu.satc.ec.erp.produtos.persistence.ProdutoDao;
import br.edu.satc.ec.erp.utils.FormConstraints;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class ProdutoListController {

    @FXML
    private TableView<Produto> produtoTable;

    @FXML
    private TableColumn<Produto, String> nome;

    @FXML
    private TableColumn<Produto, String> categoria;

    @FXML
    private TableColumn<Produto, String> quantidade;

    @FXML
    private TableColumn<Produto, String> valor;

    @FXML
    private TableColumn<Produto, String> situacao;

    @FXML
    private Label nomeLb;

    @FXML
    private Label descricaoLb;

    @FXML
    private Label quantidadeLb;

    @FXML
    private Label valorLb;

    @FXML
    private Label situacaoLb;

    @FXML
    private Label categoriaLb;

    private Stage dialogStage;

    private ProdutoDao dao;

    @FXML
    private void initialize() {
        this.dao = new ProdutoDao();
        refresh();
    }

    public void refresh() {


        produtoTable.setItems(FXCollections.observableArrayList(dao.getAll()));

        valor.setCellValueFactory(
                cellData -> {
                    TextField formatValor = new MaskField();
                    FormConstraints.monetaryField(formatValor);
                    formatValor.setText(cellData.getValue().getValorUnitario().toString());
                    return new SimpleStringProperty(formatValor.getText());
                });

        quantidade.setCellValueFactory(
                cellData -> {
                    TextField formatValor = new MaskField();
                    FormConstraints.monetaryField(formatValor);
                    formatValor.setText(cellData.getValue().getQuantidade().toString());
                    return new SimpleStringProperty(formatValor.getText());
                });

        nome.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        categoria.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        situacao.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSituacao().toString()));

        produtoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarProduto(newValue));

    }

    private String formatMonetaryValue(String value) {
        TextField formatValor = new MaskField();
        FormConstraints.monetaryField(formatValor);
        formatValor.setText(value);
        return formatValor.getText();
    }

    private void selecionarProduto(Produto produto) {
        if (Objects.nonNull(produto)) {
            nomeLb.setText(produto.getNome());
            descricaoLb.setText(produto.getDescricao());
            categoriaLb.setText(produto.getCategoria().getNome());
            situacaoLb.setText(produto.getSituacao().toString());
            quantidadeLb.setText(formatMonetaryValue(produto.getQuantidade().toString()));
            valorLb.setText(formatMonetaryValue(produto.getValorUnitario().toString()));
        } else {
            nomeLb.setText("");
            descricaoLb.setText("");
            categoriaLb.setText("");
            situacaoLb.setText("");
            quantidadeLb.setText("");
            valorLb.setText("");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setTitle("Gerenciando Produtos");
    }

    @FXML
    private void handleNovoProduto() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProdutoDataModel.class.getResource("/fxml/produtos/ProdutoAddEdit.fxml"));
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

        ProdutoAddEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
        refresh();
    }

    @FXML
    private void handleEditarProduto() {

        int selectedIndex = produtoTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            Produto produto = produtoTable.getItems().get(selectedIndex);
            ProdutoDataModel model = new ProdutoDataModel(produto);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProdutoDataModel.class.getResource("/fxml/produtos/ProdutoAddEdit.fxml"));
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

            ProdutoAddEditController controller = loader.getController();
            controller.setProduto(model);
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
        int selectedIndex = produtoTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            Produto produto = produtoTable.getItems().get(selectedIndex);

            boolean continuar = MessageUtils.confirmationDialog("Sua atenção é necessária",
                    String.format("Deseja realmente excluir o produto %s?", produto.getNome()),
                    "Esta alteração não poderá ser desfeita.");

            if (continuar) {
                if (dao.delete(produto.getId())) {
                    produtoTable.getItems().remove(selectedIndex);
                    MessageUtils.sucess("Sucesso ao excluir produto", "Produto excluída com sucesso.", String.format("Produto removido: %d", produto.getId()));
                }
            }

        } else {
            MessageUtils.error("Nenhum registro selecionado",
                    "Nenhum registro selecionado para exclusão.",
                    "Selecione um registro antes de executar esta ação.");

        }
    }


}
