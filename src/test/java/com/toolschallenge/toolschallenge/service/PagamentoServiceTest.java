package com.toolschallenge.toolschallenge.service;

import com.toolschallenge.toolschallenge.dto.DescricaoDTO;
import com.toolschallenge.toolschallenge.dto.FormaPagamentoDTO;
import com.toolschallenge.toolschallenge.dto.PagamentoRequest;
import com.toolschallenge.toolschallenge.dto.PagamentoResponse;
import com.toolschallenge.toolschallenge.dto.TransacaoDTO;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.entity.TipoPagamento;
import com.toolschallenge.toolschallenge.exception.ResourceNotFoundException;
import com.toolschallenge.toolschallenge.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @Spy
    private TransacaoMapper mapper;

    @InjectMocks
    private PagamentoService service;

    @Test
    void realizarPagamentoAutorizaEgeraNsuECodigo() {
        when(repository.existsByTransacaoId(anyString())).thenReturn(false);
        when(repository.save(any(Pagamento.class))).thenAnswer(returnsFirstArg());

        PagamentoResponse response = service.realizarPagamento(requestValido("100023568900001"));

        var descricao = response.getTransacao().getDescricao();
        assertThat(descricao.getStatus()).isEqualTo(StatusPagamento.AUTORIZADO);
        assertThat(descricao.getNsu()).hasSize(10);
        assertThat(descricao.getCodigoAutorizacao()).hasSize(9);
        assertThat(response.getTransacao().getId()).isEqualTo("100023568900001");
    }

    @Test
    void realizarPagamentoGeraIdQuandoAusente() {
        when(repository.existsByTransacaoId(anyString())).thenReturn(false);
        when(repository.save(any(Pagamento.class))).thenAnswer(returnsFirstArg());

        PagamentoResponse response = service.realizarPagamento(requestValido(null));

        assertThat(response.getTransacao().getId()).isNotBlank();
    }

    @Test
    void buscarLancaQuandoNaoEncontrado() {
        when(repository.findByTransacaoId("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscar("999"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    private PagamentoRequest requestValido(String id) {
        DescricaoDTO descricao = new DescricaoDTO();
        descricao.setValor("500.50");
        descricao.setDataHora("01/05/2021 18:30:00");
        descricao.setEstabelecimento("PetShop Mundo cão");

        FormaPagamentoDTO forma = new FormaPagamentoDTO();
        forma.setTipo(TipoPagamento.AVISTA);
        forma.setParcelas("1");

        TransacaoDTO transacao = new TransacaoDTO();
        transacao.setCartao("4444********1234");
        transacao.setId(id);
        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(forma);

        PagamentoRequest request = new PagamentoRequest();
        request.setTransacao(transacao);
        return request;
    }
}
