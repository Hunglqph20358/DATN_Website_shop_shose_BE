package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    private Instant start_date;
    @Column(name = "end_date")
    private Instant end_date;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idel")
    private Integer idel;
}
