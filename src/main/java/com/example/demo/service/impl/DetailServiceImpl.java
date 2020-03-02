package com.example.demo.service.impl;

import com.example.demo.model.DetailProduct;
import com.example.demo.repository.DetailRepository;
import com.example.demo.service.DetailSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailServiceImpl implements DetailSevice {
    @Autowired
    private DetailRepository detailRepository;
    @Override
    public void save(DetailProduct detailProduct) {
        detailRepository.save(detailProduct);
    }

    @Override
    public Optional<DetailProduct> findByProductId(Long id) {
        return detailRepository.findByProductId(id);
    }

    @Override
    public Optional<DetailProduct> findAllByVariantId(Long id) {
        return detailRepository.findAllByVariantId(id);
    }

    @Override
    public Iterable<DetailProduct> findAllDetail() {
        return detailRepository.findAll();
    }


}
