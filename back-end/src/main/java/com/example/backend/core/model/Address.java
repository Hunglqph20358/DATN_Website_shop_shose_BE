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
    @Column(name ="id_staff")
    private Long idStaff;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "province")
    private String province;
    @Column(name = "district_id")
    private String districtId;
    @Column(name = "district")
    private String district;
    @Column(name = "ward_code")
    private String wardCode;
    @Column(name = "wards")
    private String wards;
    @Column(name = "specific_address")
    private String specificAddress;

}
