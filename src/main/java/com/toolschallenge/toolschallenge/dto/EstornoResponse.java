package com.toolschallenge.toolschallenge.dto;

/**
 * Envelope de response do estorno, com a transação em status CANCELADO.
 */
public class EstornoResponse {

    private TransacaoDTO transacao;

    public EstornoResponse() {
    }

    public EstornoResponse(TransacaoDTO transacao) {
        this.transacao = transacao;
    }

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }
}
