package com.serviceproduct.infrastructure.adapter.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductCreateDTO {
    private Long code;

    @NotBlank(message = "La nombre del producto es obligatoria.")
    private String name;

    @NotBlank(message = "La marca del producto es obligatoria.")
    @Size(max = 25, message = "La marca no puede superar mas de 25 caracteres")
    private String brand;

    @NotNull(message = "El precio es obligatorio")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Formato de precio inválido (máximo 10 enteros y 2 decimales)")
    private BigDecimal price;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 31, message = "El stcok debe ser mayor o igual a 31")
    private Integer stock;

    @NotNull(message = "El stock mínimo no puede ser nulo")
    @Min(value = 30, message = "El stock mínimo debe ser mayor o igual a 30")
    private Integer minStock;
}
