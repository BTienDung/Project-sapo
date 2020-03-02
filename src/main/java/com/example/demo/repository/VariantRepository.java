package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.Variant;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository extends PagingAndSortingRepository<Variant, Long> {
    Optional<Product> findByProductId(Long id);
}
