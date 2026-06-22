package com.toolschallenge.toolschallenge.repository;

import com.toolschallenge.toolschallenge.entity.FormaPagamento;
import com.toolschallenge.toolschallenge.entity.Pagamento;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.entity.TipoPagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository repository;

    @Test
    void persisteEbuscaPorTransacaoId() {
        repository.save(novoPagamento("100023568900001"));

        Optional<Pagamento> encontrado = repository.findByTransacaoId("100023568900001");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getStatus()).isEqualTo(StatusPagamento.AUTORIZADO);
        assertThat(encontrado.get().getFormaPagamento().getTipo()).isEqualTo(TipoPagamento.AVISTA);
        assertThat(repository.existsByTransacaoId("100023568900001")).isTrue();
        assertThat(repository.existsByTransacaoId("inexistente")).isFalse();
    }

    private Pagamento novoPagamento(String transacaoId) {
        Pagamento pagamento = new Pagamento();
        pagamento.setTransacaoId(transacaoId);
        pagamento.setCartao("4444********1234");
        pagamento.setValor(new BigDecimal("500.50"));
        pagamento.setDataHora(LocalDateTime.of(2021, 5, 1, 18, 30, 0));
        pagamento.setEstabelecimento("PetShop Mundo cão");
        pagamento.setFormaPagamento(new FormaPagamento(TipoPagamento.AVISTA, 1));
        pagamento.setStatus(StatusPagamento.AUTORIZADO);
        pagamento.setNsu("1234567890");
        pagamento.setCodigoAutorizacao("147258369");
        return pagamento;
    }
}
