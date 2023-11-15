package com.example.backend.core.admin.dto;

import com.example.backend.core.view.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductAdminDTO {
    private Long id;
    private String code;
    private String name;
    private Instant createDate;
    private Instant updateDate;
    private String createName;
    private String updateName;
    private Long idBrand;
    private Long idCategory;
    private Long idMaterial;
    private Long idSole;
    private String description;
    private Integer status;
    private List<ImagesDTO> imagesDTOList;
    private Integer idel;
    private BrandDTO brandDTO;
    private CategoryDTO categoryDTO;
    private MaterialDTO materialDTO;
    private SoleDTO soleDTO;
    private BigDecimal listedPrice;
    private BigDecimal price;
    private Integer totalQuantity;
    private List<ProductDetailDTO> productDetailDTOList;
    private BigDecimal totalSold;

    public List<ProductDetailDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }
}
