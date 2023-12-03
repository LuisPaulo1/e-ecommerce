package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> buscarTodasAtivas();
    List<CategoriaDTO> buscarTodasInativas();
    CategoriaDTO buscarPorId(Integer id);
    CategoriaDTO buscarPorIdCategoriaAtiva(Integer id);
    CategoriaDTO buscarPorIdCategoriaInativa(Integer id);
    CategoriaDTO ativar(Integer id);
    CategoriaDTO inativar(Integer id);
}
