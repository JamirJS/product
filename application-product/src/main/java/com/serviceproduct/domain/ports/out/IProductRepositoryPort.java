package com.serviceproduct.domain.ports.out;

import com.serviceproduct.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepositoryPort {

    Product save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);
    List<Product> findAll();
    boolean existsById(Long id);
    List<Product> findByBrand(String brand);
}
