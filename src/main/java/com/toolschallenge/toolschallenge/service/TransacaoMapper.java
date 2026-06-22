package com.toolschallenge.toolschallenge.service;

import com.toolschallenge.toolschallenge.dto.DescricaoDTO;
import com.toolschallenge.toolschallenge.dto.FormaPagamentoDTO;
import com.toolschallenge.toolschallenge.dto.TransacaoDTO;
import com.toolschallenge.toolschallenge.entity.FormaPagamento;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Conversão entre o contrato de transação ({@link TransacaoDTO}) e a entidade
 * persistida ({@link Pagamento}).
 */
@Component
public class TransacaoMapper {

    public static final DateTimeFormatter DATA_HORA_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Constrói uma nova entidade a partir do payload recebido. Os campos
     * controlados pelo servidor (status, nsu, código de autorização) ficam a
     * cargo do serviço de pagamento.
     */
    public Pagamento toEntity(TransacaoDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setTransacaoId(dto.getId());
        pagamento.setCartao(dto.getCartao());

        DescricaoDTO descricao = dto.getDescricao();
        pagamento.setValor(parseValor(descricao.getValor()));
        pagamento.setDataHora(parseDataHora(descricao.getDataHora()));
        pagamento.setEstabelecimento(descricao.getEstabelecimento());

        FormaPagamentoDTO forma = dto.getFormaPagamento();
        pagamento.setFormaPagamento(new FormaPagamento(forma.getTipo(), parseParcelas(forma.getParcelas())));

        return pagamento;
    }

    /**
     * Projeta a entidade no contrato de transação devolvido ao cliente.
     */
    public TransacaoDTO toDto(Pagamento pagamento) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setCartao(pagamento.getCartao());
        dto.setId(pagamento.getTransacaoId());

        DescricaoDTO descricao = new DescricaoDTO();
        descricao.setValor(pagamento.getValor().toPlainString());
        descricao.setDataHora(pagamento.getDataHora().format(DATA_HORA_FORMAT));
        descricao.setEstabelecimento(pagamento.getEstabelecimento());
        descricao.setNsu(pagamento.getNsu());
        descricao.setCodigoAutorizacao(pagamento.getCodigoAutorizacao());
        descricao.setStatus(pagamento.getStatus());
        dto.setDescricao(descricao);

        FormaPagamento forma = pagamento.getFormaPagamento();
        FormaPagamentoDTO formaDto = new FormaPagamentoDTO();
        formaDto.setTipo(forma.getTipo());
        formaDto.setParcelas(forma.getParcelas() == null ? null : String.valueOf(forma.getParcelas()));
        dto.setFormaPagamento(formaDto);

        return dto;
    }

    private BigDecimal parseValor(String valor) {
        BigDecimal parsed = new BigDecimal(valor);
        if (parsed.signum() <= 0) {
            throw new IllegalArgumentException("valor deve ser maior que zero");
        }
        return parsed;
    }

    private Integer parseParcelas(String parcelas) {
        if (parcelas == null || parcelas.isBlank()) {
            return 1;
        }
        int parsed = Integer.parseInt(parcelas);
        if (parsed < 1) {
            throw new IllegalArgumentException("parcelas deve ser maior ou igual a 1");
        }
        return parsed;
    }

    private LocalDateTime parseDataHora(String dataHora) {
        try {
            return LocalDateTime.parse(dataHora, DATA_HORA_FORMAT);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("dataHora inválida; use o formato dd/MM/yyyy HH:mm:ss");
        }
    }
}
