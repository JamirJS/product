package com.serviceproduct.infrastructure.adapter.output.repository;

import com.serviceproduct.infrastructure.adapter.output.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJpaProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByBrand(String brand);
}
