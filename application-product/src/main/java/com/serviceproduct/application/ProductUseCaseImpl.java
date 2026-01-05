package com.serviceproduct.application;

import com.serviceproduct.domain.exception.InsufficientStockException;
import com.serviceproduct.domain.model.Product;
import com.serviceproduct.domain.exception.InvalidPriceException;
import com.serviceproduct.domain.exception.ProductNotFoundException;
import com.serviceproduct.domain.ports.in.IProductUseCase;
import com.serviceproduct.domain.ports.out.IProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public class ProductUseCaseImpl implements IProductUseCase {

    private IProductRepositoryPort productRepositoryPort;

    public ProductUseCaseImpl(IProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product getProduct(Long id) {
        return this.getProductOrThrow(id);
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepositoryPort.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        if(product.getPrice().compareTo(new BigDecimal("0")) <= 0){
            throw new InvalidPriceException("The price of the product cannot be negative.");
        }
        if(product.getStock() < product.getMinStock())
            throw  new InsufficientStockException("The product cannot be registered: current stock "+
                    product.getStock() + " is lower than the minimum required " + product.getMinStock());
        return this.productRepositoryPort.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        this.getProductOrThrow(id);
        this.productRepositoryPort.deleteById(id);
    }

    @Override
    public void updateProduct(Product product) {
        this.createProduct(product);
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        return this.productRepositoryPort.findByBrand(brand);
    }

    @Override
    public Product updatePrice(Long id, BigDecimal newPrice) {
        Product savedProduct = this.getProductOrThrow(id);
        if(newPrice.compareTo(new BigDecimal("0")) <= 0){
            throw new InvalidPriceException("The price of the product cannot be negative.");
        }
        savedProduct.setPrice(newPrice);
        return this.productRepositoryPort.save(savedProduct);
    }

    @Override
    public Product updateStock(Long id, int newStock) {
        Product savedProduct = this.getProductOrThrow(id);
        if(newStock < savedProduct.getMinStock())
            throw  new InsufficientStockException("The product cannot be registered: current stock "+
                    newStock + " is lower than the minimum required " + savedProduct.getMinStock());
        savedProduct.setStock(newStock);
        return this.productRepositoryPort.save(savedProduct);
    }

    @Override
    public Product updateMinStock(Long id, int newMinStock) {
        Product savedProduct = this.getProductOrThrow(id);
        if(savedProduct.getStock() < newMinStock)
            throw  new InsufficientStockException("The product cannot be registered: current stock "+
                    savedProduct.getStock() + " is lower than the minimum required " + newMinStock);
        savedProduct.setMinStock(newMinStock);
        return this.productRepositoryPort.save(savedProduct);
    }

    private Product getProductOrThrow(Long id) {
        // Código repetido ahora centralizado
        return this.productRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produc with ID " + id + " not found."));
    }
}
