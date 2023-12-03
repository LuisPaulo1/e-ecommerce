package com.stooom.ecommerce.service.impl;

import com.stooom.ecommerce.controller.dto.CategoriaDTO;
import com.stooom.ecommerce.model.Categoria;
import com.stooom.ecommerce.repository.CategoriaRepository;
import com.stooom.ecommerce.service.CategoriaService;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoriaDTO> buscarTodasAtivas() {
        List<Categoria> categorias = categoriaRepository.findAllByAtivoTrue();
        return getListaCategoriaDTO(categorias);
    }

    public List<CategoriaDTO> buscarTodasInativas() {
        List<Categoria> categorias = categoriaRepository.findAllByAtivoFalse();
        return getListaCategoriaDTO(categorias);
    }

    public CategoriaDTO buscarPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Categoria com id %d não existe.", id)));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO buscarPorIdCategoriaAtiva(Integer id) {
        Categoria categoria = buscarCategoriaAtiva(id);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO buscarPorIdCategoriaInativa(Integer id) {
        Categoria categoria = buscarCategoriaInativa(id);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO ativar(Integer id) {
        Categoria categoria = buscarCategoriaInativa(id);
        categoria.setAtivo(true);
        categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO inativar(Integer id) {
        Categoria categoria = buscarCategoriaAtiva(id);
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    private Categoria buscarCategoriaInativa(Integer id) {
        return categoriaRepository.findByIdAndAtivoFalse(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Categoria com id %d está ativa ou não existe.", id)));
    }

    private Categoria buscarCategoriaAtiva(Integer id) {
        return categoriaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Categoria com id %d está inativa ou não existe.", id)));
    }

    private List<CategoriaDTO> getListaCategoriaDTO(List<Categoria> categorias) {
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(java.util.stream.Collectors.toList());
    }

}
