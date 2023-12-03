package com.stooom.ecommerce.service;

import com.stooom.ecommerce.controller.dto.ProdutoDTO;
import com.stooom.ecommerce.controller.dto.ProdutoImputDTO;
import com.stooom.ecommerce.controller.dto.ProdutoResumoDTO;

import java.util.List;

public interface ProdutoService {
    List<ProdutoResumoDTO> buscarTodosAtivos();
    List<ProdutoResumoDTO> buscarTodosInativos();
    List<ProdutoResumoDTO> buscarPorMarca(Integer marcaId);
    List<ProdutoResumoDTO> buscarPorCategoria(Integer categoriaId);
    ProdutoDTO buscarPorId(Integer id);
    ProdutoDTO buscarPorIdProdutoAtivo(Integer id);
    ProdutoDTO buscarPorIdProdutoInativo(Integer id);
    ProdutoDTO cadastrar(ProdutoImputDTO produtoImputDTO);
    ProdutoDTO atualizar(Integer id, ProdutoImputDTO produtoImputDTO);
    ProdutoDTO ativar(Integer id);
    ProdutoDTO inativar(Integer id);
}
