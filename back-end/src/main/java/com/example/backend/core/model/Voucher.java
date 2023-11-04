package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "create_date")
    private Date createDate;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "conditions")
    private BigDecimal conditionApply;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "idel")
    private int idel;
    @Basic
    @Column(name = "create_name")
    private String createName;
    @Basic
    @Column(name = "update_name")
    private String updateName;
    @Basic
    @Column(name = "voucher_type")
    private int voucherType;
    @Basic
    @Column(name = "reduced_value")
    private BigDecimal reducedValue;
    @Basic
    @Column(name = "quantity")
    private int quantity;
}
