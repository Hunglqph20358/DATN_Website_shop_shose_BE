package com.example.backend.core.view.dto;


import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductDetailDTO {
    private Long id;
    private Long idProduct;
    private Long idColor;
    private Long idSize;
    private Integer quantity;
    private BigDecimal listedPrice;
    private BigDecimal price;
    private Instant createDate;
    private Instant updateDate;
    private Integer shoeCollar;


    public ProductDetailDTO() {
    }

    public ProductDetailDTO(Long id, Long idProduct, Long idColor, Long idSize, Integer quantity, BigDecimal listedPrice, BigDecimal price, Instant createDate, Instant updateDate, Integer shoeCollar) {
        this.id = id;
        this.idProduct = idProduct;
        this.idColor = idColor;
        this.idSize = idSize;
        this.quantity = quantity;
        this.listedPrice = listedPrice;
        this.price = price;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.shoeCollar = shoeCollar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public Long getIdSize() {
        return idSize;
    }

    public void setIdSize(Long idSize) {
        this.idSize = idSize;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(BigDecimal listedPrice) {
        this.listedPrice = listedPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getShoeCollar() {
        return shoeCollar;
    }

    public void setShoeCollar(Integer shoeCollar) {
        this.shoeCollar = shoeCollar;
    }
}
