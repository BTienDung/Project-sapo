package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "variantdetail")
public class VariantDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(name = "variantdetailname")
    private String variantDetailName;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    public VariantDetail() {
    }

    public VariantDetail(@NotBlank String variantDetailName, Variant variant) {
        this.variantDetailName = variantDetailName;
        this.variant = variant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantDetailName() {
        return variantDetailName;
    }

    public void setVariantDetailName(String variantDetailName) {
        this.variantDetailName = variantDetailName;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}
