package com.stooom.ecommerce.service;

import com.stooom.ecommerce.config.ModelMapperConfig;
import com.stooom.ecommerce.controller.dto.*;
import com.stooom.ecommerce.model.Produto;
import com.stooom.ecommerce.repository.ProdutoRepository;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import com.stooom.ecommerce.service.impl.CategoriaServiceImpl;
import com.stooom.ecommerce.service.impl.MarcaServiceImpl;
import com.stooom.ecommerce.service.impl.ProdutoServiceImpl;
import com.stooom.ecommerce.util.ProdutoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(ModelMapperConfig.class)
@ExtendWith(SpringExtension.class)
public class ProdutoServiceTesteUnitario {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private CategoriaServiceImpl categoriaService;

    @Mock
    private MarcaServiceImpl marcaService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Spy
    private ModelMapper modelMapper;

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

        when(produtoRepository.findAllByAtivoTrue()).thenReturn(List.of(new Produto()));
        when(produtoRepository.findAllByAtivoFalse()).thenReturn(List.of(new Produto()));

        when(produtoRepository.findById(idProdutoExistente)).thenReturn(Optional.of(new Produto()));
        when(produtoRepository.findById(idProdutoInexistente)).thenThrow(RecursoNaoEncontradoException.class);

        when(produtoRepository.findAllByMarcaAndAtivoTrue(any())).thenReturn(List.of(new Produto()));

        when(produtoRepository.findAllByCategoriaAndAtivoTrue(any())).thenReturn(List.of(new Produto()));

        when(produtoRepository.findByIdAndAtivoTrue(idProdutoAtivo)).thenReturn(Optional.of(new Produto()));
        when(produtoRepository.findByIdAndAtivoTrue(idProdutoInativo)).thenThrow(RecursoNaoEncontradoException.class);

        when(produtoRepository.findByIdAndAtivoFalse(idProdutoAtivo)).thenThrow(RecursoNaoEncontradoException.class);
        when(produtoRepository.findByIdAndAtivoFalse(idProdutoInativo)).thenReturn(Optional.of(new Produto()));

        when(produtoRepository.save(any())).thenReturn(new Produto());

        when(marcaService.buscarPorIdMarcaAtiva(idMarcaAtiva)).thenReturn(new MarcaDTO());
        when(marcaService.buscarPorIdMarcaAtiva(idMarcaInativa)).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaService.buscarPorIdCategoriaAtiva(idCategoriaAtiva)).thenReturn(new CategoriaDTO());
        when(categoriaService.buscarPorIdCategoriaAtiva(idCategoriaInativa)).thenThrow(RecursoNaoEncontradoException.class);
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
