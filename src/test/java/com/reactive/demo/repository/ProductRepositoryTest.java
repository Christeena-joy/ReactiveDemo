package com.reactive.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import com.reactive.demo.entity.Product;

import reactor.test.StepVerifier;

@DataR2dbcTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository p;
	
	@Test
    void testSaveAndFindById() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setPrice(19.99);

        p.save(product)
            .as(StepVerifier::create)
            .expectNextMatches(savedProduct -> {
                assertThat(savedProduct.getId()).isNotNull();
                assertThat(savedProduct.getName()).isEqualTo("Test Product");
                return true;
            })
            .verifyComplete();

        p.findById(product.getId())
            .as(StepVerifier::create)
            .expectNextMatches(foundProduct -> {
                assertThat(foundProduct).isNotNull();
                assertThat(foundProduct.getName()).isEqualTo("Test Product");
                return true;
            })
            .verifyComplete();
    }

    @Test
    void testFindAll() {
    	Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setPrice(19.99);

        p.save(product)
         .thenMany(p.findAll())
         .as(StepVerifier::create)
         .expectNextMatches(foundProduct -> {
             assertThat(foundProduct).isNotNull();
             assertThat(foundProduct.getName()).isEqualTo("Test Product");
             return true;
         })
         .expectNextCount(0) // If there's only one product, no more products should be found
         .verifyComplete();
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setName("Delete Product");
        product.setDescription("This product will be deleted");
        product.setPrice(9.99);

        p.save(product)
            .flatMap(savedProduct -> p.deleteById(savedProduct.getId()))
            .as(StepVerifier::create)
            .verifyComplete();

        p.findById(product.getId())
            .as(StepVerifier::create)
            .expectNextCount(0)
            .verifyComplete();
    }
}
