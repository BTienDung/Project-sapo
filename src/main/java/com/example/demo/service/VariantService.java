package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Variant;

import java.util.List;
import java.util.Optional;

public interface VariantService {
    void save(Variant variant);
    void delete(Long id);
    Optional<Variant> findById(Long id);
    Iterable<Variant> findAllVariant();
    Optional<Product> findByProductId(Long id);
    List<Variant> findAllByProductId(Long id);

}
