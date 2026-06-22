package com.toolschallenge.toolschallenge.controller;

import com.toolschallenge.toolschallenge.dto.DescricaoDTO;
import com.toolschallenge.toolschallenge.dto.FormaPagamentoDTO;
import com.toolschallenge.toolschallenge.dto.PagamentoResponse;
import com.toolschallenge.toolschallenge.dto.TransacaoDTO;
import com.toolschallenge.toolschallenge.entity.StatusPagamento;
import com.toolschallenge.toolschallenge.entity.TipoPagamento;
import com.toolschallenge.toolschallenge.exception.ResourceNotFoundException;
import com.toolschallenge.toolschallenge.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagamentoService service;

    private static final String PAYLOAD_VALIDO = """
            {
              "transacao": {
                "cartao": "4444********1234",
                "id": "100023568900001",
                "descricao": { "valor": "500.50", "dataHora": "01/05/2021 18:30:00", "estabelecimento": "PetShop Mundo cão" },
                "formaPagamento": { "tipo": "AVISTA", "parcelas": "1" }
              }
            }
            """;

    @Test
    void realizarPagamentoRetorna201() throws Exception {
        when(service.realizarPagamento(any())).thenReturn(respostaAutorizada());

        mockMvc.perform(post("/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PAYLOAD_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transacao.descricao.status").value("AUTORIZADO"))
                .andExpect(jsonPath("$.transacao.descricao.nsu").value("1234567890"));
    }

    @Test
    void payloadInvalidoRetorna400() throws Exception {
        String invalido = PAYLOAD_VALIDO.replace("\"500.50\"", "\"-10\"");

        mockMvc.perform(post("/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void consultarInexistenteRetorna404() throws Exception {
        when(service.consultarPorId(eq("999")))
                .thenThrow(new ResourceNotFoundException("Pagamento não encontrado para o id 999"));

        mockMvc.perform(get("/api/pagamentos/{id}", "999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    private PagamentoResponse respostaAutorizada() {
        DescricaoDTO descricao = new DescricaoDTO();
        descricao.setValor("500.50");
        descricao.setDataHora("01/05/2021 18:30:00");
        descricao.setEstabelecimento("PetShop Mundo cão");
        descricao.setNsu("1234567890");
        descricao.setCodigoAutorizacao("147258369");
        descricao.setStatus(StatusPagamento.AUTORIZADO);

        FormaPagamentoDTO forma = new FormaPagamentoDTO();
        forma.setTipo(TipoPagamento.AVISTA);
        forma.setParcelas("1");

        TransacaoDTO transacao = new TransacaoDTO();
        transacao.setCartao("4444********1234");
        transacao.setId("100023568900001");
        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(forma);

        return new PagamentoResponse(transacao);
    }
}
