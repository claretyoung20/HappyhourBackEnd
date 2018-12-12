package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BookTable.
 */
@Entity
@Table(name = "book_table")
public class BookTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_avaliable")
    private Boolean isAvaliable;

    @Column(name = "price")
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Restaurant restaurant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private TableType tableType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvaliable() {
        return isAvaliable;
    }

    public BookTable isAvaliable(Boolean isAvaliable) {
        this.isAvaliable = isAvaliable;
        return this;
    }

    public void setIsAvaliable(Boolean isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public Double getPrice() {
        return price;
    }

    public BookTable price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BookTable imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public BookTable restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public TableType getTableType() {
        return tableType;
    }

    public BookTable tableType(TableType tableType) {
        this.tableType = tableType;
        return this;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
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
        BookTable bookTable = (BookTable) o;
        if (bookTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookTable{" +
            "id=" + getId() +
            ", isAvaliable='" + isIsAvaliable() + "'" +
            ", price=" + getPrice() +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
