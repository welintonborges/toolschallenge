package com.toolschallenge.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toolschallenge.toolschallenge.entity.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Bloco {@code formaPagamento} do contrato de transação.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormaPagamentoDTO {

    @NotNull(message = "tipo é obrigatório (AVISTA, PARCELADO_LOJA ou PARCELADO_EMISSOR)")
    @Schema(example = "AVISTA")
    private TipoPagamento tipo;

    @Pattern(regexp = "\\d+", message = "parcelas deve ser um número inteiro")
    @Schema(example = "1")
    private String parcelas;

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public String getParcelas() {
        return parcelas;
    }

    public void setParcelas(String parcelas) {
        this.parcelas = parcelas;
    }
}
