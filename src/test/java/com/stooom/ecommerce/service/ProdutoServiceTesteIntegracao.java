package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.ProdutoDTO;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.controller.dto.ProdutoResumoDTO;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import com.stooom.ecommerce.util.ProdutoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ProdutoServiceTesteIntegracao {

    @Autowired
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
    }

    @Test
    void buscarTodosAtivosDeveriaRetornarRecursos() {
        List<ProdutoResumoDTO> result = produtoService.buscarTodosAtivos();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarTodosInativosDeveriaRetornarRecursos() {
        List<ProdutoResumoDTO> result = produtoService.buscarTodosInativos();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarPorMarcaDeveriaRetornarRecursosQuandoMarcaEstiverAtiva() {
        List<ProdutoResumoDTO> result = produtoService.buscarPorMarca(idMarcaAtiva);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarPorMarcaDeveriaLancarRecursoNaoEncontradoExceptionQuandoMarcaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorMarca(idMarcaInativa);
        });
    }

    @Test
    void buscarPorCategoriaDeveriaRetornarRecursosQuandoCategoriaEstiverAtiva() {
        List<ProdutoResumoDTO> result = produtoService.buscarPorCategoria(idCategoriaAtiva);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarPorCategoriaDeveriaLancarRecursoNaoEncontradoExceptionQuandoCategoriaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorCategoria(idCategoriaInativa);
        });
    }

    @Test
    void buscarPorIdDeveriaRetornarProdutoDTOQuandoIdExistir() {
        ProdutoDTO produto = produtoService.buscarPorId(idProdutoExistente);
        Assertions.assertNotNull(produto);
    }

    @Test
    void buscarPorIdDeveriaLancarRecursoNaoEncontradoExceptionQuandoIdNaoExistir() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorId(idProdutoInexistente);
        });
    }

    @Test
    void buscarPorIdProdutoAtivoDeveriaRetornarProdutoDTOQuandoProdutoEstiverAtivo() {
        ProdutoDTO produto = produtoService.buscarPorIdProdutoAtivo(idProdutoAtivo);
        Assertions.assertNotNull(produto);
    }

    @Test
    void buscarPorIdProdutoAtivoDeveriaLancarRecursoNaoEncontradoExceptionQuandoProdutoEstiverInativo() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorIdProdutoAtivo(idProdutoInativo);
        });
    }

    @Test
    void buscarPorIdProdutoInativoDeveriaRetornarProdutoDTOQuandoProdutoEstiverInativo() {
        ProdutoDTO produto = produtoService.buscarPorIdProdutoInativo(idProdutoInativo);
        Assertions.assertNotNull(produto);
    }

    @Test
    void buscarPorIdProdutoInativoDeveriaLancarRecursoNaoEncontradoExceptionQuandoProdutoEstiverAtivo() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorIdProdutoInativo(idProdutoAtivo);
        });
    }

    @Test
    void cadastrarDeveriaCadastrarNovoProduto() {
        ProdutoDTO produto = produtoService.cadastrar(productInputDTO);
        Assertions.assertNotNull(produto);
    }

    @Test
    void cadastrarDeveriaCadastrarNovoProdutoComCampoAtivoTrue() {
        ProdutoDTO produto = produtoService.cadastrar(productInputDTO);
        Assertions.assertTrue(produto.isAtivo());
    }

    @Test
    void atualizarDeveriaAtualizarProduto() {
        ProdutoDTO produto = produtoService.atualizar(idProdutoAtivo, productInputDTO);
        Assertions.assertNotNull(produto);
    }

    @Test
    void ativarDeveriaAtivarProdutoQuandoProdutoEstiverInativo() {
        ProdutoDTO produto = produtoService.ativar(idProdutoInativo);
        Assertions.assertTrue(produto.isAtivo());
    }

    @Test
    void ativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoProdutoEstiverAtivo() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.ativar(idProdutoAtivo);
        });
    }

    @Test
    void inativarDeveriaInativarProdutoQuandoProdutoEstiverAtivo() {
        ProdutoDTO produto = produtoService.inativar(idProdutoAtivo);
        Assertions.assertFalse(produto.isAtivo());
    }

    @Test
    void inativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoProdutoEstiverInativo() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.inativar(idProdutoInativo);
        });
    }
}
