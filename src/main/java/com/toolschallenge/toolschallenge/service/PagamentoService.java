package com.toolschallenge.toolschallenge.service;

import com.toolschallenge.toolschallenge.dto.PagamentoRequest;
import com.toolschallenge.toolschallenge.dto.PagamentoResponse;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.exception.BusinessException;
import com.toolschallenge.toolschallenge.exception.ResourceNotFoundException;
import com.toolschallenge.toolschallenge.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Regras de negócio de criação e consulta de pagamentos.
 */
@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final TransacaoMapper mapper;

    public PagamentoService(PagamentoRepository repository, TransacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public PagamentoResponse realizarPagamento(PagamentoRequest request) {
        Pagamento pagamento = mapper.toEntity(request.getTransacao());

        if (pagamento.getTransacaoId() == null || pagamento.getTransacaoId().isBlank()) {
            pagamento.setTransacaoId(gerarTransacaoIdUnico());
        } else if (repository.existsByTransacaoId(pagamento.getTransacaoId())) {
            throw new BusinessException("Já existe transação com o id " + pagamento.getTransacaoId());
        }
        pagamento.setStatus(StatusPagamento.AUTORIZADO);
        pagamento.setNsu(gerarNumerico(10));
        pagamento.setCodigoAutorizacao(gerarNumerico(9));

        Pagamento salvo = repository.save(pagamento);
        return new PagamentoResponse(mapper.toDto(salvo));
    }

    @Transactional(readOnly = true)
    public PagamentoResponse consultarPorId(String id) {
        return new PagamentoResponse(mapper.toDto(buscar(id)));
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .map(PagamentoResponse::new)
                .toList();
    }

    /**
     * Localiza o pagamento ou lança {@link ResourceNotFoundException}.
     * Reutilizado pelo {@link EstornoService}.
     */
    public Pagamento buscar(String transacaoId) {
        return repository.findByTransacaoId(transacaoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pagamento não encontrado para o id " + transacaoId));
    }

    private String gerarTransacaoIdUnico() {
        String candidato;
        do {
            candidato = gerarNumerico(15);
        } while (repository.existsByTransacaoId(candidato));
        return candidato;
    }

    private String gerarNumerico(int digitos) {
        StringBuilder sb = new StringBuilder(digitos);
        for (int i = 0; i < digitos; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }
}
