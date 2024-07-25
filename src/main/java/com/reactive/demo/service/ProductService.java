package com.reactive.demo.service;

import org.springframework.stereotype.Service;

import com.reactive.demo.entity.Product;
import com.reactive.demo.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository p) {
		this.productRepository=p;
	}
	
	public Flux<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Mono<Product> getProduct(Long id){
		return productRepository.findById(id);
	}
	
	public Mono<Product> createProduct(Product p){
		return productRepository.save(p);
	}
	
	public Mono<Product> updateProduct(Long id,Product p){
		return productRepository.findById(id)
				.flatMap(existingProduct->{
					existingProduct.setName(p.getName());
					existingProduct.setDescription(p.getDescription());
					existingProduct.setPrice(p.getPrice());
					return productRepository.save(existingProduct);
				});
	}
	
	public Mono<Void> deleteProduct(Long id){
		return productRepository.deleteById(id);
	}
}
