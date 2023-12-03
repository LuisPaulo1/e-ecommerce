package com.stooom.ecommerce.service.impl;

import com.stooom.ecommerce.controller.dto.MarcaDTO;
import com.stooom.ecommerce.model.Marca;
import com.stooom.ecommerce.repository.MarcaRepository;
import com.stooom.ecommerce.service.MarcaService;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MarcaDTO> buscarTodasAtivas() {
        List<Marca> marcas = marcaRepository.findAllByAtivoTrue();
        return marcas.stream()
                .map(marca -> modelMapper.map(marca, MarcaDTO.class))
                .collect(Collectors.toList());
    }

    public List<MarcaDTO> buscarTodasInativas() {
        List<Marca> marcas = marcaRepository.findAllByAtivoFalse();
        return marcas.stream()
                .map(marca -> modelMapper.map(marca, MarcaDTO.class))
                .collect(Collectors.toList());
    }

    public MarcaDTO buscarPorId(Integer id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Marca com id %d não existe.", id)));

        return modelMapper.map(marca, MarcaDTO.class);
    }

    public MarcaDTO buscarPorIdMarcaInativa(Integer id) {
        Marca marca = buscarMarcaInativa(id);
        return modelMapper.map(marca, MarcaDTO.class);
    }

    public MarcaDTO buscarPorIdMarcaAtiva(Integer id) {
        Marca marca = buscarMarcaAtiva(id);
        return modelMapper.map(marca, MarcaDTO.class);
    }


    @Transactional
    public MarcaDTO ativar(Integer id) {
        Marca marca = buscarMarcaInativa(id);
        marca.setAtivo(true);
        marcaRepository.save(marca);
        return modelMapper.map(marca, MarcaDTO.class);
    }

    @Transactional
    public MarcaDTO inativar(Integer id) {
        Marca marca = buscarMarcaAtiva(id);
        marca.setAtivo(false);
        marcaRepository.save(marca);
        return modelMapper.map(marca, MarcaDTO.class);
    }

    private Marca buscarMarcaInativa(Integer id) {
        return marcaRepository.findByIdAndAtivoFalse(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Marca com id %d está ativa ou não existe.", id)));
    }

    private Marca buscarMarcaAtiva(Integer id) {
        return marcaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Marca com id %d está inativa ou não existe.", id)));
    }
}
