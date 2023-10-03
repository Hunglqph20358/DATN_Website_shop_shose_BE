package com.example.backend.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name ="id_customer")
    private Long id_customer;
    @Column(name = "name_address")
    private String name_address;
    @Column(name = "detailed_description")
    private String detailed_description;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "wards")
    private String wards;
    @Column(name = "street")
    private String street;

}
