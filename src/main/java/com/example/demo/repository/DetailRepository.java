package com.example.demo.repository;

import com.example.demo.model.DetailProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailRepository extends PagingAndSortingRepository<DetailProduct, Long> {
    Optional<DetailProduct> findByProductId(Long id);
    Optional<DetailProduct> findAllByVariantId(Long id);
}
