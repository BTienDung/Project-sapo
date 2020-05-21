package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/category")
    public ResponseEntity<List<Category>> findAllCategory(){
        List<Category> listCategory = categoryService.findAllCategory();
        if (listCategory.isEmpty()){
            return new ResponseEntity<List<Category>>(HttpStatus.OK);
        }
        return new ResponseEntity<List<Category>>(listCategory,HttpStatus.OK);
    }
    @PostMapping("/api/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, UriComponentsBuilder uriComponentsBuilder){
        List<Category> listCategory = categoryService.findAllCategory();
        if (listCategory.size() < 3){
            if(category.getCategoryName() != null){
                for (Category c: listCategory){
                    if (c.getCategoryName().equalsIgnoreCase(category.getCategoryName())){
                        return new ResponseEntity("Category exist!!!", HttpStatus.BAD_REQUEST);
                    }
                }
                categoryService.saveCategory(category);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(uriComponentsBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri());
                return new ResponseEntity<Category>(category, HttpStatus.CREATED);
            }else {
                return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Category can't more than 3.",HttpStatus.BAD_REQUEST);

    }
    @PutMapping("/api/category/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") Long id, Category category){
        Optional<Category> categoryOption = categoryService.findById(id);
        Category categoryNew = categoryOption.get();
        if (categoryNew != null){
            categoryNew.setCategoryName(category.getCategoryName());
            categoryService.saveCategory(categoryNew);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/api/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id")Long id){
        Optional<Category> category = categoryService.findById(id);
        Category categoryFind = category.get();
        if(categoryFind !=null ){
            categoryService.deleteCategory(categoryFind.getId());
            System.out.println("Delete category success!");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/api/category/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable("id")Long id){

        Optional<Category> category = categoryService.findById(id);
        Category categoryFind = category.get();
        if(categoryFind !=null ){
            return new ResponseEntity<Category>(categoryFind,HttpStatus.OK);
        }
        return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);

    }
}
