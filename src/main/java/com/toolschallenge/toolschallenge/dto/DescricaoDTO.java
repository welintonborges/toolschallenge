package com.toolschallenge.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Bloco {@code descricao} do contrato de transação. Os campos {@code nsu},
 * {@code codigoAutorizacao} e {@code status} são preenchidos pelo servidor e só
 * aparecem nas respostas (ignorados no request).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DescricaoDTO {

    @NotBlank(message = "valor é obrigatório")
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "valor deve ser um decimal positivo (ex.: 500.50)")
    @Schema(example = "500.50")
    private String valor;

    @NotBlank(message = "dataHora é obrigatória")
    @Schema(example = "01/05/2021 18:30:00", description = "Formato dd/MM/yyyy HH:mm:ss")
    private String dataHora;

    @NotBlank(message = "estabelecimento é obrigatório")
    @Schema(example = "PetShop Mundo cão")
    private String estabelecimento;

    @Schema(example = "1234567890", accessMode = Schema.AccessMode.READ_ONLY)
    private String nsu;

    @Schema(example = "147258369", accessMode = Schema.AccessMode.READ_ONLY)
    private String codigoAutorizacao;

    @Schema(example = "AUTORIZADO", accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPagamento status;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
