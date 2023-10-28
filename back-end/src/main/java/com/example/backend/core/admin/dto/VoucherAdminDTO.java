package com.example.backend.core.admin.dto;

import com.example.backend.core.model.Staff;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherAdminDTO {
    private Long id;
    private String code;
    private Instant createDate;
    private Instant startDate;
    private Instant endDate;
    private String description;
    private int status;
    private int idel;
    private Instant updateDate;
    private String createName;
    private String updateName;
    private int voucherType;
    private BigDecimal reducedValue;

    public VoucherAdminDTO(Long id, String code) {
        this.id = id;
        this.code = code;
    }
}
