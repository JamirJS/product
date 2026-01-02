package com.serviceproduct.infrastructure.adapter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private Long code;
    private String name;
    @NotBlank(message = "Brand is required.")
    private String brand;
    private BigDecimal price;
    @Min(value = 30, message = "The value must be at least 30 than 30.")
    private int stock;
    private int minStock;
}
