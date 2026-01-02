package com.serviceproduct.infrastructure.adapter.output.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long code;
    @Column(length = 20)
    private String name;
    @Column(length = 25)
    private String brand;
    private BigDecimal price;
    @Min(30)
    private int stock;
    private int minStock;
}
