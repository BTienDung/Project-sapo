package com.example.demo.model;
import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name = "details")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllProduct",
                procedureName = "getAllProduct",

                resultClasses = Details.class),
        @NamedStoredProcedureQuery(name = "getProductById",
                procedureName = "getProductById",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = Long.class),

                },
                resultClasses = Details.class)



})

public class Details {
    @Id
    private String id;
    private String productName;
    private String categoryName;
    private String  variantName;
    private String variantDetailName;
    private Date createDate;
    private Date modifiedDate;
    private String image;

    public Details() {
    }

    public Details(String id, String productName, String categoryName, String variantName, String variantDetailName, Date createDate, Date modifiedDate, String image) {
        this.id = id;
        this.productName = productName;
        this.categoryName = categoryName;
        this.variantName = variantName;
        this.variantDetailName = variantDetailName;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
