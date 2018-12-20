package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "show_on_homepage")
    private Boolean showOnHomepage;

    @Lob
    @Column(name = "prodct_image")
    private byte[] prodct_image;

    @Column(name = "prodct_image_content_type")
    private String prodct_imageContentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Product createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Product updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public Product isAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean isShowOnHomepage() {
        return showOnHomepage;
    }

    public Product showOnHomepage(Boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
        return this;
    }

    public void setShowOnHomepage(Boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
    }

    public byte[] getProdct_image() {
        return prodct_image;
    }

    public Product prodct_image(byte[] prodct_image) {
        this.prodct_image = prodct_image;
        return this;
    }

    public void setProdct_image(byte[] prodct_image) {
        this.prodct_image = prodct_image;
    }

    public String getProdct_imageContentType() {
        return prodct_imageContentType;
    }

    public Product prodct_imageContentType(String prodct_imageContentType) {
        this.prodct_imageContentType = prodct_imageContentType;
        return this;
    }

    public void setProdct_imageContentType(String prodct_imageContentType) {
        this.prodct_imageContentType = prodct_imageContentType;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Product restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", isAvailable='" + isIsAvailable() + "'" +
            ", showOnHomepage='" + isShowOnHomepage() + "'" +
            ", prodct_image='" + getProdct_image() + "'" +
            ", prodct_imageContentType='" + getProdct_imageContentType() + "'" +
            "}";
    }
}
