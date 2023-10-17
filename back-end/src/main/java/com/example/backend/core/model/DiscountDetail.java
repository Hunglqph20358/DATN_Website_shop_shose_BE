package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promotion_detail")
public class DiscountDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_product")
    private Integer idProduct;
    @Column(name = "id_discount")
    private Integer idDiscount;
    @Column(name = "reduced_value")
    private BigDecimal reducedValue;
    @Column(name = "discount_type")
    private Integer discountType;
    @Column(name = "status")
    private Integer status;


}
