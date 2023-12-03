package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.model.Categoria;
import com.stooom.ecommerce.repository.CategoriaRepository;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import com.stooom.ecommerce.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CategoriaServiceTesteUnitario {

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Spy
    private ModelMapper modelMapper;

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

        when(categoriaRepository.findAllByAtivoTrue()).thenReturn(List.of(new Categoria()));
        when(categoriaRepository.findAllByAtivoFalse()).thenReturn(List.of(new Categoria()));

        when(categoriaRepository.findById(idExistente)).thenReturn(Optional.of(new Categoria()));
        when(categoriaRepository.findById(idInexistente)).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaRepository.findByIdAndAtivoTrue(idCategoriaAtiva)).thenReturn(Optional.of(new Categoria()));
        when(categoriaRepository.findByIdAndAtivoTrue(idCategoriaInativa)).thenThrow(RecursoNaoEncontradoException.class);

        when(categoriaRepository.findByIdAndAtivoFalse(idCategoriaAtiva)).thenThrow(RecursoNaoEncontradoException.class);
        when(categoriaRepository.findByIdAndAtivoFalse(idCategoriaInativa)).thenReturn(Optional.of(new Categoria()));

        when(categoriaRepository.save(any())).thenReturn(new Categoria());
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
