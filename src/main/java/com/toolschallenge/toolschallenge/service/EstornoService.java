package com.toolschallenge.toolschallenge.service;

import com.toolschallenge.toolschallenge.dto.EstornoResponse;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.exception.BusinessException;
import com.toolschallenge.toolschallenge.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Regra de estorno: transações AUTORIZADAS passam para CANCELADO.
 */
@Service
public class EstornoService {

    private final PagamentoRepository repository;
    private final PagamentoService pagamentoService;
    private final TransacaoMapper mapper;

    public EstornoService(PagamentoRepository repository,
                          PagamentoService pagamentoService,
                          TransacaoMapper mapper) {
        this.repository = repository;
        this.pagamentoService = pagamentoService;
        this.mapper = mapper;
    }

    @Transactional
    public EstornoResponse estornar(String id) {
        Pagamento pagamento = pagamentoService.buscar(id);

        switch (pagamento.getStatus()) {
            case AUTORIZADO -> {
                pagamento.setStatus(StatusPagamento.CANCELADO);
                repository.save(pagamento);
            }
            case CANCELADO -> {
                // Idempotente: já estornado, devolve o estado atual sem erro.
            }
            case NEGADO -> throw new BusinessException(
                    "Não é possível estornar uma transação NEGADA (id " + id + ")");
        }

        return new EstornoResponse(mapper.toDto(pagamento));
    }
}
