package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public SaleOrder dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public SaleOrder dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
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
            "}";
    }
}
