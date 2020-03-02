package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);
    List<Product> findAllProductByStatus();
    Optional<Product> findById(Long id);
}
