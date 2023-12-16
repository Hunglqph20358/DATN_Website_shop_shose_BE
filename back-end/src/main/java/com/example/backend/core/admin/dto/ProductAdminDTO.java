package com.example.backend.core.admin.dto;
import lombok.*;

import com.example.backend.core.commons.ExportDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private StaffAdminDTO staffAdminDTO;
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
    private Integer totalBestSeller;

    public ProductAdminDTO(Long id, String code, String name,BigDecimal price,Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.totalQuantity= totalQuantity;
    }

//    public List<ProductDetailDTO> getProductDetailDTOList() {
//        return productDetailDTOList;
//    }
//
//    public void setProductDetailDTOList(List<ProductDetailDTO> productDetailDTOList) {
//        this.productDetailDTOList = productDetailDTOList;
//    }
}
