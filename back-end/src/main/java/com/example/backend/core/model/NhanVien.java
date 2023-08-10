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
@Table(name = "nhan_vien")
public class NhanVien implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "ho_ten")
    private String hoTen;
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "sdt")
    private String sdt;
    @Column(name = "ngay_tao")
    private Instant ngayTao;
    @Column(name = "ngay_sua")
    private Instant ngaySua;
    @Column(name = "id_chuc_vu")
    private Long chucVu;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
