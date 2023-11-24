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
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherFreeShipDTO {
    private Long id;

    private String code;


    private String name;


    private Long idCustomer;


    private Date createDate;


    private Date startDate;


    private Date endDate;


    private BigDecimal conditions;


    private String createName;


    private BigDecimal reducedValue;


    private String description;


    private int status;


    private int idel;


    private Integer quantity;


    private Integer limitCustomer;
    private Integer useVoucher;

    private int allow;
    private CustomerAdminDTO customerAdminDTO;
    private List<CustomerAdminDTO> customerAdminDTOList;

}
