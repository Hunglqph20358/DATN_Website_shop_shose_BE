package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderHistoryAdminDTO {
    private Long id;
    private Long idOrder;
    private Long idStaff;
    private Long idCustomer;
    private Integer status;
    private Instant createDate;
    private String note;
    private StaffDTO staffDTO;
    private CustomerAdminDTO customerAdminDTO;
}
