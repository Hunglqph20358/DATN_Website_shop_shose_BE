package com.example.backend.core.admin.dto;
import com.example.backend.core.commons.ExportDTO;
import com.example.backend.core.view.dto.ProductDetailDTO;
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
public class ProductAdminDTO extends ExportDTO {

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
    private BigDecimal price;
    private Long idSole;
    private String description;
    private Integer status;
    private List<ImagesAdminDTO> imagesDTOList;
    private Integer idel;
    private StaffDTO staffDTO;
    private Integer totalQuantity;
    private List<ProductDetailAdminDTO> productDetailDTOList;
    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;
    private ProductDetailAdminDTO productDetailAdminDTO;
    private String brandName;
    private String categoryName;
    private String materialName;
    private String soleHeight;

    private String imageNameImport;

    public ProductAdminDTO(Long id, String code, String name,BigDecimal price,Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.totalQuantity= totalQuantity;
    }

    public List<ProductDetailAdminDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailAdminDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }
}
