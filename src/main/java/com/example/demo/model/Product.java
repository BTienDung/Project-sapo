package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(name = "productName")
    private String productName;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "createdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "modifieddate")
    private Date modifiedDate;
    private String image;
    private boolean status;

    public Product() {
    }

    public Product(String productName, Category category, Date createDate, Date modifiedDate, String image, boolean status) {
        this.productName = productName;
        this.category = category;
        this.createDate = createDate;
        this.image = image;
        this.status = status;
    }

    public Product(String productName, Category category,  Date createDate) {
        this.productName = productName;
        this.category = category;

        this.createDate = createDate;
        this.status = true;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
