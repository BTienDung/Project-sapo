package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.VariantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/variantdetail")
public class VariantDetailController {
    @Autowired
    private VariantDetailService variantDetailService;
    @PostMapping
    public ResponseEntity<VariantDetail> createOption(@Valid @RequestBody VariantDetail variantDetail, UriComponentsBuilder uriComponentsBuilder) {
        //dem
        Long countOption = variantDetailService.countAllByVariantId(variantDetail.getVariant().getId());
        //tim tat ca variantDetail theo variant id
        Iterable<VariantDetail> optionList = variantDetailService.findAllByVariantId(variantDetail.getVariant().getId());
        List<VariantDetail> allVariantDetailFind = new ArrayList<>();
        for (VariantDetail o : optionList) {
            allVariantDetailFind.add(o);
        }
        if (variantDetail.getVariantDetailName() != null) {
            if (countOption < 3) {
                for (int i = 0; i < allVariantDetailFind.size(); i++) {
                    try {
                        VariantDetail variantDetailDb = variantDetailService.findByVariantDetailNameAndVariantId(variantDetail.getVariantDetailName(), variantDetail.getVariant().getId()).get();
                        if (allVariantDetailFind.get(i).getVariantDetailName().equals(variantDetailDb.getVariantDetailName())) {
                            return new ResponseEntity("Create failed!!! Product is exist.", HttpStatus.BAD_REQUEST);
                        }
                    } catch (Exception e) {
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found", e);
                    }
                }
                variantDetailService.save(variantDetail);
                return new ResponseEntity<VariantDetail>(variantDetail, HttpStatus.CREATED);
            } else {
                return new ResponseEntity("Create failed!!! VariantDetail must be less than 3.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity("Create failed!!! VariantDetail name not null.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariantDetail> updateVariantDetail(@PathVariable("id") Long id, @RequestBody  VariantDetail variantDetail){
        //tim tat ca variantDetail theo variant id
        VariantDetail variantDetailOld = variantDetailService.findByVariantDetailId(id).get();
        if (variantDetailOld !=null){
            Iterable<VariantDetail> optionList = variantDetailService.findAllByVariantId(variantDetail.getVariant().getId());
            List<VariantDetail> allVariantDetailFind = new ArrayList<>();
            for (VariantDetail o : optionList) {
                allVariantDetailFind.add(o);
            }
            if (variantDetail.getVariantDetailName() != null) {
                for (int i = 0; i < allVariantDetailFind.size(); i++) {
                    try {
                        VariantDetail variantDetailDb = variantDetailService.findByVariantDetailNameAndVariantId(variantDetail.getVariantDetailName(), variantDetail.getVariant().getId()).get();
                        if (allVariantDetailFind.get(i).getVariantDetailName().equals(variantDetailDb.getVariantDetailName())) {
                            return new ResponseEntity("Update failed!!! Product is exist.", HttpStatus.BAD_REQUEST);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                variantDetailOld.setVariantDetailName(variantDetail.getVariantDetailName());
                variantDetailService.save(variantDetailOld);
                return new ResponseEntity<VariantDetail>(variantDetail, HttpStatus.OK);
            } else {
                return new ResponseEntity("Update failed!!! VariantDetail name not null.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Variant detail not found.", HttpStatus.NOT_FOUND);

    }
    @GetMapping
    public ResponseEntity<Iterable<VariantDetail>> findAllVariantDetail(){
        Iterable<VariantDetail> variantDetails = variantDetailService.findAllOption();
        if (variantDetails == null){
            return new ResponseEntity("Variant detail null", HttpStatus.OK);
        }
        return new ResponseEntity<Iterable<VariantDetail>>(variantDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantDetail> findVariantDetailById(@PathVariable("id") Long id){
        VariantDetail variantDetails = variantDetailService.findByVariantDetailId(id).get();
        if (variantDetails == null){
            return new ResponseEntity("Variant detail null", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<VariantDetail>(variantDetails, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariantDetailById(@PathVariable("id") Long id){
        VariantDetail variantDetails = variantDetailService.findByVariantDetailId(id).get();
        if (variantDetails == null){
            return new ResponseEntity("Not fount variant detail to delete",HttpStatus.NOT_FOUND);
        }
        variantDetailService.delete(id);
        return new ResponseEntity("Delete variant success", HttpStatus.OK);

    }

}


