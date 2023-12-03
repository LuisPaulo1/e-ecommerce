package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Categoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CategoriaRepositoryTeste {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Integer idCategoriaAtiva;
    private Integer idCategoriaInativa;

    @BeforeEach
    void setUp() throws Exception {
        idCategoriaAtiva = 2;
        idCategoriaInativa = 1;
    }

    @Test
    void findAllByAtivoTrueDeveriaRetornarListadeCategoriasAtivas() {
        List<Categoria> categorias = categoriaRepository.findAllByAtivoTrue();
        Assertions.assertFalse(categorias.isEmpty());
    }

    @Test
    void findAllByAtivoFalseDeveriaRetornarListadeCategoriasInativas() {
        List<Categoria> categorias = categoriaRepository.findAllByAtivoFalse();
        Assertions.assertFalse(categorias.isEmpty());
    }

    @Test
    void findByIdAndAtivoTrueDeveriaRetornarCategoriaAtiva() {
        boolean categoriaAtiva = false;
        Optional<Categoria> categoria = categoriaRepository.findByIdAndAtivoTrue(idCategoriaAtiva);
        if (categoria.isPresent()) {
            categoriaAtiva = categoria.get().isAtivo();
        }
        Assertions.assertTrue(categoriaAtiva);
    }

    @Test
    void findByIdAndAtivoFalseDeveriaRetornarCategoriaInativa() {
        boolean categoriaInativa = true;
        Optional<Categoria> categoria = categoriaRepository.findByIdAndAtivoFalse(idCategoriaInativa);
        if (categoria.isPresent()) {
            categoriaInativa = categoria.get().isAtivo();
        }
        Assertions.assertFalse(categoriaInativa);
    }

}
