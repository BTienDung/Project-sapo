package com.example.demo.model;


import javax.persistence.*;

@Entity
@Table(name = "variant")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "variantname")
    private String variantName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product ;

    public Variant() {
    }

    public Variant(String variantName, Product product) {
        this.variantName = variantName;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
