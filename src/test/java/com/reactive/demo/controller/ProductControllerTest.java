package com.reactive.demo.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.demo.entity.Product;
import com.reactive.demo.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        Product product = new Product(1L, "Product1", "Description1", 19.99);
        when(productService.getAllProducts()).thenReturn(Flux.just(product));

        webTestClient.get().uri("/api/products")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .contains(product);
    }

    @Test
    void testGetProductById() {
        Product product = new Product(1L, "Product1", "Description1", 19.99);
        when(productService.getProduct(1L)).thenReturn(Mono.just(product));

        webTestClient.get().uri("/api/products/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "Product1", "Description1", 19.99);
        Product savedProduct = new Product(1L, "Product1", "Description1", 19.99);
        when(productService.createProduct(product)).thenReturn(Mono.just(savedProduct));

        webTestClient.post().uri("/api/products")
            .bodyValue(product)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(savedProduct);
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Product1", "Description1", 19.99);
        Product updatedProduct = new Product(1L, "ProductUpdated", "DescriptionUpdated", 29.99);

        when(productService.updateProduct(1L, updatedProduct)).thenReturn(Mono.just(updatedProduct));

        webTestClient.put().uri("/api/products/1")
            .bodyValue(updatedProduct)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/products/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();
    }
}
