package com.stooom.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class CategoriaTeste {

    @Test
    public void testarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Categoria 1");
        categoria.setDescricao("Descrição da categoria 1");
        categoria.setAtivo(true);
        categoria.setProdutos(new HashSet<>());

        Assertions.assertEquals(1, categoria.getId());
        Assertions.assertEquals("Categoria 1", categoria.getNome());
        Assertions.assertEquals("Descrição da categoria 1", categoria.getDescricao());
        Assertions.assertTrue(categoria.isAtivo());
        Assertions.assertNotNull(categoria.getProdutos());
    }
}
