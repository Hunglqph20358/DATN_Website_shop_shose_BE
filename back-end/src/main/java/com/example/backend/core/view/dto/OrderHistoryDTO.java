package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderHistoryDTO implements Serializable {
    private Long id;
    private Long idOrder;
    private Long idStaff;
    private Long idCustomer;
    private Integer status;
    private Instant createDate;
    private String note;
}
