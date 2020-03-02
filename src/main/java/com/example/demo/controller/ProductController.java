package com.example.demo.controller;

import com.example.demo.model.DetailProduct;
import com.example.demo.model.Details;
import com.example.demo.model.Product;
import com.example.demo.repository.DetailsRepositoryImpl;
import com.example.demo.service.DetailSevice;
import com.example.demo.service.FileService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
//    @Autowired
//    private VariantDetailService optionService;
//    @Autowired
//    private VarientService varientService;
    @Autowired
    private DetailSevice detailSevice;
    @Autowired
    private FileService fileService;


    @Autowired
    private DetailsRepositoryImpl detailsRepository;

    @GetMapping()
    public ResponseEntity<List<Details>> showAllProduct(){
        List<Details> productList = detailsRepository.getAllDetails();
        //List<Product> productList = productService.findAllProductByStatus();
        if (productList == null){
            return new ResponseEntity<List<Details>>(HttpStatus.BAD_REQUEST);
        }
            return  new ResponseEntity<List<Details>>(productList, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
//        List<Product> productList = productService.findAllProductByStatus();
//        if (productList!=null){
//            for(Product p: productList){
//                if (((p.getNameProduct().equals( product.getNameProduct()))  && (p.getCategory().getId() == product.getCategory().getId())) ){
//                    return new ResponseEntity<Product>(HttpStatus.FOUND);
//                }
//            }
//        }
//        VariantDetail option = new VariantDetail();
//        //option.setName(product.getNameOption());
//        //option.setVariant(product.getVariant());
//        optionService.save(option);

            Product productNew = new Product();
            productNew.setProductName(product.getProductName());
            productNew.setCategory(product.getCategory());
            productNew.setStatus(true);
            //productNew.setVariant(product.getVariant());
            //productNew.setNameOption(product.getNameOption());
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();

            productNew.setCreateDate(date);
            productService.saveProduct(productNew);

            //luu vao bang chi tiet san pham
        DetailProduct detailProduct = new DetailProduct();
        detailProduct.setProduct(productNew);
        detailSevice.save(detailProduct);


            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
            return new ResponseEntity<Product>(HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
        List<Product> productList = productService.findAllProductByStatus();
        Optional<Product> productOptional = productService.findById(id);
        Product productNew = productOptional.get();
        if(productNew == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        for(Product p: productList){
            if (((p.getProductName().equals( product.getProductName()))  && (p.getCategory().getId() == product.getCategory().getId())) ){
                return new ResponseEntity<Void>(HttpStatus.FOUND);
            }
        }
        productNew.setProductName(product.getProductName());
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        productNew.setModifiedDate(date);
        productService.saveProduct(productNew);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        Optional<Product> productOptional = productService.findById(id);
        Product productNew = productOptional.get();
        if(productNew == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        productNew.setStatus(false);
        productService.saveProduct(productNew);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/mutilpartFile/{id}")
    public ResponseEntity<Product> updateUser(@PathVariable("id") Long id, @RequestPart("image") MultipartFile[] file) throws IOException {
        // Khoi tao mang ten
        ArrayList<String> fileName = new ArrayList<>();
        //khoi tao mang luu file
        ArrayList<File> saveFiles = new ArrayList<>();

        Optional<Product> productOptional = productService.findById(id);
        Product product = productOptional.get();
        if (product != null){
            if (product.getImage() != null){
                fileService.deleteImage(product);
            }else
                //lưu tên ảnh vao db
                for (MultipartFile multipartFile: file){
                    fileName.add(multipartFile.getOriginalFilename());
                }
            // lưu file ảnh lên serve
            for (MultipartFile multipartFile: file){
                fileService.saveImage(multipartFile);
            }
            product.setImage(fileName);
            productService.saveProduct(product);
            return new ResponseEntity<Product>(product,HttpStatus.OK);
        }else {
            for (MultipartFile multipartFile: file){
                fileName.add(multipartFile.getOriginalFilename());
            }
            for (MultipartFile multipartFile: file){
                fileService.saveImage(multipartFile);
            }
            product.setImage(fileName);
            productService.saveProduct(product);
            return new ResponseEntity<Product>(product,HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> showAllProduct(@PathVariable("id")Long id){

        Product productFind = productService.findById(id).get();
        if (productFind == null){
            return new ResponseEntity("Not found product",HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<Product>( productFind, HttpStatus.OK);
    }

}
