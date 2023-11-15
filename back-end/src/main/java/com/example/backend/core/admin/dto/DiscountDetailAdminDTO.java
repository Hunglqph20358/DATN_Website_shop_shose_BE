package com.example.backend.core.admin.dto;

import com.example.backend.core.model.Product;
import com.example.backend.core.view.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DiscountDetailAdminDTO {
    private Long id;
    private String idProduct;
    private String idDiscount;
    private BigDecimal reducedValue;
    private String discountType;
    private int status;
    private DiscountAdminDTO discountAdminDTO;
    private ProductAdminDTO productDTO;
    private List<ProductAdminDTO> productDTOList;


}
