package com.example.backend.core.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MauSacDTO {

    private Long id;
    @NotBlank(message = "Vui lòng nhập mã")
    private String ma;
    @NotBlank(message = "Vui lòng nhập tên")
    private String ten;
    private Integer trangThai;
}
