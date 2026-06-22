package com.toolschallenge.toolschallenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Value object embutido na transação descrevendo como o pagamento foi feito.
 */
@Embeddable
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento_tipo")
    private TipoPagamento tipo;

    @Column(name = "forma_pagamento_parcelas")
    private Integer parcelas;

    public FormaPagamento() {
    }

    public FormaPagamento(TipoPagamento tipo, Integer parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
}
