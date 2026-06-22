package com.toolschallenge.toolschallenge.controller;

import com.toolschallenge.toolschallenge.dto.EstornoResponse;
import com.toolschallenge.toolschallenge.service.EstornoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos/{id}/estorno")
@Tag(name = "Estornos", description = "Cancelamento de pagamentos autorizados")
public class EstornoController {

    private final EstornoService service;

    public EstornoController(EstornoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Estorna um pagamento", description = "Transações AUTORIZADAS passam para CANCELADO; operação idempotente")
    public ResponseEntity<EstornoResponse> estornar(@PathVariable String id) {
        return ResponseEntity.ok(service.estornar(id));
    }
}
