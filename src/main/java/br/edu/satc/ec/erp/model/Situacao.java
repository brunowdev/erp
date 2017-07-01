package br.edu.satc.ec.erp.model;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public enum Situacao {

    ATIVO("Ativo", "A"), INATIVO("Inativo", "I");

    private final String description;
    private final String value;

    Situacao(String description, String value) {
        this.value = value;
        this.description = description;
    }

    public static Situacao fromValue(String value) {
        switch (value) {
            case "A":
                return Situacao.ATIVO;
            case "I":
                return Situacao.INATIVO;
        }

        return null;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return description;
    }
}
