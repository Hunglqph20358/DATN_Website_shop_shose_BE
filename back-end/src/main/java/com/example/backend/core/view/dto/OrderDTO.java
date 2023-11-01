package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private Long id;
    private String code;
    private Long idCustomer;
    private Long idStaff;
    private Instant createDate;
    private Instant paymentDate;
    private Instant deliveryDate;
    private Instant receivedDate;
    private String shipperPhone;
    private String receiverPhone;
    private String receiver;
    private BigDecimal shipPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private Integer paymentType;
    private String description;
    private Integer status;

    private CustomerDTO customerDTO;

}
