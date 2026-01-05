package com.serviceproduct.infrastructure.adapter.input.rest;

import com.serviceproduct.domain.model.Product;
import com.serviceproduct.domain.ports.in.IProductUseCase;
import com.serviceproduct.infrastructure.adapter.dto.ProductDTO;
import com.serviceproduct.infrastructure.adapter.dto.UpdatePriceRequest;
import com.serviceproduct.infrastructure.adapter.dto.UpdatetMinStockRequest;
import com.serviceproduct.infrastructure.adapter.dto.UpdatetStockRequest;
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
@Validated
public class ProductController implements ProductApi{

    private final IProductUseCase productUseCase;
    private final ProductEntityMapper productEntityMapper;

    public ProductController(IProductUseCase productUseCase, ProductEntityMapper productEntityMapper) {
        this.productUseCase = productUseCase;
        this.productEntityMapper = productEntityMapper;
    }

    public ResponseEntity<ProductDTO> getProduct(Long id){
        Product savedProduct = this.productUseCase.getProduct(id);
        return new ResponseEntity<>(this.productEntityMapper.domainToProductDTO(savedProduct), HttpStatus.OK);
    }

    public ResponseEntity<ProductDTO> saveProduct(ProductDTO productDTO){
        Product productDomain = this.productEntityMapper.producDTOToDomain(productDTO);
        Product savedProduct = this.productUseCase.createProduct(productDomain);
        return new ResponseEntity<>(this.productEntityMapper.domainToProductDTO(savedProduct), HttpStatus.CREATED);
    }

    public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO){
        Product product = this.productEntityMapper.producDTOToDomain(productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDTO>> getAll(){
        List<ProductDTO> productDTOList = this.productUseCase.getProducts().stream()
                .map(this.productEntityMapper::domainToProductDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDTO>> getByBrand(String brand){
        List<ProductDTO> productsByBrand = this.productUseCase.findProductsByBrand(brand).stream()
                .map(this.productEntityMapper::domainToProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsByBrand);
    }

    public ResponseEntity<ProductDTO> updateStock(Long id, UpdatetStockRequest updatetStockRequest){
        Product savedProduct = this.productUseCase.updateStock(id, updatetStockRequest.newStock());
        return ResponseEntity.ok(productEntityMapper.domainToProductDTO(savedProduct));
    }

    public ResponseEntity<ProductDTO> updateMinStock(Long id, UpdatetMinStockRequest updatetMinStockRequest){
        Product savedProduct = this.productUseCase.updateMinStock(id, updatetMinStockRequest.newMinStock());
        return ResponseEntity.ok(productEntityMapper.domainToProductDTO(savedProduct));
    }

    public ResponseEntity<ProductDTO> updatePrice(Long id, UpdatePriceRequest updatePriceRequest){
        Product savedProduct = this.productUseCase.updatePrice(id, updatePriceRequest.newPrice());
        return ResponseEntity.ok(productEntityMapper.domainToProductDTO(savedProduct));
    }
}
