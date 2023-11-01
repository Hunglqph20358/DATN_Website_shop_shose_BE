package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "`voucher`")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Voucher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "condition_apply")
    private BigDecimal conditionApply;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "createName")
    private String createName;
    @Column(name = "voucher_type")
    private Integer voucherType;
    @Column(name = "reduced_value")
    private BigDecimal reducedValue;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idel")
    private Integer isdel;

}
