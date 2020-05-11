package com.example.demo.service;

import com.example.demo.model.VariantDetail;

import java.util.List;
import java.util.Optional;

public interface VariantDetailService {
    void save(VariantDetail variantDetail);
    void delete(Long id);
    Long countAllByVariantId(Long id);
    List<VariantDetail> findAllByVariantId(Long id);
    Iterable<VariantDetail> findAllOption();
    Optional<VariantDetail> findByVariantDetailId(Long id);
    Optional<VariantDetail> findByVariantDetailNameAndVariantId(String name, Long id);

}
