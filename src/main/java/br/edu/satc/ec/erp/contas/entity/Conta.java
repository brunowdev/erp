package br.edu.satc.ec.erp.contas.entity;

import br.edu.satc.ec.erp.model.Situacao;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class Conta {

    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private Situacao situacao;

    public Conta() {
    }

    public Conta(String cnpj, String razaoSocial, String nomeFantasia, Situacao situacao) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.situacao = situacao;
    }

    public Conta(Long id, String cnpj, String razaoSocial, String nomeFantasia, Situacao situacao) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}
