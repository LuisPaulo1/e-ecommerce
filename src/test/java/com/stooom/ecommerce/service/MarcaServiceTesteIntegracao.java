package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
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
public class MarcaServiceTesteIntegracao {

    @Autowired
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
    }

    @Test
    void buscarTodasAtivasDeveriaRetornarRecursos() {
        List<MarcaDTO> result = marcaService.buscarTodasAtivas();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarTodasInativasDeveriaRetornarRecursos() {
        List<MarcaDTO> result = marcaService.buscarTodasInativas();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void buscarPorIdDeveriaRetornarMarcaDTOQuandoIdExistir() {
        MarcaDTO marca = marcaService.buscarPorId(idExistente);
        Assertions.assertNotNull(marca);
    }

    @Test
    void buscarPorIdDeveriaLancarRecursoNaoEncontradoExceptionQuandoIdNaoExistir() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            marcaService.buscarPorId(idInexistente);
        });
    }

    @Test
    void buscarPorIdMarcaAtivaDeveriaRetornarMarcaDTOQuandoMarcaEstiverAtiva() {
        MarcaDTO marca = marcaService.buscarPorIdMarcaAtiva(idMarcaAtiva);
        Assertions.assertNotNull(marca);
    }

    @Test
    void buscarPorIdMarcaAtivaDeveriaLancarRecursoNaoEncontradoExceptionQuandoMarcaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            marcaService.buscarPorIdMarcaAtiva(idMarcaInativa);
        });
    }

    @Test
    void buscarPorIdMarcaInativaDeveriaRetornarMarcaDTOQuandoMarcaEstiverInativa() {
        MarcaDTO marca = marcaService.buscarPorIdMarcaInativa(idMarcaInativa);
        Assertions.assertNotNull(marca);
    }

    @Test
    void buscarPorIdMarcaInativaDeveriaLancarRecursoNaoEncontradoExceptionQuandoMarcaEstiverAtiva() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            marcaService.buscarPorIdMarcaInativa(idMarcaAtiva);
        });
    }

    @Test
    void ativarDeveriaAtivarMarcaQuandoMarcaEstiverInativa() {
        MarcaDTO marca = marcaService.ativar(idMarcaInativa);
        Assertions.assertTrue(marca.isAtivo());
    }

    @Test
    void ativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoMarcaEstiverAtiva() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            marcaService.ativar(idMarcaAtiva);
        });
    }

    @Test
    void inativarDeveriaInativarMarcaQuandoMarcaEstiverAtiva() {
        MarcaDTO marca = marcaService.inativar(idMarcaAtiva);
        Assertions.assertFalse(marca.isAtivo());
    }

    @Test
    void inativarDeveriaLancarRecursoNaoEncontradoExceptionQuandoMarcaEstiverInativa() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            marcaService.inativar(idMarcaInativa);
        });
    }

}
