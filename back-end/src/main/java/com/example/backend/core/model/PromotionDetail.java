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
public class PromotionDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_product_detail")
    private Integer id_product_detail;
    @Column(name = "id_promotion")
    private Integer id_promotion;
    @Column(name = "reduced_value")
    private BigDecimal reduced_value;
    @Column(name = "promotion_type")
    private Integer promotion_type;
    @Column(name = "status")
    private Integer status;


}
