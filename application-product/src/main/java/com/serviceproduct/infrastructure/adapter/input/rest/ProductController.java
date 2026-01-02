package com.serviceproduct.infrastructure.adapter.input.rest;

import com.serviceproduct.domain.model.Product;
import com.serviceproduct.domain.ports.in.IProductUseCase;
import com.serviceproduct.infrastructure.adapter.dto.ProductDTO;
import com.serviceproduct.infrastructure.adapter.mapper.ProductEntityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@Validated
public class ProductController {

    private final IProductUseCase productUseCase;
    private final ProductEntityMapper productEntityMapper;

    public ProductController(IProductUseCase productUseCase, ProductEntityMapper productEntityMapper) {
        this.productUseCase = productUseCase;
        this.productEntityMapper = productEntityMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        Product savedProduct = this.productUseCase.getProduct(id);
        return new ResponseEntity<>(this.productEntityMapper.domainToProductDTO(savedProduct), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO){
        Product productDomain = this.productEntityMapper.producDTOToDomain(productDTO);
        Product savedProduct = this.productUseCase.createProduct(productDomain);
        return new ResponseEntity<>(this.productEntityMapper.domainToProductDTO(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTO productDTO){
        Product product = this.productEntityMapper.producDTOToDomain(productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAll(){
        List<ProductDTO> productDTOList = this.productUseCase.getProducts().stream()
                .map(this.productEntityMapper::domainToProductDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductDTO>> getByBrand(@NotBlank @RequestParam("brand") String brand){
        List<ProductDTO> productsByBrand = this.productUseCase.findProductsByBrand(brand).stream()
                .map(this.productEntityMapper::domainToProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsByBrand);
    }
}
