package com.example.backend.core.admin.dto;

import com.example.backend.core.view.dto.CustomerDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderAdminDTO {
    private Long id;
    private String code;
    private Long idCustomer;
    private Long idStaff;
    private String codeVoucher;
    private Instant createDate;
    private Instant paymentDate;
    private Date deliveryDate;
    private Date receivedDate;
    private String addressReceived;
    private String shipperPhone;
    private String receiverPhone;
    private String receiver;
    private BigDecimal shipPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private Integer paymentType;
    private String description;
    private Integer status;
    private Integer missedOrder;
    private Integer statusPayment;
    private CustomerAdminDTO customerAdminDTO;
    private String dateFrom;
    private String dateTo;
    private String note;
}
