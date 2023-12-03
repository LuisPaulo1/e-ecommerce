package com.stooom.ecommerce.controller;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.service.CategoriaService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTesteUnitario {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

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

        when(categoriaService.buscarTodasAtivas()).thenReturn(new ArrayList<>());
        when(categoriaService.buscarTodasInativas()).thenReturn(new ArrayList<>());

        when(categoriaService.buscarPorId(eq(idExistente))).thenReturn(new CategoriaDTO());
        when(categoriaService.buscarPorId(eq(idInexistente))).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaService.buscarPorIdCategoriaAtiva(eq(idCategoriaAtiva))).thenReturn(new CategoriaDTO());
        when(categoriaService.buscarPorIdCategoriaAtiva(eq(idCategoriaInativa))).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaService.buscarPorIdCategoriaInativa(eq(idCategoriaInativa))).thenReturn(new CategoriaDTO());
        when(categoriaService.buscarPorIdCategoriaInativa(eq(idCategoriaAtiva))).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaService.ativar(eq(idCategoriaInativa))).thenReturn(new CategoriaDTO());
        when(categoriaService.ativar(eq(idCategoriaAtiva))).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaService.inativar(eq(idCategoriaAtiva))).thenReturn(new CategoriaDTO());
        when(categoriaService.inativar(eq(idCategoriaInativa))).thenThrow(RecursoNaoEncontradoException.class);
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
