package com.stooom.ecommerce.controller.openapi;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
import com.stooom.ecommerce.controller.exception.StandardError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Marcas")
public interface MarcaControllerOpenAPI {

    @ApiOperation("Lista as marcas ativas")
    ResponseEntity<List<MarcaDTO>> findAllActive();

    @ApiOperation("Lista as marcas inativas")
    ResponseEntity<List<MarcaDTO>> findAllInactive();

    @ApiOperation("Busca uma marca por Id (ativa ou inativa)")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Marca não encontrada", response = StandardError.class)
    })
    ResponseEntity<MarcaDTO> findById(Integer marcaId);

    @ApiOperation("Busca uma marca ativa por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Marca não encontrada", response = StandardError.class)
    })
    ResponseEntity<MarcaDTO> findByIdActive(Integer marcaId);

    @ApiOperation("Busca uma marca inativa por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Marca não encontrada", response = StandardError.class)
    })
    ResponseEntity<MarcaDTO> findByIdInactive(Integer marcaId);

    @ApiOperation("Ativa uma marca")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Marca já está ativa", response = StandardError.class)
    })
    ResponseEntity<MarcaDTO> activate(Integer marcaId);

    @ApiOperation("Inativa uma marca")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Marca já está inativa", response = StandardError.class)
    })
    ResponseEntity<MarcaDTO> inactivate(Integer marcaId);
}