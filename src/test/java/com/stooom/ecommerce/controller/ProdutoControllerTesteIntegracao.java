package com.stooom.ecommerce.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.util.ProdutoFactory;
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
public class ProdutoControllerTesteIntegracao {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void saveDeveriaRetornarStatusNotFoundQuandoProdutoForCadastradoComCategoriaInativa() throws Exception {
        productInputDTO.setCategoriaId(idCategoriaInativa);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void saveDeveriaRetornarStatusNotFoundQuandoProdutoForCadastradoComMarcaInativa() throws Exception {
        productInputDTO.setMarcaId(idMarcaInativa);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(post("/v1/produtos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
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
    void updateDeveriaRetornarStatusNotFoundQuandoProdutoForAtualizadoComCategoriaInativa() throws Exception {
        productInputDTO.setCategoriaId(idCategoriaInativa);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/{id}", idProdutoAtivo)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpectAll(status().isNotFound());
    }

    @Test
    void updateDeveriaRetornarStatusNotFoundQuandoProdutoForAtualizadoComMarcaInativa() throws Exception {
        productInputDTO.setMarcaId(idMarcaInativa);
        String jsonBody = objectMapper.writeValueAsString(productInputDTO);
        ResultActions result =
                mockMvc.perform(put("/v1/produtos/{id}", idProdutoAtivo)
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
