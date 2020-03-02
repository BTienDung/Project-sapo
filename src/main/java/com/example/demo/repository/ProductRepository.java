package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query("select c from Product c where c.status = true ")
    List<Product> findAllByStatus();

}
