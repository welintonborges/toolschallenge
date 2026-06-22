package com.toolschallenge.toolschallenge.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Forma de pagamento aceita pela transação. No contrato JSON os valores usam
 * espaço ("PARCELADO LOJA"); internamente usamos o nome do enum.
 */
public enum TipoPagamento {

    AVISTA("AVISTA"),
    PARCELADO_LOJA("PARCELADO LOJA"),
    PARCELADO_EMISSOR("PARCELADO EMISSOR");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoPagamento from(String valor) {
        if (valor == null) {
            return null;
        }
        for (TipoPagamento tipo : values()) {
            if (tipo.descricao.equalsIgnoreCase(valor) || tipo.name().equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException(
                "formaPagamento.tipo inválido: " + valor + " (use AVISTA, PARCELADO LOJA ou PARCELADO EMISSOR)");
    }
}
