package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Marca;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MarcaRepositoryTeste {

    @Autowired
    private MarcaRepository marcaRepository;

    private Integer idMarcaAtiva;
    private Integer idMarcaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idMarcaAtiva = 1;
        idMarcaInativa = 3;
    }

    @Test
    void findAllByAtivoTrueDeveriaRetornarListadeMarcasAtivas() {
        List<Marca> marcas = marcaRepository.findAllByAtivoTrue();
        Assertions.assertFalse(marcas.isEmpty());
    }

    @Test
    void findAllByAtivoFalseDeveriaRetornarListadeMarcasInativas() {
        List<Marca> marcas = marcaRepository.findAllByAtivoFalse();
        Assertions.assertFalse(marcas.isEmpty());
    }

    @Test
    void findByIdAndAtivoTrueDeveriaRetornarMarcaAtiva() {
        boolean marcaAtiva = false;
        Optional<Marca> marca = marcaRepository.findByIdAndAtivoTrue(idMarcaAtiva);
        if (marca.isPresent()) {
            marcaAtiva = marca.get().isAtivo();
        }
        Assertions.assertTrue(marcaAtiva);
    }

    @Test
    void findByIdAndAtivoFalseDeveriaRetornarMarcaInativa() {
        boolean marcaInativa = true;
        Optional<Marca> marca = marcaRepository.findByIdAndAtivoFalse(idMarcaInativa);
        if (marca.isPresent()) {
            marcaInativa = marca.get().isAtivo();
        }
        Assertions.assertFalse(marcaInativa);
    }
}
