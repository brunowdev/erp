package br.edu.satc.ec.erp.produtos.entity;

import br.edu.satc.ec.erp.categoriasproduto.entity.CategoriaProduto;
import br.edu.satc.ec.erp.model.Situacao;

import java.math.BigDecimal;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private CategoriaProduto categoria;
    private Situacao situacao;

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, BigDecimal quantidade, BigDecimal valorUnitario, CategoriaProduto categoria, Situacao situacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}
