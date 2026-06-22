package com.toolschallenge.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Corpo da transação compartilhado entre request e response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransacaoDTO {

    @NotBlank(message = "cartao é obrigatório")
    @Schema(example = "4444********1234")
    private String cartao;

    @Schema(example = "100023568900001", description = "Identificador da transação; gerado pelo servidor quando ausente")
    private String id;

    @NotNull(message = "descricao é obrigatória")
    @Valid
    private DescricaoDTO descricao;

    @NotNull(message = "formaPagamento é obrigatória")
    @Valid
    private FormaPagamentoDTO formaPagamento;

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DescricaoDTO getDescricao() {
        return descricao;
    }

    public void setDescricao(DescricaoDTO descricao) {
        this.descricao = descricao;
    }

    public FormaPagamentoDTO getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
