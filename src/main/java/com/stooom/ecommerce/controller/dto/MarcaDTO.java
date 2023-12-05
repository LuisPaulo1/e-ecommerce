package com.stooom.ecommerce.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarcaDTO {

    @ApiModelProperty(example = "1")
    private Integer id;

    @ApiModelProperty(example = "Apple")
    private String nome;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}
