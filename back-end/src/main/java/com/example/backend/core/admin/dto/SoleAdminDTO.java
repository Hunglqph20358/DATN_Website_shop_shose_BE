package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoleAdminDTO {
    private Long id;
    private String soleHeight;
    private String soleMaterial;
    private Instant createDate;
    private Instant updateDate;
    private String description;
    private Integer status;
}
