package com.toolschallenge.toolschallenge.repository;

import com.toolschallenge.toolschallenge.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByTransacaoId(String transacaoId);

    boolean existsByTransacaoId(String transacaoId);
}
