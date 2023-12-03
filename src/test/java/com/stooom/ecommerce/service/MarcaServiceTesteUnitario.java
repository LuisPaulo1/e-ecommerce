package com.stooom.ecommerce.service;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
import com.stooom.ecommerce.model.Marca;
import com.stooom.ecommerce.repository.MarcaRepository;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import com.stooom.ecommerce.service.impl.MarcaServiceImpl;
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

@ExtendWith(SpringExtension.class)
public class MarcaServiceTesteUnitario {

    @InjectMocks
    private MarcaServiceImpl marcaService;

    @Mock
    private MarcaRepository marcaRepository;

    @Spy
    private ModelMapper modelMapper;

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

        when(marcaRepository.findAllByAtivoTrue()).thenReturn(List.of(new Marca()));
        when(marcaRepository.findAllByAtivoFalse()).thenReturn(List.of(new Marca()));

        when(marcaRepository.findById(idExistente)).thenReturn(Optional.of(new Marca()));
        when(marcaRepository.findById(idInexistente)).thenThrow(RecursoNaoEncontradoException.class);

        when(marcaRepository.findByIdAndAtivoTrue(idMarcaAtiva)).thenReturn(Optional.of(new Marca()));
        when(marcaRepository.findByIdAndAtivoTrue(idMarcaInativa)).thenThrow(RecursoNaoEncontradoException.class);

        when(marcaRepository.findByIdAndAtivoFalse(idMarcaAtiva)).thenThrow(RecursoNaoEncontradoException.class);
        when(marcaRepository.findByIdAndAtivoFalse(idMarcaInativa)).thenReturn(Optional.of(new Marca()));

        when(marcaRepository.save(any())).thenReturn(new Marca());
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
