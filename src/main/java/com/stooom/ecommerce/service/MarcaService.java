package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.MarcaDTO;

import java.util.List;

public interface MarcaService {
    List<MarcaDTO> buscarTodasAtivas();
    List<MarcaDTO> buscarTodasInativas();
    MarcaDTO buscarPorId(Integer id);
    MarcaDTO buscarPorIdMarcaInativa(Integer id);
    MarcaDTO buscarPorIdMarcaAtiva(Integer id);
    MarcaDTO ativar(Integer id);
    MarcaDTO inativar(Integer id);
}
