package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQueryRepository  extends PagingAndSortingRepository<Product, Long> {
    //@Query("select p from product p")
    // @Query("select v.id, v.name, p.nameproduct from product p, variant v where p.id = v.product_id")
    List<Product> findAll();


}
