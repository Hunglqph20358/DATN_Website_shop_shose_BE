package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_san_pham")
    private Long sanPham;
    @Column(name = "id_loai")
    private Long loai;
    @Column(name = "id_nha_cung_cap")
    private Long nhaCungCap;
    @Column(name = "id_kich_co")
    private Long kichCo;
    @Column(name = "id_thuong_hieu")
    private Long thuongHieu;
    @Column(name = "id_mau_sac")
    private Long mauSac;
    @Column(name = "id_chat_lieu")
    private Long chatLieu;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "gia")
    private BigDecimal gia;
    @Column(name = "barcode")
    private String barCode;
    @Column(name = "ngay_tao")
    private Instant ngayTao;
    @Column(name = "ngay_sua")
    private Instant ngaySua;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
