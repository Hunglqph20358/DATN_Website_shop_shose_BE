package com.example.backend.core.admin.dto;

import com.example.backend.core.model.Staff;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherAdminDTO {
    private long id;
    private String code;
    private String name;
    private String idCustomer;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private BigDecimal conditions;
    private String createName;
    private int voucherType;
    private BigDecimal reducedValue;
    private String description;
    private int status;
    private String idel;
    private int quantity;
    private BigDecimal maxReduced;//gtri giảm tối đa
    private int limitCustomer;//giới hạn số lần sd của mỗi kk
    private int allow;//cho phép sd cùng km hay ko
    private int useVoucher;
    private CustomerAdminDTO customerAdminDTO;
    private List<CustomerAdminDTO> customerAdminDTOList;


}
