package com.stooom.ecommerce.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResumoDTO {

    @ApiModelProperty(example = "1")
    private Integer id;

    @ApiModelProperty(example = "iPhone 12")
    private String nome;

    @ApiModelProperty(example = "iPhone 12 128GB Azul, com Tela de 6,1”.")
    private String descricao;

    @ApiModelProperty(example = "6000.00")
    private BigDecimal preco;

    private CategoriaDTO categoria;
    private MarcaDTO marca;
}
