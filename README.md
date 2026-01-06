
# Product Management System (Hexagonal Architecture)

Este es un microservicio para la gestión de productos, desarrollado siguiendo los principios de la Arquitectura Hexagonal (Puertos y Adaptadores). El objetivo es mantener la lógica de negocio aislada de las dependencias externas como bases de datos o APIs.

## 🏗️ Arquitectura
El proyecto se divide en tres capas principales:

    Domain: Entidades de negocio (Product) y excepciones personalizadas.

    Application: Casos de uso (ProductUseCase) y puertos de salida (ProductRepositoryPort).

    Infrastructure: Adaptadores de entrada (REST Controllers) y adaptadores de salida (JPA/Hibernate).
## 🛠️ Tecnologías Utilizadas

    Java 17

    Spring Boot 3

    Spring Data JPA

    JUnit 5 & Mockito (Para pruebas unitarias y de comportamiento)

    Mapstruct

    Swagger

    Docker

    Postgres
    
 
## 🧪 Testing Strategy
Se ha implementado una suite de pruebas robusta enfocada en la lógica de negocio dentro de la capa de aplicación.

Pruebas de Casos de Uso

Utilizamos Mockito para aislar la lógica de negocio y verificar el comportamiento de los puertos. Algunos escenarios cubiertos:

    Validación de Negocio: No se permiten productos con precio cero o stock insuficiente.

    Manejo de Excepciones: Verificación de lanzamientos de ProductNotFoundException.

    Integridad de Flujo: Uso de verify() y never() para asegurar que el repositorio solo se llame bajo condiciones válidas.

Ejemplo de un test de validación:

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

## 👤 Autor
JamirJS - [GitHub Profile](https://github.com/JamirJS)
## API 

#### Get all product

```http
  GET /api/product
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.

## 🚀 API Reference

### Obtener todos los productos
`GET /products`

| Parámetro | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `limit`   | `string` | **Opcional**. Límite de resultados |

### Obtener un producto
`GET /products/${id}`

| Parámetro | Tipo     | Descripción                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Requerido**. ID del producto    |

#### Ejemplo de respuesta:
```json
{
  "id": "123",
  "code": 432,
  "name": "Laptop Gaming",
  "brand": "milk",
  "price": 1200,
  "stock": 32,
  "min_stock": 20
}
