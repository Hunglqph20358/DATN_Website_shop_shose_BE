package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
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
    private Long idCustomer;

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

    @Column(name = "limit_customer")
    private Integer limitCustomer;

    @Column(name = "allow", columnDefinition = "int default 0")
    private Integer allow;

    @Column(name = "option_customer", columnDefinition = "int default 0")
    private Integer optionCustomer;

}
