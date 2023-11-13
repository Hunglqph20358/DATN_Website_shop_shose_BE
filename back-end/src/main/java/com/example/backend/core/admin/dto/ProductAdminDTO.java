package com.example.backend.core.admin.dto;

import com.example.backend.core.view.dto.*;
import jakarta.persistence.Column;
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
    private String description;
    private Integer status;
    private Integer idel;
    private Long idSole;

//    private BigDecimal listedPrice;
//    private BigDecimal price;
    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;
}
