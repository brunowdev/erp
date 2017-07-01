package br.edu.satc.ec.erp.contas;

import br.edu.satc.ec.erp.contas.entity.Conta;
import br.edu.satc.ec.erp.contas.persistence.ContaDao;
import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.utils.components.MaskField;
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
public class ContaAddEditController {

    @FXML
    private TextField razaoSocialTf;

    @FXML
    private TextField nomeFantasiaTf;

    @FXML
    private MaskField cnpjTf;

    @FXML
    private ComboBox<Situacao> situacaoCb;

    private Stage dialogStage;
    private ContaDataModel model;
    private boolean editando = false;

    @FXML
    private void initialize() {
        situacaoCb.setItems(FXCollections.observableArrayList(Arrays.asList(Situacao.values())));
        situacaoCb.getSelectionModel().selectFirst();

        FormConstraints.maxField(razaoSocialTf, 125);
        FormConstraints.maxField(nomeFantasiaTf, 50);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        if (nonNull(model) && nonNull(model.getId())) {
            editando = true;
            dialogStage.setTitle("Editando conta");
        } else {
            model = new ContaDataModel();
            editando = false;
            dialogStage.setTitle("Cadastrando conta");
        }

    }

    public void setConta(ContaDataModel model) {
        this.model = model;
        razaoSocialTf.setText(model.getRazaoSocial());
        nomeFantasiaTf.setText(model.getNomeFantasia());
        cnpjTf.setText(model.getCnpj());
        cnpjTf.setPlainText(model.getCnpj());
        situacaoCb.getSelectionModel().select(model.getSituacao());
    }

    public String getCnpj() {
       return cnpjTf.getText()
               .replace("_", "")
               .replace(".", "")
               .replace("-", "")
               .replace("/", "");
    }

    @FXML
    private void handleOk() {

        ContaDao dao = new ContaDao();

        if (isInputValid()) {
            model.setRazaoSocial(razaoSocialTf.getText());
            model.setNomeFantasia(nomeFantasiaTf.getText());
            model.setCnpj(getCnpj());
            model.setSituacao(situacaoCb.getSelectionModel().getSelectedItem());

            Conta conta = new Conta(model.getId(), model.getCnpj(), model.getRazaoSocial(), model.getNomeFantasia(), model.getSituacao());

            conta = dao.merge(conta);

            if (Objects.nonNull(conta)) {
                MessageUtils.sucess("Sucesso ao salvar",
                        "Conta salva com sucesso.",
                        String.format("Código da conta: %d", conta.getId()));
            } else {
                MessageUtils.error("Erro ao salvar",
                        "Ocorreu um erro ao salvar conta.",
                        "Contacte o administrador.");
            }

            this.model = new ContaDataModel();
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (razaoSocialTf.getText() == null || razaoSocialTf.getText().length() == 0) {
            errorMessage += "Razão Social inválida!\n";
        }
        if (nomeFantasiaTf.getText() == null || nomeFantasiaTf.getText().length() == 0) {
            errorMessage += "Nome Fantasia inválido!\n";
        }
        if (cnpjTf.getText() == null || cnpjTf.getText().length() == 0 || getCnpj().length() < 14) {
            errorMessage += "CNPJ inválido!\n";
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
