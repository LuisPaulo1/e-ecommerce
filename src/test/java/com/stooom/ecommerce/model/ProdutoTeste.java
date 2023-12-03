package com.stooom.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProdutoTeste {

    @Test
    public void testarProduto() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto 1");
        produto.setDescricao("Descrição do produto 1");
        produto.setPreco(new BigDecimal(10));
        produto.setAtivo(true);
        produto.setCategoria(new Categoria());
        produto.setMarca(new Marca());

        Assertions.assertEquals(1, produto.getId());
        Assertions.assertEquals("Produto 1", produto.getNome());
        Assertions.assertEquals("Descrição do produto 1", produto.getDescricao());
        Assertions.assertEquals(new BigDecimal(10), produto.getPreco());
        Assertions.assertTrue(produto.isAtivo());
        Assertions.assertNotNull(produto.getCategoria());
        Assertions.assertNotNull(produto.getMarca());
    }

}
