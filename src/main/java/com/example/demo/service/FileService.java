package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface FileService {
    void saveImage(MultipartFile multipartFile);
    File callFileImage(Optional<Product> product) throws FileNotFoundException;
    void deleteImage(Product product) throws IOException;
}
