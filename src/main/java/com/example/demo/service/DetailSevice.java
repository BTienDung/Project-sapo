package com.example.demo.service;

import com.example.demo.model.DetailProduct;

import java.util.Optional;

public interface DetailSevice {
    void save(DetailProduct detailProduct);
    Optional<DetailProduct> findByProductId(Long id);
    Optional<DetailProduct> findAllByVariantId(Long id);
    Iterable<DetailProduct> findAllDetail();
}
