package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.repository.VoucherFSCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class VoucherFSCustomerRepositoryImpl implements VoucherFSCustomerRepository {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<VoucherFreeShipDTO> getAllVouchers() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "where dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VoucherFreeShipDTO> getAllVoucherFSsExport() {
        try {
            String sql = "SELECT v.id, v.code, v.name,v.start_date, v.end_date,v.conditions, v.reduced_value,v.quantity,v.limit_customer,v.allow,v.status,c.fullname \n" +
                    "FROM voucher_free_ship v\n" +
                    "JOIN customer c ON FIND_IN_SET(c.id, v.id_customer) > 0 where v.dele=0;";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setQuantity(Integer.valueOf(row[7].toString()));
                voucher.setLimitCustomer(Integer.valueOf(row[8].toString()));
                voucher.setAllow(row[9] != null ? Integer.valueOf((row[9].toString())) : null);
                voucher.setStatus(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setNameCustomer(row[11].toString());


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKichHoat() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "where idel = 1 and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    " v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKhongKH() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code" +
                    " where  idel =0 and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByTimeRange(Date fromDate, Date toDate) {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "WHERE v.start_date BETWEEN :fromDate AND :toDate and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    " v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword) {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "WHERE v.name LIKE :keyword OR v.code LIKE :keyword and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("keyword", "%" + keyword + "%"); // Sử dụng % để tìm kiếm mọi nơi trong chuỗi.

            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm) {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "LEFT JOIN customer c ON v.id_customer = c.id " +
                    "WHERE LOWER(c.code) LIKE LOWER(:searchTerm) " +
                    "   OR LOWER(c.fullname) LIKE LOWER(:searchTerm) " +
                    "   OR c.phone LIKE  :searchTerm and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    " v.reduced_value, v.description, v.status, v.idel, v.quantity";


            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("searchTerm", "%" + searchTerm + "%"); // Sử dụng % để tìm kiếm mọi nơi trong chuỗi.

            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(row[10] != null ? Integer.valueOf((row[10].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[11].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<CustomerAdminDTO> getAllCustomer() {
        try {
            String sql = "SELECT " +
                    "  c.id, " +
                    "  c.code, " +
                    "  c.fullname, " +
                    "  c.birthday, " +
                    "  c.phone, " +
                    "  c.email, " +
                    "  c.gender, " +
                    "  c.status, " +
                    "  c.idel, " +
                    "  COUNT(o.id) AS order_count " +
                    "FROM customer c " +
                    "LEFT JOIN `order` o ON c.id = o.id_customer " +
                    "GROUP BY c.id, c.code, c.fullname, c.birthday, c.phone, c.email, c.gender, c.status, c.idel";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<CustomerAdminDTO> customers = new ArrayList<>();

            for (Object[] row : resultList) {
                CustomerAdminDTO customer = new CustomerAdminDTO();

                customer.setId(Long.parseLong(row[0].toString()));
                customer.setCode(row[1].toString());
                customer.setFullname(row[2].toString());
                customer.setPhone(row[4].toString());
                customer.setEmail(row[5].toString());
                customer.setGender(row[6].toString());
                customer.setStatus(Integer.valueOf(row[7].toString()));
                customer.setIdel(Integer.valueOf(row[8].toString()));
                customer.setOrderCount(Integer.valueOf(row[9].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date birthday = dateFormat.parse(row[3].toString());

                    customer.setBirthday(birthday);

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                customers.add(customer);
            }
            return customers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
