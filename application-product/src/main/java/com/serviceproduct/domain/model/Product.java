package com.serviceproduct.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long id;
    private Long code;
    private String name;
    private String brand;
    private BigDecimal price;
    private int stock;
    private int minStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public Product() {
    }

    public Product(Long id, Long code, String name, String brand, BigDecimal price, int stock, int minStock) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.minStock = minStock;
    }

    public Product(Long code, String name, String brand, BigDecimal price, int stock, int minStock) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.minStock = minStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si apuitan al mismo espacio de meoria
        if (o == null || getClass() != o.getClass()) return false; // Si es nulo o de otra clase
        Product product = (Product) o;
        // Comparamos el "code", que es nuestra identidad de negocio
        // el id no xq podria haber ocjetos con id null al momento de pasar por parametro y se considerarian igual
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        // Se genera el hash basado únicamente en el codigo único
        return Objects.hash(code);
    }
}
