package com.example.backend.core.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher_free_ship")
public class VoucherFreeShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "id_customer")
    private String idCustomer;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "conditions")
    private BigDecimal conditions;

    @Column(name = "create_name")
    private String createName;

    @Column(name = "reduced_value", precision = 24, scale = 2)
    private BigDecimal reducedValue;

    @Column(name = "description")
    private String description;

    @Column(name = "status", columnDefinition = "int default 0")
    private Integer status;

    @Column(name = "idel", columnDefinition = "int default 0")
    private Integer idel;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "dele")
    private Integer delete;

    @Column(name = "limit_customer")
    private Integer limitCustomer;

    @Column(name = "option_customer", columnDefinition = "int default 0")
    private Integer optionCustomer;

}
