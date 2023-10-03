package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "id_customer")
    private Long id_customer;
    @Column(name = "id_staff")
    private Long id_staff;
    @Column(name = "create_date")
    private Instant create_date;
    @Column(name = "payment_date")
    private Instant payment_date;
    @Column(name = "delivery_date")
    private Instant delivery_date;
    @Column(name = "received_date")
    private Instant received_date;
    @Column(name = "shipper_phone")
    private String shipper_phone;
    @Column(name = "receiver_phone")
    private String receiver_phone;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "ship_price")
    private BigDecimal ship_price;
    @Column(name = "total_price")
    private BigDecimal total_price;
    @Column(name = "total_payment")
    private BigDecimal total_payment;
    @Column(name = "payment_type")
    private Integer payment_type;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
}
