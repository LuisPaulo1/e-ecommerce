package com.stooom.ecommerce.controller;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.controller.openapi.CategoriaControllerOpenAPI;
import com.stooom.ecommerce.service.CategoriaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(path = "/v1/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController implements CategoriaControllerOpenAPI {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/ativas")
    public ResponseEntity<List<CategoriaDTO>> findAllActive() {
        List<CategoriaDTO> categorias = categoriaService.buscarTodasAtivas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping(value = "/inativas")
    public ResponseEntity<List<CategoriaDTO>> findAllInactive() {
        List<CategoriaDTO> categorias = categoriaService.buscarTodasInativas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @GetMapping(value = "/{categoriaId}/ativa")
    public ResponseEntity<CategoriaDTO> findByIdActive(@PathVariable Integer categoriaId) {
        return ResponseEntity.ok(categoriaService.buscarPorIdCategoriaAtiva(categoriaId));
    }

    @GetMapping(value = "/{categoriaId}/inativa")
    public ResponseEntity<CategoriaDTO> findByIdInactive(@PathVariable Integer categoriaId) {
        return ResponseEntity.ok(categoriaService.buscarPorIdCategoriaInativa(categoriaId));
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<CategoriaDTO> activate(@RequestParam Integer categoriaId) {
        log.info("Ativando categoria de id: {}", categoriaId);
        CategoriaDTO categoria = categoriaService.ativar(categoriaId);
        log.info("Categoria ativada com sucesso");
        return ResponseEntity.ok(categoria);
    }

    @PutMapping(value = "/inativar")
    public ResponseEntity<CategoriaDTO> inactivate(@RequestParam Integer categoriaId) {
        log.info("Inativando categoria de id: {}", categoriaId);
        CategoriaDTO categoria = categoriaService.inativar(categoriaId);
        log.info("Categoria inativada com sucesso");
        return ResponseEntity.ok(categoria);
    }
}