package com.stooom.ecommerce.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoImputDTO {

    @ApiModelProperty(example = "iPhone 12")
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "iPhone 12 128GB Azul, com Tela de 6,1”.")
    private String descricao;

    @ApiModelProperty(example = "6000.00")
    @NotNull
    private BigDecimal preco;

    @ApiModelProperty(example = "1")
    @NotNull
    private Integer categoriaId;

    @ApiModelProperty(example = "1")
    @NotNull
    private Integer marcaId;
}
