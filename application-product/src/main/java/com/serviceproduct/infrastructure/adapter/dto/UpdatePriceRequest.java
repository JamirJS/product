package com.serviceproduct.infrastructure.adapter.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdatePriceRequest(@NotNull(message = "El precio no puede ser nulo")
                                 @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")BigDecimal newPrice) {
}
