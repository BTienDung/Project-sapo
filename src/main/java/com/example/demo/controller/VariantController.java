package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/variant")
public class VariantController {
    @Autowired
    private VariantService variantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private VariantDetailService variantDetailService;


    @GetMapping
    public ResponseEntity<Iterable<Variant>> getAllVariant(){
        Iterable<Variant> variantList = variantService.findAllVariant();
        if(variantList == null){
            return new ResponseEntity<Iterable<Variant>>(HttpStatus.FOUND);
        }
        return new ResponseEntity<Iterable<Variant>>(variantList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Variant> findVariantId(@PathVariable("id")  Long id){
        Variant variant = variantService.findById(id).get();
        if (variant == null){
            return new ResponseEntity("Not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Variant>(variant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Variant> addVariant(@RequestBody Variant variant, UriComponentsBuilder uriComponentsBuilder){
        Iterable<Variant> iterableVariantOld = variantService.findAllByProductId(variant.getProduct().getId());
        List<Variant> listVariantOld = new ArrayList<>();
        for(Variant v: iterableVariantOld){
            listVariantOld.add(v);
        }

        if(variant.getVariantName() != null){
            for (int i=0; i< listVariantOld.size(); i++){
                if (listVariantOld.get(i).getVariantName().equalsIgnoreCase(variant.getVariantName())){
                    return new ResponseEntity<Variant>(HttpStatus.BAD_REQUEST);
                }
            }
            Variant variantNew = new Variant();
            Product product = productService.findById(variant.getProduct().getId()).get();
            variantNew.setProduct(product);
            variantNew.setVariantName(variant.getVariantName());
            variantService.save(variantNew);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(variant.getId()).toUri());
            return new ResponseEntity<Variant>(variant, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<Variant>(HttpStatus.FOUND);
        }

    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateVariant(@PathVariable("id") Long id, @RequestBody Variant variant, UriComponentsBuilder uriComponentsBuilder){
//
//        Variant variantNew = variantService.findById(id).get();
//        if (variantNew == null){
//            return new ResponseEntity("Not found variant to update.",HttpStatus.NOT_FOUND);
//        }
//        variantNew.setVariantName(variant.getVariantName());
//        variantService.save(variantNew);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(variant.getId()).toUri());
//        return new ResponseEntity("Update variant success.",HttpStatus.OK);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Variant> updateVariant(@PathVariable("id") Long id, @RequestBody Variant variant, UriComponentsBuilder uriComponentsBuilder){


        Variant variantNew = variantService.findById(id).get();
        Iterable<Variant> iterableVariantOld = variantService.findAllByProductId(variant.getProduct().getId());
        List<Variant> listVariantOld = new ArrayList<>();
        for(Variant v: iterableVariantOld){
            listVariantOld.add(v);
        }
        if (variantNew == null){

            return new ResponseEntity<Variant>(HttpStatus.BAD_REQUEST);
        }
        for (int i=0; i< listVariantOld.size(); i++){
            if (listVariantOld.get(i).getVariantName().equalsIgnoreCase(variant.getVariantName())){
                return new ResponseEntity<Variant>(HttpStatus.BAD_REQUEST);
            }
        }
        variantNew.setVariantName(variant.getVariantName());
        variantService.save(variantNew);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(variant.getId()).toUri());
        return new ResponseEntity<Variant>(variant,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable("id") Long id){
        Variant variantNew = variantService.findById(id).get();
        List<VariantDetail> variantDetailList =  variantDetailService.findAllByVariantId(id);
        if (variantNew == null){
            return new ResponseEntity("Not fount variant to delete",HttpStatus.NOT_FOUND);
        }
        variantService.delete(id);
        for (int i = 0; i< variantDetailList.size(); i++){
            variantDetailService.delete(variantDetailList.get(i).getId());
        }
        return new ResponseEntity("Delete variant success", HttpStatus.OK);

    }
}
