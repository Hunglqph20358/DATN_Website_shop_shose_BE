package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountAdminDTO {
    private Long id;
    private String code;
    private String name;
    private Instant createDate;
    private Instant startDate;
    private Instant endDate;
    private String description;
    private Integer status;
    private Integer idel;
}
