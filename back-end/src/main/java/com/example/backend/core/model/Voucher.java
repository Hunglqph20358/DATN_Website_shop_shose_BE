package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    @Column(name = "create_date")
    private Instant createDate;
    @Basic
    @Column(name = "start_date")
    private Instant startDate;
    @Basic
    @Column(name = "end_date")
    private Instant endDate;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "idel")
    private int idel;
    @Basic
    @Column(name = "update_date")
    private Instant updateDate;
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
}
