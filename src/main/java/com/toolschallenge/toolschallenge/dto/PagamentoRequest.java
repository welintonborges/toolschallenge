package com.toolschallenge.toolschallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Envelope de request com o objeto raiz {@code transacao}.
 */
public class PagamentoRequest {

    @NotNull(message = "transacao é obrigatória")
    @Valid
    private TransacaoDTO transacao;

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }
}
