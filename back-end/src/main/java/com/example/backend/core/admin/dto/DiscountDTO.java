package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DiscountDTO {
    private Long id;
    private Integer idProduct;
    private Integer idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private Integer status;
//    private String name;
//    private Instant startDate;
//    private Instant endDate;
//    private String description;
//    private Integer status;
//    private Integer idel;

}
