package com.serviceproduct.infrastructure.adapter.output.repository;

import com.serviceproduct.domain.model.Product;
import com.serviceproduct.domain.ports.out.IProductRepositoryPort;
import com.serviceproduct.infrastructure.adapter.mapper.ProductEntityMapper;
import com.serviceproduct.infrastructure.adapter.output.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaProductRepositoryAdapter implements IProductRepositoryPort {

    private final IJpaProductRepository jpaProductRepsitory;
    private final ProductEntityMapper productEntityMapper;

    public JpaProductRepositoryAdapter(IJpaProductRepository jpaProductRepsitory, ProductEntityMapper productEntityMapper) {
        this.jpaProductRepsitory = jpaProductRepsitory;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = this.productEntityMapper.toEntity(product);
        ProductEntity savedProductEntity= this.jpaProductRepsitory.save(productEntity);
        return this.productEntityMapper.toDomain(savedProductEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.jpaProductRepsitory.findById(id)
                .map(this.productEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        this.jpaProductRepsitory.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return this.jpaProductRepsitory.findAll().stream()
                .map(this.productEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return this.jpaProductRepsitory.existsById(id);
    }

    @Override
    public List<Product> findByBrand(String brand) {
        return jpaProductRepsitory.findByBrand(brand).stream()
                .map(this.productEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
