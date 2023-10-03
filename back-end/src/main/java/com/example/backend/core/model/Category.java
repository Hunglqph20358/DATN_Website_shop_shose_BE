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
@Setter
@Getter
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "create_date")
    private Instant create_date;
    @Column(name = "update_date")
    private Instant update_date;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idel")
    private Integer idel;
}
