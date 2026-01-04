package com.serviceproduct;


import com.serviceproduct.application.ProductUseCaseImpl;
import com.serviceproduct.domain.exception.InsufficientStockException;
import com.serviceproduct.domain.exception.InvalidPriceException;
import com.serviceproduct.domain.exception.ProductNotFoundException;
import com.serviceproduct.domain.model.Product;
import com.serviceproduct.domain.ports.out.IProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductUseCaseTest {

    @Mock
    private IProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductUseCaseImpl productUseCase;

    @Test
    public void getProduct_WhenIdExists_ShouldReturnProduct(){
        // 1. Arrange (Configuración de Mocks y Datos)
        Product product = new Product();
        product.setId(12L);
        product.setCode(1786L);
        product.setName("pasta dental");
        product.setBrand("colgate");
        product.setPrice(new BigDecimal("6.500"));
        product.setStock(45);
        product.setMinStock(15);

        when(productRepositoryPort.findById(12L)).thenReturn(Optional.of(product));

        // 2. Act (Ejecución de la Lógica de negocio
        Product productSaved = this.productUseCase.getProduct(12L);

        // 3. Assert (Verificación de Resultados)
        assertNotNull(productSaved);
        assertEquals(12L, productSaved.getId());
        assertEquals(1786L, productSaved.getCode());
        assertEquals("pasta dental", productSaved.getName());
        assertEquals(45, productSaved.getStock());
        assertEquals(15, productSaved.getMinStock());

        verify(productRepositoryPort, times(1)).findById(12L);
    }

    @Test
    public void getProductById_NotFound_ThrowsException(){
        // 1. Arrange
        when(productRepositoryPort.findById(66L)).thenReturn(Optional.empty());

        // 2y3. Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productUseCase.getProduct(66L);
        });

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, times(1)).findById(66L);

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

    @Test
    public void getProducts(){
        // 1. Arrange (Configuración de Mocks y Datos)
        List<Product> productsList = new ArrayList<>();
        productsList.add(new Product(1L, 435L, "milk", "marge", new BigDecimal("34"), 70, 10));
        productsList.add(new Product(1L, 435L, "mocka", "marge", new BigDecimal("100"), 198, 46));

        when(productRepositoryPort.findAll()).thenReturn(productsList);

        // 2. Act (Ejecución de la Lógica de negocio
        List<Product> products = this.productUseCase.getProducts();

        // 3. Assert (Verificación de Resultados)
        assertNotNull(products);
        assertEquals(2, products.size());

        // verifiacamos el primer elemento
        assertEquals("milk", products.get(0).getName());
        assertEquals(new BigDecimal("34"), products.get(0).getPrice());

        // verificamos el segundo elemnto
        assertEquals("mocka", products.get(1).getName());
        assertEquals(new BigDecimal("100"), products.get(1).getPrice());

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, times(1)).findAll();

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

    @Test
    public void shouldReturnEmptyListWhenNoProductsExist(){
        // 1. Arrange (Configuración de Mocks y Datos)
        when(productRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        // 2. Act (Ejecución de la Lógica de negocio
        List<Product> products = this.productUseCase.getProducts();

        // 3. Assert (Verificación de Resultados)
        assertNotNull(products, "La lista non deberia ser nula.");
        assertTrue(products.isEmpty(),"La lista deberia estar vacia.");
        assertEquals(0, products.size());

        verify(productRepositoryPort, times(1)).findAll();

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

    @Test
    public void shouldReturnSavedProductWhenDataIsValid(){
        // 1. Arrange (Configuración de Mocks y Datos)
        Product inputProduct = new Product(null, 42235L, "milk", "gloria", new BigDecimal("23"), 70, 10);
        Product savedProduct = new Product(23L, 42235L, "milk", "gloria", new BigDecimal("23"), 70, 10);

        when(productRepositoryPort.save(any(Product.class))).thenReturn(savedProduct);

        // 2. Act (Ejecución de la Lógica de negocio
        // Se usa any para que no importe si el objeto cambia de instancia en el camino
        Product result = this.productUseCase.createProduct(inputProduct);

        // 3. Assert (Verificación de Resultados)
        assertNotNull(result, "El producto guardado no debería ser nulo");
        assertEquals(23L, result.getId(), "El ID debería ser el generado por el repositorio");
        assertEquals("milk", result.getName());

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, times(1)).save(any(Product.class));

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);

    }

    @Test
    public void shouldThrowInsufficientStockExceptionWhenStockIsLow(){
        // 1. Arrange
        int stockInfucient = 21;
        Product inputProduct = new Product(null, 235L, "chocolate", "gloria", new BigDecimal("238"), stockInfucient, 50);

        // 2. Act y Assert
        assertThrows(InsufficientStockException.class, () ->{
            this.productUseCase.createProduct(inputProduct);
        });

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, never()).save(any(Product.class));

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

    @Test
    public void shouldThrowInvalidPriceExceptionWhenPriceIsZero(){
        // 1. Arrange
        Product inputProduct = new Product(null, 2231325L, "galleta", "kick", new BigDecimal("0"), 21, 50);

        // 2. Act y Assert
        assertThrows(InvalidPriceException.class, () ->{
            this.productUseCase.createProduct(inputProduct);
        });

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, never()).save(any(Product.class));

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

    @Test
    public void deleteProduct(){
        // 1. Arrange
        Product product = new Product(null, 42235L, "milk", "gloria", new BigDecimal("23"), 70, 10);
        when(productRepositoryPort.findById(15L)).thenReturn(Optional.of(product));

        // 2. Act
        this.productUseCase.deleteProduct(15L);

        // 3. Assert
        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, times(1)).findById(15L);
        verify(productRepositoryPort, times(1)).deleteById(15L);
        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }


    @Test
    public void shouldThrowNotFoundExceptionWhenProductDoesNotExist(){
        // 1. Arrange
        Long id = 34L;
        when(productRepositoryPort.findById(id)).thenReturn(Optional.empty());

        // 2y3. Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productUseCase.deleteProduct(id);
        });

        // Se verifica que se llamo solo una vez
        verify(productRepositoryPort, times(1)).findById(id);

        // Se verifica que el borrado nunca ocurrio
        verify(productRepositoryPort, never()).deleteById(id); /// o times(0)

        // Se Verifica que el repository de product no fue usado para nada más
        verifyNoMoreInteractions(productRepositoryPort);
    }

}
