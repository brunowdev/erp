package br.edu.satc.ec.erp.categoriasproduto;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.categoriasproduto.persistence.CategoriaProdutoDao;
import br.edu.satc.ec.erp.utils.MessageUtils;
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
public class CategoriaProdutoListController {

    @FXML
    private TableView<CategoriaProduto> categoriaProdutoTable;

    @FXML
    private TableColumn<CategoriaProduto, String> nome;

    @FXML
    private TableColumn<CategoriaProduto, String> descricao;

    @FXML
    private TableColumn<CategoriaProduto, String> situcao;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelDescricao;

    @FXML
    private Label situacaoTf;

    private Stage dialogStage;

    private CategoriaProdutoDao dao;

    @FXML
    private void initialize() {
        this.dao = new CategoriaProdutoDao();
        refresh();
    }

    public void refresh() {

        categoriaProdutoTable.setItems(FXCollections.observableArrayList(dao.getAll()));

        nome.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        situcao.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSituacao().toString()));

        categoriaProdutoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarCategoria(newValue));

    }

    private void selecionarCategoria(CategoriaProduto categoriaProduto) {
        if (Objects.nonNull(categoriaProduto)) {
            labelNome.setText(categoriaProduto.getNome());
            labelDescricao.setText(categoriaProduto.getDescricao());
            situacaoTf.setText(categoriaProduto.getSituacao().toString());
        } else {
            labelNome.setText("");
            labelDescricao.setText("");
            situacaoTf.setText("");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setTitle("Gerenciando Categorias de Produto");
    }

    @FXML
    private void handleNovaCategoria() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CategoriaProdutoDataModel.class.getResource("/fxml/categoriasproduto/CategoriaProdutoAddEdit.fxml"));
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

        CategoriaProdutoAddEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
        refresh();
    }

    @FXML
    private void handleEditarCategoria() {

        int selectedIndex = categoriaProdutoTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            CategoriaProduto categoriaProduto = categoriaProdutoTable.getItems().get(selectedIndex);
            CategoriaProdutoDataModel model = new CategoriaProdutoDataModel(categoriaProduto);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CategoriaProdutoDataModel.class.getResource("/fxml/categoriasproduto/CategoriaProdutoAddEdit.fxml"));
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

            CategoriaProdutoAddEditController controller = loader.getController();
            controller.setCategoria(model);
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
        int selectedIndex = categoriaProdutoTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {

            CategoriaProduto categoriaProduto = categoriaProdutoTable.getItems().get(selectedIndex);

            boolean continuar = MessageUtils.confirmationDialog("Sua atenção é necessária",
                    String.format("Deseja realmente excluir a categoria %s?", categoriaProduto.getNome()),
                    "Esta alteração não poderá ser desfeita.");

            if (continuar) {
                if (dao.delete(categoriaProduto.getId())) {
                    categoriaProdutoTable.getItems().remove(selectedIndex);
                    MessageUtils.sucess("Sucesso ao excluir categoria", "Categoria excluída com sucesso.", String.format("Categoria removida: %d", categoriaProduto.getId()));
                }
            }

        } else {
            MessageUtils.error("Nenhum registro selecionado",
                    "Nenhum registro selecionado para exclusão.",
                    "Selecione um registro antes de executar esta ação.");

        }
    }


}
