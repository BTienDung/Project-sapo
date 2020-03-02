package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.util.ResourceUtils.getFile;

@Service
@PropertySource("classpath:application.properties")
public class FileServiceImpl implements FileService {
    @Value(value = "E:/SpringBoot/DemoProjectSapo/src/main/webapp/images/")
    private String imageProduct;
    @Override

    //luu file len server
    public void saveImage(MultipartFile multipartFile) {
        File uploadedFile = new File(imageProduct, multipartFile.getOriginalFilename());
        try {
            uploadedFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //ham goi file de xoa
    @Override
    public File callFileImage(Optional<Product> product) throws FileNotFoundException {
        String pathFile = imageProduct + product.get().getImage();
        File fileName = getFile(pathFile);
        return fileName;
    }

    @Override
    public void deleteImage(Product product) throws IOException {
        ArrayList<String> productImage = product.getImage();
        for (String pathFile: productImage){
            String imgFile = imageProduct+ "/" + product.getImage();
            try {
                File avatar = FileUtils.getFile(pathFile);
                FileUtils.forceDelete(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
