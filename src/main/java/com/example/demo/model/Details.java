package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name = "details")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllProduct",
                procedureName = "getAllProduct",
                resultClasses = Details.class)
})

public class Details {
    @Id
    private Long id;
    private String productName;
    private String categoryName;
    private String  variantName;
    private String variantDetailName;
    private Date modifiedDate;

    public Details() {
    }

    public Details(Long id, String productName, String categoryName, String variantName, String variantDetailName, Date modifiedDate) {
        this.id = id;
        this.productName = productName;
        this.categoryName = categoryName;
        this.variantName = variantName;
        this.variantDetailName = variantDetailName;
        this.modifiedDate = modifiedDate;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getVariantDetailName() {
        return variantDetailName;
    }

    public void setVariantDetailName(String variantDetailName) {
        this.variantDetailName = variantDetailName;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
