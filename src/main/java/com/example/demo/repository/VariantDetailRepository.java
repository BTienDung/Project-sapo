package com.example.demo.repository;

import com.example.demo.model.VariantDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantDetailRepository extends PagingAndSortingRepository<VariantDetail, Long> {
    Long countAllByVariantId(Long id);
    List<VariantDetail> findAllByVariantId(Long id);
    //Optional<Variant> findByVariantId(Long id);
    Optional<VariantDetail> findByVariantDetailNameAndVariantId(String name, Long id);
}
