package br.com.appday.product.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.appday.product.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
