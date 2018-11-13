package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SaleOrder.
 */
@Entity
@Table(name = "sale_order")
public class SaleOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "original_price")
    private Double originalPrice;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("")
    private HappyOrder happyOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public SaleOrder basePrice(Double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public SaleOrder dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public SaleOrder dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public SaleOrder discountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public SaleOrder originalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public SaleOrder product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public HappyOrder getHappyOrder() {
        return happyOrder;
    }

    public SaleOrder happyOrder(HappyOrder happyOrder) {
        this.happyOrder = happyOrder;
        return this;
    }

    public void setHappyOrder(HappyOrder happyOrder) {
        this.happyOrder = happyOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SaleOrder saleOrder = (SaleOrder) o;
        if (saleOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleOrder{" +
            "id=" + getId() +
            ", basePrice=" + getBasePrice() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", originalPrice=" + getOriginalPrice() +
            "}";
    }
}
