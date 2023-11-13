package com.example.backend.core.admin.dto;

import com.example.backend.core.model.Staff;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherAdminDTO {
    private Long id;
    private String code;
    private String name;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String description;
    private BigDecimal conditionApply;
    private int status;
    private int idel;
    private String createName;
    private String updateName;
    private int voucherType;
    private BigDecimal reducedValue;
    private int quantity;
    private String startDateStr;
    private String endDateStr;

}
