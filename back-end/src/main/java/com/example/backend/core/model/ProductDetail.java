package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_detail")
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_product")
    private Long id_product;
    @Column(name = "id_color")
    private Long id_color;
    @Column(name = "id_brand")
    private Long id_brand;
    @Column(name = "id_category")
    private Long id_category;
    @Column(name = "id_size")
    private Long id_size;
    @Column(name = "id_material")
    private Long id_material;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "listed_price")
    private BigDecimal listed_price;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "create_date")
    private Instant create_date;
    @Column(name = "update_date")
    private Instant update_date;
    @Column(name = "shoe_collar")
    private String shoe_collar;
    @Column(name = "id_sole")
    private Long id_sole;
}
