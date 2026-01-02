package com.serviceproduct.infrastructure.adapter.mapper;

import com.serviceproduct.domain.model.Product;
import com.serviceproduct.infrastructure.adapter.dto.ProductDTO;
import com.serviceproduct.infrastructure.adapter.output.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    ProductEntityMapper INSTANCE = Mappers.getMapper(ProductEntityMapper.class);

    Product toDomain(ProductEntity productEntity);
    ProductEntity toEntity(Product productDomain);
    ProductDTO domainToProductDTO(Product productDomain);
    Product producDTOToDomain(ProductDTO productDTO);
}
