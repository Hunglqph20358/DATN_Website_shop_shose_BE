package com.example.backend.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_order")
    private Long id_order;
    @Column(name = "id_product_detail")
    private Long id_product_detail;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "code_promotion")
    private String code_promotion;
    @Column(name = "status")
    private Integer status;
}
