package com.toolschallenge.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Bloco {@code pagamento} do contrato de transação. No request é ignorado
 * (o servidor preenche status, nsu e código de autorização); no response é
 * devolvido preenchido.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoInfoDTO {

    @Schema(example = "AUTORIZADO", accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPagamento status;

    @Schema(example = "1234567890", accessMode = Schema.AccessMode.READ_ONLY)
    private String nsu;

    @Schema(example = "147258369", accessMode = Schema.AccessMode.READ_ONLY)
    private String codigoAutorizacao;

    public PagamentoInfoDTO() {
    }

    public PagamentoInfoDTO(StatusPagamento status, String nsu, String codigoAutorizacao) {
        this.status = status;
        this.nsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
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
}
