package com.example.demo.model;

import javax.persistence.*;

@Entity
public class DetailProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @ManyToOne()
//    @JoinColumn(name = "category_id")
//    private Category category;

    @ManyToOne
    @JoinColumn(name = "variants_id")
    private Variant variant;

    @ManyToOne
    @JoinColumn(name = "VariantDetail")
    private VariantDetail variantDetail;



//    @Column(name = "createdate")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date createDate;
//
//    @Column(name = "modifieddate")
//    private Date modifiedDate;
//
//
//    private ArrayList<String> image;
//
//    private boolean status;
//    public DetailProduct() {
//    }

//    public DetailProduct(Product product, Category category, Variant variant, VariantDetail variantDetail, Date createDate, Date modifiedDate, ArrayList<String> image, boolean status) {
//        this.product = product;
//        this.category = category;
//        this.variant = variant;
//        this.variantDetail = variantDetail;
//        this.createDate = createDate;
//        this.modifiedDate = modifiedDate;
//        this.image = image;
//        this.status = status;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public VariantDetail getVariantDetail() {
        return variantDetail;
    }

    public void setVariantDetail(VariantDetail variantDetail) {
        this.variantDetail = variantDetail;
    }

//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(Date modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    public ArrayList<String> getImage() {
//        return image;
//    }
//
//    public void setImage(ArrayList<String> image) {
//        this.image = image;
//    }
}
