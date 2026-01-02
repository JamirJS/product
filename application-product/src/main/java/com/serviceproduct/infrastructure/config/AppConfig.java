package com.serviceproduct.infrastructure.config;

import com.serviceproduct.application.ProductUseCaseImpl;
import com.serviceproduct.domain.ports.in.IProductUseCase;
import com.serviceproduct.domain.ports.out.IProductRepositoryPort;
import com.serviceproduct.infrastructure.adapter.mapper.ProductEntityMapper;
import com.serviceproduct.infrastructure.adapter.output.repository.JpaProductRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public IProductUseCase productUseCase(IProductRepositoryPort productRepositoryPort){
        return new ProductUseCaseImpl(productRepositoryPort);
    }

}
