package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.model.Variant;
import com.example.demo.repository.VariantRepository;
import com.example.demo.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariantServiceImpl implements VariantService {
    @Autowired
    private VariantRepository variantRepository;
    @Override
    public void save(Variant variant) {
        variantRepository.save(variant);
    }

    @Override
    public void delete(Long id) {
        variantRepository.deleteById(id);
    }

    @Override
    public Optional<Variant> findById(Long id) {
        return variantRepository.findById(id);
    }

    @Override
    public Iterable<Variant> findAllVariant() {
        return variantRepository.findAll();
    }

    @Override
    public Optional<Product> findByProductId(Long id) {
        return variantRepository.findByProductId(id);
    }


}
