package com.example.backend.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_hoa_don")
    private Long hoaDon;
    @Column(name = "id_san_pham_chi_tiet")
    private Long sanPhamChiTiet;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "gia")
    private BigDecimal gia;
    @Column(name = "ngay_tao")
    private Instant ngayTao;
    @Column(name = "ngay_sua")
    private Instant ngaySua;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
