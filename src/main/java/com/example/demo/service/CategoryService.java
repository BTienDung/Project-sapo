package com.example.demo.service;

import com.example.demo.model.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    @Transactional
    List<Category> findAllCategory();
    void saveCategory(Category category);
    void deleteCategory(Long id);
    Optional<Category> findById(Long id);
}
