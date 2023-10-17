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
    @Column(name ="id_customer")
    private Long idCustomer;
    @Column(name = "name_address")
    private String nameAddress;
    @Column(name = "detailed_description")
    private String detailedDescription;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "wards")
    private String wards;
    @Column(name = "street")
    private String street;

}
