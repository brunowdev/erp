package br.edu.satc.ec.erp.produtos;

import br.edu.satc.ec.erp.model.Situacao;
import br.edu.satc.ec.erp.produtos.entity.Produto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

/**
 * Created by BRUNO-PC on 19/06/2017.
 */
public class ProdutoDataModel {

    private ObjectProperty<Long> id;
    private StringProperty nome;
    private StringProperty descricao;
    private ObjectProperty<Situacao> situacao;
    private ObjectProperty<BigDecimal> quantidade;
    private ObjectProperty<BigDecimal> valor;
    private ObjectProperty<Long> categoria;


    public ProdutoDataModel() {
        this.id = new SimpleObjectProperty<>();
        this.situacao = new SimpleObjectProperty<>();
        this.nome = new SimpleStringProperty();
        this.descricao = new SimpleStringProperty();
        this.quantidade = new SimpleObjectProperty<>();
        this.valor = new SimpleObjectProperty<>();
        this.categoria = new SimpleObjectProperty<>();
    }

    public ProdutoDataModel(Produto produto) {
        this.id = new SimpleObjectProperty<>(produto.getId());
        this.situacao = new SimpleObjectProperty<>(produto.getSituacao());
        this.nome = new SimpleStringProperty(produto.getNome());
        this.descricao = new SimpleStringProperty(produto.getDescricao());
        this.quantidade = new SimpleObjectProperty<>(produto.getQuantidade());
        this.valor = new SimpleObjectProperty<>(produto.getValorUnitario());
        this.categoria = new SimpleObjectProperty<>(produto.getCategoria().getId());
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

    public BigDecimal getQuantidade() {
        return quantidade.get();
    }

    public ObjectProperty<BigDecimal> quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade.set(quantidade);
    }

    public BigDecimal getValor() {
        return valor.get();
    }

    public ObjectProperty<BigDecimal> valorProperty() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor.set(valor);
    }

    public Long getCategoria() {
        return categoria.get();
    }

    public ObjectProperty<Long> categoriaProperty() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria.set(categoria);
    }
}
