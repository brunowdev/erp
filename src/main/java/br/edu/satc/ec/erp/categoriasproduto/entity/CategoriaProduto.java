package br.edu.satc.ec.erp.categoriasproduto.entity;

import br.edu.satc.ec.erp.model.Situacao;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class CategoriaProduto {

    private Long id;
    private String nome;
    private String descricao;
    private Situacao situacao;

    public CategoriaProduto() {
    }

    public CategoriaProduto(Long id) {
        this.id = id;
    }

    public CategoriaProduto(Long id, String nome, String descricao, Situacao situacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriaProduto that = (CategoriaProduto) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
