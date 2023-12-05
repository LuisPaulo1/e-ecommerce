package com.stooom.ecommerce.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoriaDTO {

    @ApiModelProperty(example = "1")
    private Integer id;

    @ApiModelProperty(example = "Tecnologia")
    private String nome;

    @ApiModelProperty(example = "Celulares")
    private String descricao;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}
