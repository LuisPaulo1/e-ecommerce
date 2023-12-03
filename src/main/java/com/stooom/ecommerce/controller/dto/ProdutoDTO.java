package com.stooom.ecommerce.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProdutoDTO {

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

    @ApiModelProperty(example = "true")
    private boolean ativo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;
}
