package com.stooom.ecommerce.controller.openapi;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.controller.exception.StandardError;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Categorias")
public interface CategoriaControllerOpenAPI {

    @ApiOperation("Lista as categorias ativas")
    ResponseEntity<List<CategoriaDTO>> findAllActive();

    @ApiOperation("Lista as categorias inativas")
    ResponseEntity<List<CategoriaDTO>> findAllInactive();

    @ApiOperation("Busca uma categoria por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Categoria não encontrada", response = StandardError.class)
    })
    ResponseEntity<CategoriaDTO> findById(Integer categoriaId);

    @ApiOperation("Busca uma categoria ativa por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Categoria não encontrada", response = StandardError.class)
    })
    ResponseEntity<CategoriaDTO> findByIdActive(Integer categoriaId);

    @ApiOperation("Busca uma categoria inativa por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Categoria não encontrada", response = StandardError.class)
    })
    ResponseEntity<CategoriaDTO> findByIdInactive(Integer categoriaId);

    @ApiOperation("Ativa uma categoria")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Categoria já está ativa", response = StandardError.class)
    })
    ResponseEntity<CategoriaDTO> activate(Integer categoriaId);

    @ApiOperation(value = "Inativa uma categoria")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Categoria já está inativa", response = StandardError.class)
    })
    ResponseEntity<CategoriaDTO> inactivate(Integer categoriaId);
}