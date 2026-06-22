package com.toolschallenge.toolschallenge.dto;

/**
 * Envelope de response com o objeto raiz {@code transacao} já processada.
 */
public class PagamentoResponse {

    private TransacaoDTO transacao;

    public PagamentoResponse() {
    }

    public PagamentoResponse(TransacaoDTO transacao) {
        this.transacao = transacao;
    }

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }
}
