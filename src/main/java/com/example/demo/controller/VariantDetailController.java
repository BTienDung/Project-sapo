package com.example.demo.controller;

import com.example.demo.model.VariantDetail;
import com.example.demo.service.DetailSevice;
import com.example.demo.service.VariantDetailService;
import com.example.demo.service.ProductService;
import com.example.demo.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/variantdetail")
public class VariantDetailController {
    @Autowired
    private VariantDetailService variantDetailService;
    @Autowired
    private VariantService variantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DetailSevice detailSevice;
    @PostMapping
    public ResponseEntity<VariantDetail> createOption(@RequestBody VariantDetail variantDetail, UriComponentsBuilder uriComponentsBuilder){

        //dem
        Long countOption = variantDetailService.countAllByVariantId(variantDetail.getVariant().getId());
        //tim tat ca variantDetail theo variant id
        Iterable<VariantDetail> optionList = variantDetailService.findAllByVariantId(variantDetail.getVariant().getId());

        List<VariantDetail> allVariantDetailFind = new ArrayList<>();
        for(VariantDetail o: optionList){
            allVariantDetailFind.add(o);
        }


        if(variantDetail.getVariantDetailName() != null) {
            if (countOption < 3) {
                for (int i = 0; i< allVariantDetailFind.size(); i++) {
                    try{
                        VariantDetail variantDetailDb = variantDetailService.findByVariantDetailNameAndVariantId(variantDetail.getVariantDetailName(), variantDetail.getVariant().getId()).get();
                        if (allVariantDetailFind.get(i).getVariantDetailName().equals(variantDetailDb.getVariantDetailName())) {
                            return new ResponseEntity("Create failed!!! Product is exist.", HttpStatus.FOUND);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                variantDetailService.save(variantDetail);
                countOption++;
//                HttpHeaders headers = new HttpHeaders();
//                headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(variantDetail.getId()).toUri());
                return new ResponseEntity<VariantDetail>(variantDetail, HttpStatus.CREATED);
            } else {
                return new ResponseEntity("Create failed!!! VariantDetail must be less than 3.", HttpStatus.FOUND);

            }
        }else{
            return new ResponseEntity("Create failed!!! VariantDetail name not null.", HttpStatus.FOUND);
        }

    }
}
