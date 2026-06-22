package com.toolschallenge.toolschallenge.service;

import com.toolschallenge.toolschallenge.dto.EstornoResponse;
import com.toolschallenge.toolschallenge.entity.FormaPagamento;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.entity.TipoPagamento;
import com.toolschallenge.toolschallenge.exception.BusinessException;
import com.toolschallenge.toolschallenge.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstornoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @Mock
    private PagamentoService pagamentoService;

    @Spy
    private TransacaoMapper mapper;

    @InjectMocks
    private EstornoService service;

    @Test
    void estornaPagamentoAutorizado() {
        Pagamento pagamento = pagamento(StatusPagamento.AUTORIZADO);
        when(pagamentoService.buscar("1")).thenReturn(pagamento);

        EstornoResponse response = service.estornar("1");

        assertThat(pagamento.getStatus()).isEqualTo(StatusPagamento.CANCELADO);
        assertThat(response.getTransacao().getDescricao().getStatus()).isEqualTo(StatusPagamento.CANCELADO);
        verify(repository).save(pagamento);
    }

    @Test
    void estornoEhIdempotenteQuandoJaCancelado() {
        Pagamento pagamento = pagamento(StatusPagamento.CANCELADO);
        when(pagamentoService.buscar("1")).thenReturn(pagamento);

        EstornoResponse response = service.estornar("1");

        assertThat(response.getTransacao().getDescricao().getStatus()).isEqualTo(StatusPagamento.CANCELADO);
        verify(repository, never()).save(pagamento);
    }

    @Test
    void naoEstornaTransacaoNegada() {
        when(pagamentoService.buscar("1")).thenReturn(pagamento(StatusPagamento.NEGADO));

        assertThatThrownBy(() -> service.estornar("1"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("NEGADA");
    }

    private Pagamento pagamento(StatusPagamento status) {
        Pagamento pagamento = new Pagamento();
        pagamento.setTransacaoId("100023568900001");
        pagamento.setCartao("4444********1234");
        pagamento.setValor(new BigDecimal("500.50"));
        pagamento.setDataHora(LocalDateTime.of(2021, 5, 1, 18, 30, 0));
        pagamento.setEstabelecimento("PetShop Mundo cão");
        pagamento.setFormaPagamento(new FormaPagamento(TipoPagamento.AVISTA, 1));
        pagamento.setStatus(status);
        pagamento.setNsu("1234567890");
        pagamento.setCodigoAutorizacao("147258369");
        return pagamento;
    }
}
