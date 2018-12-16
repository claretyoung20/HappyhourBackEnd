package com.happyhour.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HappyOrder.
 */
@Entity
@Table(name = "happy_order")
public class HappyOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_total")
    private Double baseTotal;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @ManyToOne
    @JsonIgnoreProperties("")
    private OrderStatus orderStatus;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Coupon coupon;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Staff staff;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private BookTable bookTable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public HappyOrder baseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
        return this;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public HappyOrder totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public HappyOrder dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public HappyOrder dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public HappyOrder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public HappyOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public HappyOrder coupon(Coupon coupon) {
        this.coupon = coupon;
        return this;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public HappyOrder restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Staff getStaff() {
        return staff;
    }

    public HappyOrder staff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public BookTable getBookTable() {
        return bookTable;
    }

    public HappyOrder bookTable(BookTable bookTable) {
        this.bookTable = bookTable;
        return this;
    }

    public void setBookTable(BookTable bookTable) {
        this.bookTable = bookTable;
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
        HappyOrder happyOrder = (HappyOrder) o;
        if (happyOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), happyOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HappyOrder{" +
            "id=" + getId() +
            ", baseTotal=" + getBaseTotal() +
            ", totalPrice=" + getTotalPrice() +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
