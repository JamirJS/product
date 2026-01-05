package com.serviceproduct.domain.ports.in;

import com.serviceproduct.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductUseCase {

    Product getProduct(Long id);
    List<Product> getProducts();
    Product createProduct(Product product);
    void deleteProduct(Long id);
    void updateProduct(Product product);
    List<Product> findProductsByBrand(String brand);
    Product updatePrice(Long id, BigDecimal newPrice);
    Product updateStock(Long id, int newStock);
    Product updateMinStock(Long id, int newMinStock);
}
