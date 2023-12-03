package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Categoria;
import com.stooom.ecommerce.model.Marca;
import com.stooom.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProdutoRepositoryTeste {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Integer idProdutoAtivo;
    private Integer idProdutoInativo;
    private Integer idMarcaAtiva;
    private Integer idCategoriaAtiva;

    @BeforeEach
    void setUp() throws Exception {
        idProdutoAtivo = 1;
        idProdutoInativo = 3;
        idMarcaAtiva = 1;
        idCategoriaAtiva = 2;
    }

    @Test
    void findAllByAtivoTrueDeveriaRetornarListadeProdutosAtivos() {
        List<Produto> produtos = produtoRepository.findAllByAtivoTrue();
        Assertions.assertFalse(produtos.isEmpty());
    }

    @Test
    void findAllByAtivoFalseDeveriaRetornarListadeProdutosInativos() {
        List<Produto> produtos = produtoRepository.findAllByAtivoFalse();
        Assertions.assertFalse(produtos.isEmpty());
    }

    @Test
    void findByIdAndAtivoTrueDeveriaRetornarProdutoAtivo() {
        boolean produtoAtivo = false;
        Optional<Produto> produto = produtoRepository.findByIdAndAtivoTrue(idProdutoAtivo);
        if (produto.isPresent()) {
            produtoAtivo = produto.get().isAtivo();
        }
        Assertions.assertTrue(produtoAtivo);
    }

    @Test
    void findByIdAndAtivoFalseDeveriaRetornarProdutoInativo() {
        boolean produtoInativo = true;
        Optional<Produto> produto = produtoRepository.findByIdAndAtivoFalse(idProdutoInativo);
        if (produto.isPresent()) {
            produtoInativo = produto.get().isAtivo();
        }
        Assertions.assertFalse(produtoInativo);
    }

    @Test
    void findAllByMarcaAndAtivoTrueDeveriaRetornarProdutosAtivosDaMarca() {
        Optional<Marca> marca = marcaRepository.findByIdAndAtivoTrue(idMarcaAtiva);
        marca.ifPresent(value -> {
            List<Produto> produtos = produtoRepository.findAllByMarcaAndAtivoTrue(value);
            Assertions.assertFalse(produtos.isEmpty());
        });
    }

    @Test
    void findAllByCategoriaAndAtivoTrueDeveriaRetornarProdutosAtivosDaCategoria() {
        Optional<Categoria> categoria = categoriaRepository.findByIdAndAtivoTrue(idCategoriaAtiva);
        categoria.ifPresent(value -> {
            List<Produto> produtos = produtoRepository.findAllByCategoriaAndAtivoTrue(value);
            Assertions.assertFalse(produtos.isEmpty());
        });
    }


}
