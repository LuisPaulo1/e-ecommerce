package com.stooom.ecommerce.controller;

import com.stooom.ecommerce.controller.dto.ProdutoDTO;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.controller.dto.ProdutoResumoDTO;
import com.stooom.ecommerce.controller.openapi.ProdutoControllerOpenAPI;
import com.stooom.ecommerce.service.ProdutoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/v1/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController implements ProdutoControllerOpenAPI {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping(value = "/ativos")
    public ResponseEntity<List<ProdutoResumoDTO>> findAllActive() {
        List<ProdutoResumoDTO> produtos = produtoService.buscarTodosAtivos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/inativos")
    public ResponseEntity<List<ProdutoResumoDTO>> findAllInactive() {
        List<ProdutoResumoDTO> produtos = produtoService.buscarTodosInativos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/marca/{marcaId}")
    public ResponseEntity<List<ProdutoResumoDTO>> findAllByMarca(@PathVariable Integer marcaId) {
        List<ProdutoResumoDTO> produtos = produtoService.buscarPorMarca(marcaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResumoDTO>> findAllByCategoria(@PathVariable Integer categoriaId) {
        List<ProdutoResumoDTO> produtos = produtoService.buscarPorCategoria(categoriaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping(value = "/{produtoId}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Integer produtoId) {
        ProdutoDTO produto = produtoService.buscarPorId(produtoId);
        return ResponseEntity.ok(produto);
    }

    @GetMapping(value = "/{produtoId}/ativo")
    public ResponseEntity<ProdutoDTO> findByIdActive(@PathVariable Integer produtoId) {
        ProdutoDTO produto = produtoService.buscarPorIdProdutoAtivo(produtoId);
        return ResponseEntity.ok(produto);
    }

    @GetMapping(value = "/{produtoId}/inativo")
    public ResponseEntity<ProdutoDTO> findByIdInactive(@PathVariable Integer produtoId) {
        ProdutoDTO produto = produtoService.buscarPorIdProdutoInativo(produtoId);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> save(@RequestBody @Valid ProdutoImputDTO produtoImputDTO) {
        log.info("Cadastrando produto: {}", produtoImputDTO);
        ProdutoDTO produto = produtoService.cadastrar(produtoImputDTO);
        log.info("Produto cadastrado: {}", produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping(value = "/{produtoId}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Integer produtoId, @RequestBody @Valid ProdutoImputDTO produtoImputDTO) {
        log.info("Atualizando produto: {}", produtoImputDTO);
        ProdutoDTO produto = produtoService.atualizar(produtoId, produtoImputDTO);
        log.info("Produto atualizado: {}", produto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<ProdutoDTO> activate(@RequestParam Integer produtoId) {
        log.info("Ativando produto de id: {}", produtoId);
        ProdutoDTO produto = produtoService.ativar(produtoId);
        log.info("Produto ativado com sucesso");
        return ResponseEntity.ok(produto);
    }

    @PutMapping(value = "/inativar")
    public ResponseEntity<ProdutoDTO> inactivate(@RequestParam Integer produtoId) {
        log.info("Inativando produto de id: {}", produtoId);
        ProdutoDTO produto = produtoService.inativar(produtoId);
        log.info("Produto inativado com sucesso");
        return ResponseEntity.ok(produto);
    }

}
