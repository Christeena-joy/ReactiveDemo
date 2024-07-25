package com.reactive.demo.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactive.demo.entity.Product;
import com.reactive.demo.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ProductServiceTest {
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product(1L, "Product1", "Description1", 19.99);
        when(productRepository.findAll()).thenReturn(Flux.just(product));

        productService.getAllProducts()
            .as(StepVerifier::create)
            .expectNext(product)
            .verifyComplete();
    }

    @Test
    void testGetProductById() {
        Product product = new Product(1L, "Product1", "Description1", 19.99);
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));

        productService.getProduct(1L)
            .as(StepVerifier::create)
            .expectNext(product)
            .verifyComplete();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "Product1", "Description1", 19.99);
        Product savedProduct = new Product(1L, "Product1", "Description1", 19.99);
        when(productRepository.save(product)).thenReturn(Mono.just(savedProduct));

        productService.createProduct(product)
            .as(StepVerifier::create)
            .expectNext(savedProduct)
            .verifyComplete();
    }

    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Product1", "Description1", 19.99);
        Product updatedProduct = new Product(1L, "ProductUpdated", "DescriptionUpdated", 29.99);

        when(productRepository.findById(1L)).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(updatedProduct)).thenReturn(Mono.just(updatedProduct));

        productService.updateProduct(1L, updatedProduct)
            .as(StepVerifier::create)
            .expectNext(updatedProduct)
            .verifyComplete();
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

        productService.deleteProduct(1L)
            .as(StepVerifier::create)
            .verifyComplete();
    }

}
