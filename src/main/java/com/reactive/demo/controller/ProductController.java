package com.reactive.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.demo.entity.Product;
import com.reactive.demo.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private ProductService productService;
	
	public ProductController(ProductService productService) {
	    this.productService = productService;
	}

	
	@GetMapping
	public Flux<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Mono<Product> getProductById(@PathVariable Long id){
		return productService.getProduct(id);
	}
	
	@PostMapping
	public Mono<Product> createProduct(@RequestBody Product p){
		return productService.createProduct(p);
	}
	
	@PutMapping("/{id}")
	public Mono<Product> updateProduct(@PathVariable Long id , @RequestBody Product p){
		return productService.updateProduct(id, p);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteProduct(@PathVariable Long id){
		return productService.deleteProduct(id);
	}
	
	
}
