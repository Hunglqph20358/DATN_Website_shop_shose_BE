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
@Table(name = "kick_co")
public class KichCo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "sokichco")
    private String soKichCo;
    @Column(name = "ngay_tao")
    private Instant ngayTao;
    @Column(name = "ngay_sua")
    private Instant ngaySua;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
