package com.example.demo.controller;

import com.example.demo.model.Details;
import com.example.demo.model.Product;
import com.example.demo.repository.DetailsRepositoryImpl;
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

import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;


    @Autowired
    private DetailsRepositoryImpl detailsRepository;


    @GetMapping()
    public ResponseEntity<List<Details>> showAllProduct(){
        List<Object[]> objects = detailsRepository.getAllDetails();
        if (objects == null){
            return new ResponseEntity<List<Details>>(HttpStatus.NOT_FOUND);
        }
            List<Details> detailsList = new ArrayList<>();
            for (Object[] o: objects){
                Details details = new Details();
                detailsList.add(details);
                details.setId((o[0].toString()));
                details.setProductName((String) o[1]);
                details.setCategoryName((String) o[2]);
                details.setVariantName((String) o[3]);
                details.setVariantDetailName((String) o[4]);
                if (o[5] != null){
                    details.setImage( o[5].toString());
                }
                details.setCreateDate((Date) o[6]);
                if (o[7] != null){
                    details.setModifiedDate((Date) o[7]);
                }
            }
            return  new ResponseEntity<List<Details>>(detailsList, HttpStatus.OK);
    }
    //tim product nguyen thuy
    @GetMapping("/original")
    public ResponseEntity<List<Product>> showAllProductOriginal(){
        List<Product> products = productService.findAllProductByStatus();
        if (products == null){
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getOneProductById(@PathVariable("id") Long id) {
//        Product productFind = productService.findById(id).get();
//        if (productFind !=null){
//            return new ResponseEntity<Product>(productFind, HttpStatus.OK);
//        }
//        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Details>> getProductById(@PathVariable("id") Long id){
        List<Object[]> objects = detailsRepository.getDetailsById(id);
        if (objects.size() == 0){
            return new ResponseEntity<List<Details>>(HttpStatus.NOT_FOUND);
        }
        List<Details> detailsList = new ArrayList<>();
        for (Object[] o: objects){
            Details details = new Details();
            detailsList.add(details);
            details.setId((o[0].toString()));
            details.setProductName((String) o[1]);
            details.setCategoryName((String) o[2]);
            details.setVariantName((String) o[3]);
            details.setVariantDetailName((String) o[4]);
            if (o[5] != null){
                details.setImage( o[5].toString());
            }
            details.setCreateDate((Date) o[6]);
            details.setModifiedDate((Date) o[7]);
        }
        return  new ResponseEntity<List<Details>>(detailsList, HttpStatus.OK);
    }

//    @PostMapping("/addProduct")
//    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
//
//    }

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
        List<Product> productList = productService.findAllProductByStatus();
        if (productList!=null){
            for (Product p: productList){
                if (p.getProductName().equalsIgnoreCase(product.getProductName())){
                    return new ResponseEntity("Product exist!!!",HttpStatus.BAD_REQUEST);
                }
            }
        }
        if (product.getCategory() == null){
            return new ResponseEntity("Category not null!!!",HttpStatus.BAD_REQUEST);
        }
        Product productNew = new Product();
        productNew.setProductName(product.getProductName());
        productNew.setCategory(product.getCategory());
        productNew.setStatus(true);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        productNew.setCreateDate(date);
        productService.saveProduct(productNew);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Product>(productNew,HttpStatus.CREATED);

    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
//        List<Product> productList = productService.findAllProductByStatus();
//        Optional<Product> productOptional = productService.findById(id);
//        Product productNew = productOptional.get();
//        if(productNew == null){
//            return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
//        }
//
//        for(Product p: productList){
//            if (((p.getProductName().equals( product.getProductName()))  && (p.getCategory().getId() == product.getCategory().getId())) ){
//                return new ResponseEntity<Product>(HttpStatus.FOUND);
//            }
//        }
//        productNew.setProductName(product.getProductName());
//        productNew.setCategory(product.getCategory());
//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//        productNew.setModifiedDate(date);
//        productService.saveProduct(productNew);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(product.getId()).toUri());
//        return new ResponseEntity<Product>(productNew,HttpStatus.OK);
//    }

    //them yeu cau neu ma category mà trống thì lưu category cũ

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
        List<Product> productList = productService.findAllProductByStatus();
        Optional<Product> productOptional = productService.findById(id);
        Product productNew = productOptional.get();

        if(productNew == null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        for(Product p: productList){
            if (((p.getProductName().equals( product.getProductName()))  && (p.getCategory().getId() == product.getCategory().getId())) ){
                return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
            }
        }

        if (product.getCategory() == null){
            productNew.setProductName(product.getProductName());
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            productNew.setModifiedDate(date);
            productService.saveProduct(productNew);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(product.getId()).toUri());
            return new ResponseEntity<Product>(productNew,HttpStatus.OK);
        }
        productNew.setProductName(product.getProductName());
        productNew.setCategory(product.getCategory());
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        productNew.setModifiedDate(date);
        productService.saveProduct(productNew);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Product>(productNew,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        Optional<Product> productOptional = productService.findById(id);
        Product productNew = productOptional.get();
        if(productNew == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        productNew.setStatus(false);
        productService.saveProduct(productNew);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Product> saveImage(@PathVariable("id") Long id, @RequestPart("image") MultipartFile file) throws IOException {
        Optional<Product> productOptional = productService.findById(id);
        Product product = productOptional.get();
        String fileName = file.getOriginalFilename();
        if (product !=null){
            if (product.getImage() !=null){
                fileService.deleteImage(product);
                product.setImage(fileName);
                fileService.saveImage(file);

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                product.setModifiedDate(date);

                productService.saveProduct(product);
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            }
            else {
                product.setImage(fileName);
                fileService.saveImage(file);

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                product.setModifiedDate(date);

                productService.saveProduct(product);
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            }
        }else
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

}
