package com.serviceproduct.infrastructure.adapter.input.rest;

import com.serviceproduct.domain.model.Product;
import com.serviceproduct.infrastructure.adapter.dto.ProductDTO;
import com.serviceproduct.infrastructure.adapter.dto.UpdatePriceRequest;
import com.serviceproduct.infrastructure.adapter.dto.UpdatetMinStockRequest;
import com.serviceproduct.infrastructure.adapter.dto.UpdatetStockRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Productos", description = "Gestioon de productos")
@RequestMapping("/api/product")
public interface ProductApi {

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id);

    @Operation(summary = "Generar producto")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO);

    @Operation(summary = "Actualizar producto completo")
    @ApiResponse(responseCode = "200", description = "Exito")
    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO);

    @Operation(summary = "Obtener productos")
    @ApiResponse(responseCode = "200", description = "Exito")
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAll();

    @Operation(summary = "Obtener productos dado una marca")
    @ApiResponse(responseCode = "200", description = "Exito")
    @GetMapping("/brand")
    public ResponseEntity<List<ProductDTO>> getByBrand(@NotBlank @RequestParam("brand") String brand);

    @Operation(summary = "Actualizar stock de un producto por ID")
    @ApiResponse(responseCode = "200", description = "Exito")
    @PatchMapping("stock/{id}")
    public ResponseEntity<ProductDTO> updateStock(@PathVariable Long id, @Valid @RequestBody UpdatetStockRequest updatetStockRequest);

    @Operation(summary = "Actualizar stock minimo de un producto por ID")
    @ApiResponse(responseCode = "200", description = "Exito")
    @PatchMapping("min-stock/{id}")
    public ResponseEntity<ProductDTO> updateMinStock(@PathVariable Long id, @Valid @RequestBody UpdatetMinStockRequest updatetMinStockRequest);

    @Operation(summary = "Actualizar precio de un producto por ID")
    @ApiResponse(responseCode = "200", description = "Exito")
    @PatchMapping("price/{id}")
    public ResponseEntity<ProductDTO> updatePrice(@PathVariable Long id, @Valid @RequestBody UpdatePriceRequest updatePriceRequest);
}
