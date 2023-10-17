package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PromotionDTO {
    private Long id;
    private Integer idProductDetail;

    private Integer idPromotion;

    private BigDecimal reducedValue;

    private Integer promotionType;

    private Integer status;

}
