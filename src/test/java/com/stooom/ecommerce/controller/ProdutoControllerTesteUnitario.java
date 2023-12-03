package com.stooom.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stooom.ecommerce.controller.dto.ProdutoDTO;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.service.ProdutoService;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import com.stooom.ecommerce.util.ProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTesteUnitario {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    private ProdutoImputDTO productInputDTO;
    private Integer idProdutoExistente;
    private Integer idProdutoInexistente;
    private Integer idProdutoAtivo;
    private Integer idProdutoInativo;
    private Integer idCategoriaAtiva;
    private Integer idCategoriaInativa;
    private Integer idMarcaAtiva;
    private Integer idMarcaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idProdutoExistente = 1;
        idProdutoInexistente = 100;
        idProdutoAtivo = 1;
        idProdutoInativo = 3;
        idCategoriaAtiva = 2;
        idCategoriaInativa = 1;
        idMarcaAtiva = 1;
        idMarcaInativa = 3;
        productInputDTO = ProdutoFactory.criarProduto();

        when(produtoService.buscarTodosAtivos()).thenReturn(new ArrayList<>());
        when(produtoService.buscarTodosInativos()).thenReturn(new ArrayList<>());

        when(produtoService.buscarPorMarca(eq(idMarcaAtiva))).thenReturn(new ArrayList<>());
        when(produtoService.buscarPorMarca(eq(idMarcaInativa))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.buscarPorCategoria(eq(idCategoriaAtiva))).thenReturn(new ArrayList<>());
        when(produtoService.buscarPorCategoria(eq(idCategoriaInativa))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.buscarPorId(eq(idProdutoExistente))).thenReturn(new ProdutoDTO());
        when(produtoService.buscarPorId(eq(idProdutoInexistente))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.buscarPorIdProdutoAtivo(eq(idProdutoAtivo))).thenReturn(new ProdutoDTO());
        when(produtoService.buscarPorIdProdutoAtivo(eq(idProdutoInativo))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.buscarPorIdProdutoInativo(eq(idProdutoInativo))).thenReturn(new ProdutoDTO());
        when(produtoService.buscarPorIdProdutoInativo(eq(idProdutoAtivo))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.cadastrar(eq(productInputDTO))).thenReturn(new ProdutoDTO());

        when(produtoService.atualizar(eq(idProdutoInativo), any())).thenThrow(new RecursoNaoEncontradoException());
        when(produtoService.atualizar(eq(idProdutoAtivo), any())).thenReturn(new ProdutoDTO());

        when(produtoService.ativar(eq(idProdutoInativo))).thenReturn(new ProdutoDTO());
        when(produtoService.ativar(eq(idProdutoAtivo))).thenThrow(new RecursoNaoEncontradoException());

        when(produtoService.inativar(eq(idProdutoAtivo))).thenReturn(new ProdutoDTO());
        when(produtoService.inativar(eq(idProdutoInativo))).thenThrow(new RecursoNaoEncontradoException());
    }

    @Test
    void findAllActiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/ativos")
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllInactiveDeveriaRetornarOsRecursosComStatusOk() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/inativos")
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllByMarcaDeveriaRetornarStatusOkQuandoMarcaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/marca/{marcaId}", idMarcaAtiva)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllByMarcaDeveriaRetornarStatusNotFoundQuandoMarcaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/marca/{marcaId}", idMarcaInativa)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findAllByCategoriaDeveriaRetornarStatusOkQuandoCategoriaEstiverAtiva() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/categoria/{categoriaId}", idCategoriaAtiva)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findAllByCategoriaDeveriaRetornarStatusNotFoundQuandoCategoriaEstiverInativa() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/categoria/{categoriaId}", idCategoriaInativa)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdDeveriaRetornarStatusOkQuandoProdutoExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{id}", idProdutoExistente)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdDeveriaRetornarStatusNotFoundQuandoProdutoNaoExistir() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{id}", idProdutoInexistente)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdActiveDeveriaRetornarStatusOkQuandoProdutoEstiverAtivo() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{produtoId}/ativo", idProdutoAtivo)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdActiveDeveriaRetornarStatusNotFoundQuandoProdutoEstiverInativo() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{produtoId}/ativo", idProdutoInativo)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void findByIdInactiveDeveriaRetornarStatusOkQuandoProdutoEstiverInativo() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{produtoId}/inativo", idProdutoInativo)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void findByIdInactiveDeveriaRetornarStatusNotFoundQuandoProdutoEstiverAtivo() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/v1/produtos/{produtoId}/inativo", idProdutoAtivo)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void saveDeveriaRetornarStatusCreatedQuandoProdutoForCadastradoComCamposValidos() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isCreated());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComNomeNulo() throws Exception {
        productInputDTO.setNome(null);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComNomeVazio() throws Exception {
        productInputDTO.setNome("");
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComNomeEmBranco() throws Exception {
        productInputDTO.setNome(" ");
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComPrecoNulo() throws Exception {
        productInputDTO.setPreco(null);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComCategoriaNula() throws Exception {
        productInputDTO.setCategoriaId(null);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void saveDeveriaRetornarStatusBadRequestQuandoProdutoForCadastradoComMarcaNula() throws Exception {
        productInputDTO.setMarcaId(null);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isBadRequest());
    }

    @Test
    void updateDeveriaRetornarStatusOkQuandoProdutoForAtualizadoComCamposValidos() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/{id}", idProdutoAtivo)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void updateDeveriaRetornarStatusNotFoundQuandoProdutoForAtualizadoComProdutoInativo() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/{id}", idProdutoInativo)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void activateDeveriaRetornarStatusOkQuandoProdutoEstiverInativo() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/ativar")
                        .param("produtoId", idProdutoInativo.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void activateDeveriaRetornarStatusNotFoundQuandoProdutoEstiverAtivo() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/ativar")
                        .param("produtoId", idProdutoAtivo.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void inactivateDeveriaRetornarStatusOkQuandoProdutoEstiverAtivo() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/inativar")
                        .param("produtoId", idProdutoAtivo.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isOk());
    }

    @Test
    void inactivateDeveriaRetornarStatusNotFoundQuandoProdutoEstiverInativo() throws Exception {
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/inativar")
                        .param("produtoId", idProdutoInativo.toString())
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

}
