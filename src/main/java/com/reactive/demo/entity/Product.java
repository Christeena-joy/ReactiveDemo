package com.reactive.demo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("products")
@NoArgsConstructor
public class Product {
	@Id
    private Long id;
    private String name;
    private String description;
    private Double price;

}
