package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private Long id;
    private Long idOrder;
    private Long idProductDetail;
    private Integer quantity;
    private BigDecimal price;
    private String codePromotion;
    private Integer status;

    private ProductDetailDTO productDetailDTO;
}