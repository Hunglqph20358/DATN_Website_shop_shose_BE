package com.example.backend.core.admin.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountAdminDTO {
    private Long id;
    private String code;
    private String name;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String description;
    private String status;
    private String idel;
    private String startDateStr;
    private String endDateStr;
}
