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
public class CategoriaControllerTesteIntegracao {

    @Autowired
    private MockMvc mockMvc;

    private Integer idExistente;
    private Integer idInexistente;
    private Integer idCategoriaAtiva;
    private Integer idCategoriaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idExistente = 3;
        idInexistente = 100;
        idCategoriaAtiva = 2;
        idCategoriaInativa = 1;
    }

    @Test
    void findAllActiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/ativas")
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllInactiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/inativas")
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdDeveriaRetornarRecursoComStatusOkQuandoCategoriaExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{id}", idExistente)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdDeveriaRetornarRecursoComStatusNotFoundQuandoCategoriaNaoExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{id}", idInexistente)
                    .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdActiveDeveriaRetornarRecursoComStatusOkQuandoCategoriaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{categoriaId}/ativa", idCategoriaAtiva)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdActiveDeveriaRetornarRecursoComStatusNotFoundQuandoCategoriaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{categoriaId}/ativa", idCategoriaInativa)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdInactiveDeveriaRetornarRecursoComStatusOkQuandoCategoriaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{categoriaId}/inativa", idCategoriaInativa)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdInactiveDeveriaRetornarRecursoComStatusNotFoundQuandoCategoriaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/categorias/{categoriaId}/inativa", idCategoriaAtiva)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void activateDeveriaRetornarStatusOkQuandoCategoriaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/categorias/ativar")
                        .param("categoriaId", idCategoriaInativa.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void activateDeveriaRetornarStatusNotFoundQuandoCategoriaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/categorias/ativar")
                        .param("categoriaId", idCategoriaAtiva.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void inactivateDeveriaRetornarStatusOkQuandoCategoriaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/categorias/inativar")
                        .param("categoriaId", idCategoriaAtiva.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void inactivateDeveriaRetornarStatusNotFoundQuandoCategoriaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/categorias/inativar")
                        .param("categoriaId", idCategoriaInativa.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

}
