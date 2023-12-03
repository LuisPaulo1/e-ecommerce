package com.stooom.ecommerce.service.impl;

import com.stooom.ecommerce.controller.dto.*;
import com.stooom.ecommerce.model.Categoria;
import com.stooom.ecommerce.model.Marca;
import com.stooom.ecommerce.model.Produto;
import com.stooom.ecommerce.repository.ProdutoRepository;
import com.stooom.ecommerce.service.ProdutoService;
import com.stooom.ecommerce.service.exception.RecursoNaoEncontradoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Autowired
    private MarcaServiceImpl marcaService;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProdutoResumoDTO> buscarTodosAtivos() {
        List<Produto> produtos = produtoRepository.findAllByAtivoTrue();
        return getListaProdutosDTO(produtos);
    }

    public List<ProdutoResumoDTO> buscarTodosInativos() {
        List<Produto> produtos = produtoRepository.findAllByAtivoFalse();
        return getListaProdutosDTO(produtos);
    }

    public List<ProdutoResumoDTO> buscarPorMarca(Integer marcaId) {
        MarcaDTO marcaDTO = marcaService.buscarPorIdMarcaAtiva(marcaId);
        Marca marca = modelMapper.map(marcaDTO, Marca.class);
        List<Produto> produtos = produtoRepository.findAllByMarcaAndAtivoTrue(marca);
        return getListaProdutosDTO(produtos);
    }

    public List<ProdutoResumoDTO> buscarPorCategoria(Integer categoriaId) {
        CategoriaDTO categoriaDTO = categoriaService.buscarPorIdCategoriaAtiva(categoriaId);
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        List<Produto> produtos = produtoRepository.findAllByCategoriaAndAtivoTrue(categoria);
        return getListaProdutosDTO(produtos);
    }

    public ProdutoDTO buscarPorId(Integer id) {
        Produto produto = buscarProduto(id);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public ProdutoDTO buscarPorIdProdutoAtivo(Integer id) {
        Produto produto = buscarProdutoAtivo(id);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public ProdutoDTO buscarPorIdProdutoInativo(Integer id) {
        Produto produto = buscarProdutoInativo(id);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Transactional
    public ProdutoDTO cadastrar(ProdutoImputDTO produtoImputDTO) {
        Produto produto = modelMapper.map(produtoImputDTO, Produto.class);
        return getProdutoDTO(produtoImputDTO, produto);
    }

    @Transactional
    public ProdutoDTO atualizar(Integer id, ProdutoImputDTO produtoImputDTO) {
        Produto produto = buscarProdutoAtivo(id);
        modelMapper.map(produtoImputDTO, produto);
        return getProdutoDTO(produtoImputDTO, produto);
    }

    @Transactional
    public ProdutoDTO ativar(Integer id) {
        Produto produto = buscarProdutoInativo(id);
        produto.setAtivo(true);
        produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Transactional
    public ProdutoDTO inativar(Integer id) {
        Produto produto = buscarProdutoAtivo(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    private Produto buscarProdutoInativo(Integer id) {
        return produtoRepository.findByIdAndAtivoFalse(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Produto com id %d está ativo ou não existe.", id)));
    }

    private Produto buscarProdutoAtivo(Integer id) {
        return produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Produto com id %d está inativo ou não existe.", id)));
    }

    private Produto buscarProduto(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Produto com id %d não existe.", id)));
    }

    private ProdutoDTO getProdutoDTO(ProdutoImputDTO produtoImputDTO, Produto produto) {
        CategoriaDTO categoriaDTO = categoriaService.buscarPorIdCategoriaAtiva(produtoImputDTO.getCategoriaId());
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        MarcaDTO marcaDTO = marcaService.buscarPorIdMarcaAtiva(produtoImputDTO.getMarcaId());
        Marca marca = modelMapper.map(marcaDTO, Marca.class);
        produto.setCategoria(categoria);
        produto.setMarca(marca);
        produto = produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    private List<ProdutoResumoDTO> getListaProdutosDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResumoDTO.class))
                .collect(Collectors.toList());
    }

}
