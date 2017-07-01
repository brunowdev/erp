package br.edu.satc.ec.erp.contas;

import br.edu.satc.ec.erp.contas.entity.Conta;
import br.edu.satc.ec.erp.model.Situacao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class ContaDataModel {

    private ObjectProperty<Long> id;
    private StringProperty razaoSocial;
    private StringProperty nomeFantasia;
    private StringProperty cnpj;
    private ObjectProperty<Situacao> situacao;

    public ContaDataModel() {
        this.id = new SimpleObjectProperty<>();
        this.situacao = new SimpleObjectProperty<>();
        this.razaoSocial = new SimpleStringProperty();
        this.nomeFantasia = new SimpleStringProperty();
        this.cnpj = new SimpleStringProperty();
    }

    public ContaDataModel(Conta conta) {
        this.id = new SimpleObjectProperty<>(conta.getId());
        this.situacao = new SimpleObjectProperty<>(conta.getSituacao());
        this.razaoSocial = new SimpleStringProperty(conta.getRazaoSocial());
        this.nomeFantasia = new SimpleStringProperty(conta.getNomeFantasia());
        this.cnpj = new SimpleStringProperty(conta.getCnpj());
    }

    public Long getId() {
        return id.get();
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getRazaoSocial() {
        return razaoSocial.get();
    }

    public StringProperty razaoSocialProperty() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial.set(razaoSocial);
    }

    public String getNomeFantasia() {
        return nomeFantasia.get();
    }

    public StringProperty nomeFantasiaProperty() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia.set(nomeFantasia);
    }

    public String getCnpj() {
        return cnpj.get();
    }

    public StringProperty cnpjProperty() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj.set(cnpj);
    }

    public Situacao getSituacao() {
        return situacao.get();
    }

    public ObjectProperty<Situacao> situacaoProperty() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao.set(situacao);
    }
}
