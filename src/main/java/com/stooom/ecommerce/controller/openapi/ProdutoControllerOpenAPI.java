package com.stooom.ecommerce.controller.openapi;

import com.stooom.ecommerce.controller.dto.ProdutoDTO;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.controller.dto.ProdutoResumoDTO;
import com.stooom.ecommerce.controller.exception.StandardError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Produtos")
public interface ProdutoControllerOpenAPI {

    @ApiOperation("Lista os produtos ativos")
    ResponseEntity<List<ProdutoResumoDTO>> findAllActive();

    @ApiOperation("Lista os produtos inativos")
    ResponseEntity<List<ProdutoResumoDTO>> findAllInactive();

    @ApiOperation("Lista os produtos por marca")
    ResponseEntity<List<ProdutoResumoDTO>> findAllByMarca(Integer marcaId);

    @ApiOperation("Lista os produtos por categoria")
    ResponseEntity<List<ProdutoResumoDTO>> findAllByCategoria(Integer categoriaId);

    @ApiOperation("Busca um produto por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto não encontrado", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> findById(Integer produtoId);

    @ApiOperation("Busca um produto ativo por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto não encontrado", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> findByIdActive(Integer produtoId);

    @ApiOperation("Busca um produto inativo por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto não encontrado", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> findByIdInactive(Integer produtoId);

    @ApiOperation("Cadastra um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 400, message = "Requisição inválida", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> save(ProdutoImputDTO produtoImputDTO);

    @ApiOperation("Atualiza um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado", response = StandardError.class),
            @ApiResponse(code = 400, message = "Requisição inválida", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> update(Integer produtoId, ProdutoImputDTO produtoImputDTO);

    @ApiOperation("Ativa um produto")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto já está ativo", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> activate(Integer produtoId);

    @ApiOperation("Inativa um produto")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto já está inativo", response = StandardError.class)
    })
    ResponseEntity<ProdutoDTO> inactivate(Integer produtoId);
}
