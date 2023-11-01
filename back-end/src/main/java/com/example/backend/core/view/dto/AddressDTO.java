package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {
    private Long id;
    private Long idCustomer;
    private Long idStaff;
    private Instant createDate;
    private String province;
    private String districtId;
    private String district;
    private String wardCode;
    private String wards;
    private String specificAddress;
}
