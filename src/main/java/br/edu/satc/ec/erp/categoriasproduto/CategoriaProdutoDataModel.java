package br.edu.satc.ec.erp.categoriasproduto;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.model.Situacao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class CategoriaProdutoDataModel {

    private ObjectProperty<Long> id;
    private StringProperty nome;
    private StringProperty descricao;
    private ObjectProperty<Situacao> situacao;

    public CategoriaProdutoDataModel() {
        this.id = new SimpleObjectProperty<>();
        this.situacao = new SimpleObjectProperty<>();
        this.nome = new SimpleStringProperty();
        this.descricao = new SimpleStringProperty();
    }

    public CategoriaProdutoDataModel(CategoriaProduto categoriaProduto) {
        this.id = new SimpleObjectProperty<>(categoriaProduto.getId());
        this.situacao = new SimpleObjectProperty<>(categoriaProduto.getSituacao());
        this.nome = new SimpleStringProperty(categoriaProduto.getNome());
        this.descricao = new SimpleStringProperty(categoriaProduto.getDescricao());
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

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
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
