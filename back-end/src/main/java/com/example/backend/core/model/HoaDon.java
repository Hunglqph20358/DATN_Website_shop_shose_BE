package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoa_don")
public class HoaDon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_khach_hang")
    private Long khachHang;
    @Column(name = "id_nhan_vien")
    private Long nhanVien;
    @Column(name = "ma")
    private String ma;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "ngay_sua")
    private Date ngaySua;
    @Column(name = "ngay_thanh_toan")
    private Date ngayThanhToan;
    @Column(name = "ngay_ship")
    private Date ngayShip;
    @Column(name = "ngay_du_kien")
    private Date ngayDuKien;
    @Column(name = "ngay_giao_hang")
    private Date ngayGiaoHang;
    @Column(name = "phi_giao_hang")
    private BigDecimal phiGiaoHang;
    @Column(name = "tong_tien")
    private BigDecimal tongTien;
    @Column(name = "nguoi_nhan")
    private String nguoiNhan;
    @Column(name = "sdt_nguoi_nhan")
    private String sdtNguoiNhan;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "sdt_nguoi_giao")
    private String sdtNguoiGiao;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
