package com.stooom.ecommerce.repository;

import com.stooom.ecommerce.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    List<Marca> findAllByAtivoTrue();
    List<Marca> findAllByAtivoFalse();
    Optional<Marca> findByIdAndAtivoTrue(Integer id);
    Optional<Marca> findByIdAndAtivoFalse(Integer id);

}
