package com.stooom.ecommerce.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MarcaControllerTesteIntegracao {

    @Autowired
    private MockMvc mockMvc;

    private Integer idExistente;
    private Integer idInexistente;
    private Integer idMarcaAtiva;
    private Integer idMarcaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idExistente = 1;
        idInexistente = 100;
        idMarcaAtiva = 1;
        idMarcaInativa = 3;
    }

    @Test
    void findAllActiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/ativas")
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllInactiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/inativas")
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdDeveriaRetornarRecursoComStatusOkQuandoMarcaExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}", idExistente)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdDeveriaRetornarStatusNotFoundQuandoMarcaNaoExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}", idInexistente)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdActiveDeveriaRetornarStatusOkQuandoMarcaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}/ativa", idMarcaAtiva)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdActiveDeveriaRetornarStatusNotFoundQuandoMarcaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}/ativa", idMarcaInativa)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdInactiveDeveriaRetornarStatusOkQuandoMarcaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}/inativa", idMarcaInativa)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdInactiveDeveriaRetornarStatusNotFoundQuandoMarcaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/marcas/{id}/inativa", idMarcaAtiva)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void activateDeveriaRetornarStatusOkQuandoMarcaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/marcas/ativar")
                    .param("marcaId", idMarcaInativa.toString())
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void activateDeveriaRetornarStatusNotFoundQuandoMarcaEstiverAtiva() throws Exception {
        ResultActions result =
            mockMvc.perform(put("/v1/marcas/ativar")
                .param("marcaId", idMarcaAtiva.toString())
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void inactivateDeveriaRetornarStatusOkQuandoMarcaEstiverAtiva() throws Exception {
        ResultActions result =
            mockMvc.perform(put("/v1/marcas/inativar")
                .param("marcaId", idMarcaAtiva.toString())
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void inactivateDeveriaRetornarStatusNotFoundQuandoMarcaEstiverInativa() throws Exception {
        ResultActions result =
            mockMvc.perform(put("/v1/marcas/inativar")
                .param("marcaId", idMarcaInativa.toString())
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

}
