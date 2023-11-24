package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "id_customer")
  private String idCustomer;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "conditions")
  private BigDecimal conditions;

  @Column(name = "create_name")
  private String createName;

  @Column(name = "voucher_type")
  private int voucherType;

  @Column(name = "reduced_value")
  private BigDecimal reducedValue;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private int status;

  @Column(name = "idel")
  private int idel;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "max_reduced")
  private BigDecimal maxReduced;

  @Column(name = "limit_customer")
  private int limitCustomer;

  @Column(name = "allow")
  private int allow;

  // Getters and setters (omitted for brevity)

}
