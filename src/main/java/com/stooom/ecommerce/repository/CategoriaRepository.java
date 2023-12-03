package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAllByAtivoTrue();
    List<Categoria> findAllByAtivoFalse();
    Optional<Categoria> findByIdAndAtivoTrue(Integer id);
    Optional<Categoria> findByIdAndAtivoFalse(Integer id);
}
