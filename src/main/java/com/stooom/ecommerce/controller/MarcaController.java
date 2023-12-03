package com.stooom.ecommerce.controller;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
import com.stooom.ecommerce.controller.openapi.MarcaControllerOpenAPI;
import com.stooom.ecommerce.service.MarcaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(path = "/v1/marcas", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarcaController implements MarcaControllerOpenAPI {

    @Autowired
    private MarcaService marcaService;

    @GetMapping(value = "/ativas")
    public ResponseEntity<List<MarcaDTO>> findAllActive() {
        List<MarcaDTO> marcas = marcaService.buscarTodasAtivas();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping(value = "/inativas")
    public ResponseEntity<List<MarcaDTO>> findAllInactive() {
        List<MarcaDTO> marcas = marcaService.buscarTodasInativas();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MarcaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @GetMapping(value = "/{marcaId}/ativa")
    public ResponseEntity<MarcaDTO> findByIdActive(@PathVariable Integer marcaId) {
        return ResponseEntity.ok(marcaService.buscarPorIdMarcaAtiva(marcaId));
    }

    @GetMapping(value = "/{marcaId}/inativa")
    public ResponseEntity<MarcaDTO> findByIdInactive(@PathVariable Integer marcaId) {
        return ResponseEntity.ok(marcaService.buscarPorIdMarcaInativa(marcaId));
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<MarcaDTO> activate(@RequestParam Integer marcaId) {
        log.info("Ativando marca de id: {}", marcaId);
        MarcaDTO marca = marcaService.ativar(marcaId);
        log.info("Marca ativada com sucesso");
        return ResponseEntity.ok(marca);
    }

    @PutMapping(value = "/inativar")
    public ResponseEntity<MarcaDTO> inactivate(@RequestParam Integer marcaId) {
        log.info("Inativando marca de id: {}", marcaId);
        MarcaDTO marca = marcaService.inativar(marcaId);
        log.info("Marca inativada com sucesso");
        return ResponseEntity.ok(marca);
    }
}
