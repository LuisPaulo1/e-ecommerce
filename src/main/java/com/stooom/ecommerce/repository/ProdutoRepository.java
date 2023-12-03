package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Categoria;
import com.stooom.ecommerce.model.Marca;
import com.stooom.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findAllByAtivoTrue();
    List<Produto> findAllByAtivoFalse();
    Optional<Produto> findByIdAndAtivoTrue(Integer id);
    Optional<Produto> findByIdAndAtivoFalse(Integer id);
    List<Produto> findAllByMarcaAndAtivoTrue(Marca marca);
    List<Produto> findAllByCategoriaAndAtivoTrue(Categoria categoria);
}