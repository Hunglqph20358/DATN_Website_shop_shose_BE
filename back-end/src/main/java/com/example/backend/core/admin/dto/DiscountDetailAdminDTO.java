package com.example.backend.core.admin.dto;

import com.example.backend.core.model.Product;
import com.example.backend.core.view.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DiscountDetailAdminDTO {
    private Long id;
    private Integer idProduct;
    private Integer idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private Integer status;
    private DiscountAdminDTO discountAdminDTO;
    private ProductDTO product;

}
