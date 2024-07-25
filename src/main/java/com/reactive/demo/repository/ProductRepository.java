package com.reactive.demo.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.reactive.demo.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product,Long>{

}
