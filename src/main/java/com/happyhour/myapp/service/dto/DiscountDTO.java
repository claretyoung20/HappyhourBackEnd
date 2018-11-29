package com.happyhour.myapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Discount entity.
 */
public class DiscountDTO implements Serializable {

    private Long id;

    private Double amount;

    private LocalDate endDate;

    private LocalDate startDate;

    @NotNull
    private Integer percentage;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscountDTO discountDTO = (DiscountDTO) o;
        if (discountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", endDate='" + getEndDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", percentage=" + getPercentage() +
            ", product=" + getProductId() +
            "}";
    }
}
