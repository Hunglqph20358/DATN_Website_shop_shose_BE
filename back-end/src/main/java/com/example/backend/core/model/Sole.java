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
@Table(name = "sole")
public class Sole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "sole_height")
    private String sole_height;
    @Column(name = "sole_material")
    private String sole_material;
    @Column(name = "create_date")
    private Instant create_date;
    @Column(name = "update_date")
    private Instant update_date;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
}
