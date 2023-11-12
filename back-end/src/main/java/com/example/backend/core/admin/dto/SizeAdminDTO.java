package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SizeAdminDTO {
    private Long id;
    private String sizeNumber;
    private Instant createDate;
    private Integer status;
}
