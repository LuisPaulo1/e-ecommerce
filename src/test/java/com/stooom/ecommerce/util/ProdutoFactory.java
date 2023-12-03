package com.stooom.ecommerce.util;

import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;

import java.math.BigDecimal;

public final class ProdutoFactory {
    private ProdutoFactory() {}
    public static ProdutoImputDTO criarProduto() {
        return ProdutoImputDTO.builder()
                .nome("iPhone 12")
                .descricao("iPhone 12 128GB Azul, com Tela de 6,1”.")
                .preco(new BigDecimal("6000.00"))
                .categoriaId(2)
                .marcaId(2)
                .build();
    }
}
