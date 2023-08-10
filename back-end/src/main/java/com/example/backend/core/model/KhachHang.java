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
@Table(name = "khach_hang")
public class KhachHang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "ho_ten")
    private String hoTen;
    @Column(name = "ngay_sinh")
    private Date ngaySinh;
    @Column(name = "sdt")
    private String sdt;
    @Column(name = "email")
    private String email;
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;
    @Column(name = "anh")
    private String linkAnh;
    @Column(name = "ngay_tao")
    private Instant ngayTao;
    @Column(name = "ngay_sua")
    private Instant ngaySua;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
