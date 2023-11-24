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
    private List<ImagesAdminDTO> imagesDTOList;
    private Integer idel;
    private BrandDTO brandDTO;
    private CategoryDTO categoryDTO;
    private MaterialDTO materialDTO;
    private SoleDTO soleDTO;
    private BigDecimal price;
    private Integer totalQuantity;
    private List<ProductDetailDTO> productDetailDTOList;
    private BigDecimal totalSold;
    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;

    public ProductAdminDTO(Long id, String code, String name,BigDecimal price,Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.totalQuantity= totalQuantity;
    }

    public List<ProductDetailDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }
}
