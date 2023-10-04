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
    private Long idProduct;
    @Column(name = "id_color")
    private Long idColor;
    @Column(name = "id_brand")
    private Long idBrand;
    @Column(name = "id_category")
    private Long idCategory;
    @Column(name = "id_size")
    private Long idSize;
    @Column(name = "id_material")
    private Long idMaterial;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "listed_price")
    private BigDecimal listedPrice;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "update_date")
    private Instant updateDate;
    @Column(name = "shoe_collar")
    private String shoeCollar;
    @Column(name = "id_sole")
    private Long idSole;
}
