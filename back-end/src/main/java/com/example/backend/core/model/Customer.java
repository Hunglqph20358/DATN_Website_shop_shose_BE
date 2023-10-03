package com.example.backend.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "birthday")
    private Instant birthday;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @Column(name = "create_date")
    private Instant create_date;
    @Column(name = "update_date")
    private Instant update_date;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idel")
    private Integer idel;
}
