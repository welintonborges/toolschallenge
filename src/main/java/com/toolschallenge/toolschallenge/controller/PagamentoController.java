package com.toolschallenge.toolschallenge.controller;

import com.toolschallenge.toolschallenge.dto.PagamentoRequest;
import com.toolschallenge.toolschallenge.dto.PagamentoResponse;
import com.toolschallenge.toolschallenge.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "Processamento e consulta de pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Realiza um pagamento", description = "Recebe a transação e devolve com status AUTORIZADO, nsu e código de autorização")
    public ResponseEntity<PagamentoResponse> realizar(@Valid @RequestBody PagamentoRequest request,
                                                      UriComponentsBuilder uriBuilder) {
        PagamentoResponse response = service.realizarPagamento(request);
        URI location = uriBuilder.path("/api/pagamentos/{id}")
                .buildAndExpand(response.getTransacao().getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta um pagamento por id")
    public ResponseEntity<PagamentoResponse> consultar(@PathVariable String id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os pagamentos")
    public ResponseEntity<List<PagamentoResponse>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
