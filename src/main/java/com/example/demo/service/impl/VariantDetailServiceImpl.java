package com.example.demo.service.impl;

import com.example.demo.model.VariantDetail;
import com.example.demo.repository.VariantDetailRepository;
import com.example.demo.service.VariantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VariantDetailServiceImpl implements VariantDetailService {
    @Autowired
    private VariantDetailRepository variantDetailRepository;

    @Override
    public void save(VariantDetail variantDetail) {
        variantDetailRepository.save(variantDetail);
    }

    @Override
    public void delete(Long id) {
        variantDetailRepository.deleteById(id);
    }

    @Override
    public Long countAllByVariantId(Long id) {
        return variantDetailRepository.countAllByVariantId(id);
    }

    @Override
    public List<VariantDetail> findAllByVariantId(Long id) {
        return variantDetailRepository.findAllByVariantId(id);
    }

//    @Override
//    public Optional<Variant> findByVariantId(Long id) {
//        return optionRepository.findByVariantId(id);
//    }

    @Override
    public Optional<VariantDetail> findByVariantDetailNameAndVariantId(String name, Long id) {
        return variantDetailRepository.findByVariantDetailNameAndVariantId(name, id);
    }

    @Override
    public Iterable<VariantDetail> findAllOption() {
        return variantDetailRepository.findAll();
    }

    @Override
    public Optional<VariantDetail> findByVariantDetailId(Long id) {
        return variantDetailRepository.findById(id);
    }


}
