package com.stooom.ecommerce.controller;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
import com.stooom.ecommerce.service.MarcaService;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarcaController.class)
public class MarcaControllerTesteUnitario {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarcaService marcaService;

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

        when(marcaService.buscarTodasAtivas()).thenReturn(new ArrayList<>());
        when(marcaService.buscarTodasInativas()).thenReturn(new ArrayList<>());

        when(marcaService.buscarPorId(eq(idExistente))).thenReturn(new MarcaDTO());
        when(marcaService.buscarPorId(eq(idInexistente))).thenThrow(new RecursoNaoEncontradoException());

        when(marcaService.buscarPorIdMarcaAtiva(eq(idMarcaAtiva))).thenReturn(new MarcaDTO());
        when(marcaService.buscarPorIdMarcaAtiva(eq(idMarcaInativa))).thenThrow(new RecursoNaoEncontradoException());

        when(marcaService.buscarPorIdMarcaInativa(eq(idMarcaInativa))).thenReturn(new MarcaDTO());
        when(marcaService.buscarPorIdMarcaInativa(eq(idMarcaAtiva))).thenThrow(new RecursoNaoEncontradoException());

        when(marcaService.ativar(eq(idMarcaInativa))).thenReturn(new MarcaDTO());
        when(marcaService.ativar(eq(idMarcaAtiva))).thenThrow(new RecursoNaoEncontradoException());
        when(marcaService.inativar(eq(idMarcaAtiva))).thenReturn(new MarcaDTO());
        when(marcaService.inativar(eq(idMarcaInativa))).thenThrow(new RecursoNaoEncontradoException());
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
