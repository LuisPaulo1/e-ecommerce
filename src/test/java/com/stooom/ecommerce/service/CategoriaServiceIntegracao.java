package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CategoriaServiceIntegracao {

    @Autowired
    private CategoriaService categoriaService;

    private Integer idExistente;
    private Integer idInexistente;
    private Integer idCategoriaAtiva;
    private Integer idCategoriaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idExistente = 1;
        idInexistente = 100;
        idCategoriaAtiva = 2;
        idCategoriaInativa = 1;
    }

    @Test
    void buscarTodasAtivasDeveriaRetornarRecursos() {
        List<CategoriaDTO> result = categoriaService.buscarTodasAtivas();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarTodasInativasDeveriaRetornarRecursos() {
        List<CategoriaDTO> result = categoriaService.buscarTodasInativas();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarPorIdDeveriaRetornarCategoriaDTOQuandoIdExistir() {
        CategoriaDTO categoria = categoriaService.buscarPorId(idExistente);
        Assertions.assertNotNull(categoria);
    }

    @Test
    void buscarPorIdDeveriaLancarRecursoNaoEncontradoExceptionQuandoIdNaoExistir() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.buscarPorId(idInexistente);
        });
    }

    @Test
    void buscarPorIdCategoriaAtivaDeveriaRetornarCategoriaDTOQuandoCategoriaEstiverAtiva() {
        CategoriaDTO categoria = categoriaService.buscarPorIdCategoriaAtiva(idCategoriaAtiva);
        Assertions.assertNotNull(categoria);
    }

    @Test
    void buscarPorIdCategoriaAtivaDeveriaLancarRecursoNaoEncontradoExceptionQuandoCategoriaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.buscarPorIdCategoriaAtiva(idCategoriaInativa);
        });
    }

    @Test
    void buscarPorIdCategoriaInativaDeveriaRetornarCategoriaDTOQuandoCategoriaEstiverInativa() {
        CategoriaDTO categoria = categoriaService.buscarPorIdCategoriaInativa(idCategoriaInativa);
        Assertions.assertNotNull(categoria);
    }

    @Test
    void buscarPorIdCategoriaInativaDeveriaLancarRecursoNaoEncontradoExceptionQuandoCategoriaEstiverAtiva() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.buscarPorIdCategoriaInativa(idCategoriaAtiva);
        });
    }

    @Test
    void ativarDeveriaAtivarCategoriaQuandoCategoriaEstiverInativa() {
        CategoriaDTO categoria = categoriaService.ativar(idCategoriaInativa);
        Assertions.assertTrue(categoria.isAtivo());
    }

    @Test
    void ativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoCategoriaEstiverAtiva() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.ativar(idCategoriaAtiva);
        });
    }

    @Test
    void inativarDeveriaInativarCategoriaQuandoCategoriaEstiverAtiva() {
        CategoriaDTO categoria = categoriaService.inativar(idCategoriaAtiva);
        Assertions.assertFalse(categoria.isAtivo());
    }

    @Test
    void inativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoCategoriaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaService.inativar(idCategoriaInativa);
        });
    }

}
