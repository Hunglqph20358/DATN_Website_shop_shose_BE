package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.mapper.VoucherAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.repository.VoucherAdminRepository;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.dto.CustomerDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherAdminServiceImpl implements VoucherAdminService {
    @Autowired
    private VoucherAdminRepository voucherAdminRepository;
    @Autowired
    private VoucherAdminMapper voucherAdminMapper;
    @Autowired
    EntityManager entityManager;

    @Override
    public List<VoucherAdminDTO> getAll() {
        return voucherAdminMapper.toDto(voucherAdminRepository.getAll());
    }
    @Override
    public List<VoucherAdminDTO> getAllVouchers() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date" +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN order_detail o ON o.code_discount = v.code " +
                    "GROUP BY v.id, v.code, v.name, v.create_date, v.end_date, v.conditions, " +
                    "v.voucher_type, v.reduced_value, v.description, v.status, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditions(BigDecimal.valueOf(Long.parseLong(row[5].toString())));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(BigDecimal.valueOf(Long.parseLong(row[7].toString())));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(row[9].toString());
                voucher.setMaxReduced(BigDecimal.valueOf(Long.parseLong(row[10].toString())));
                voucher.setAllow(Integer.valueOf(row[11].toString()));
                voucher.setUseVoucher(Integer.valueOf(row[12].toString()));

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
    public ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        Voucher voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucher.setCode("GG" + Instant.now().getEpochSecond());
        voucher.setCreateDate(java.util.Date.from(Instant.now()));
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setCreateName(voucherAdminDTO.getCreateName());
        voucher.setStartDate(voucherAdminDTO.getStartDate());
        voucher.setEndDate(voucherAdminDTO.getEndDate());
        voucher.setAllow(voucher.getAllow());
       if(voucher.getVoucherType()==0){
           voucher.setMaxReduced(voucher.getReducedValue());
       }
        voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
        StringBuilder customer = new StringBuilder();
        for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
            CustomerAdminDTO customerAdminDTO=voucherAdminDTO.getCustomerAdminDTOList().get(i);
            customer.append(customerAdminDTO.getId());
            customer.append(",");
        }
        voucher.setIdCustomer(customer.toString());
        voucherAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        // Tìm kiếm đối tượng Voucher trong cơ sở dữ liệu dựa trên id
        Optional<Voucher> voucherOptional = voucherAdminRepository.findById(id);

        if (voucherOptional.isPresent()) {
            Voucher existingVoucher = voucherOptional.get();

            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            existingVoucher.setStartDate(updatedVoucherAdminDTO.getStartDate());
            existingVoucher.setEndDate(updatedVoucherAdminDTO.getEndDate());
            existingVoucher.setDescription(updatedVoucherAdminDTO.getDescription());
           existingVoucher.setVoucherType(updatedVoucherAdminDTO.getVoucherType());
           existingVoucher.setConditions(updatedVoucherAdminDTO.getConditions());
           existingVoucher.setReducedValue(updatedVoucherAdminDTO.getReducedValue());
           existingVoucher.setQuantity(updatedVoucherAdminDTO.getQuantity());

            // Lưu đối tượng Voucher đã cập nhật
            voucherAdminRepository.save(existingVoucher);

            serviceResult.setData(updatedVoucherAdminDTO);
            serviceResult.setMessage("Cập nhật thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setData(null);
            serviceResult.setMessage("Không tìm thấy Voucher cần cập nhật");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }


    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<Voucher> voucher = voucherAdminRepository.findById(voucherId);

        if (voucher.isPresent()) {
            Voucher voucher1 = voucher.get();
            voucher1.setIdel(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherAdminRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
    @Override
    public List<VoucherAdminDTO> detailById(Long voucherId) {
        List<VoucherAdminDTO> list = new ArrayList<>();

        // Thực hiện câu truy vấn JOIN để lấy thông tin từ cả bảng voucher và customer
        String query = "SELECT "
                + "c.code as customer_code, c.fullname as customer_fullname, "
                + " v.code as voucher_code, v.name as voucher_name, v.start_date as voucher_start_date, v.end_date as voucher_end_date, "
                + "v.conditions as voucher_conditions, v.create_name as voucher_create_name, v.voucher_type as voucher_type, "
                + "v.reduced_value as voucher_reduced_value, v.description as voucher_description, v.status as voucher_status, "
                + "v.quantity as voucher_quantity, v.max_reduced as voucher_max_reduced, v.limit_customer as voucher_limit_customer, v.allow as voucher_allow "
                + " FROM\n" +
                "       voucher v \n" +
                "    LEFT JOIN\n" +
                "        customer c ON c.id = v.id_customer "
                + "WHERE v.id = :voucherId";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("voucherId", voucherId);

        List<Object[]> result = nativeQuery.getResultList();
        // Chuyển kết quả từ nativeQuery sang DTO
        for (Object[] row : result) {
            VoucherAdminDTO voucherAdminDTO = new VoucherAdminDTO();
            CustomerAdminDTO customerAdminDTO=new CustomerAdminDTO();
            customerAdminDTO.setCode((String) row[0]);
            customerAdminDTO.setFullname((String) row[1]);


            voucherAdminDTO.setCode((String) row[2]);
            voucherAdminDTO.setName((String) row[3]);
//            voucherAdminDTO.setStartDate((Date) row[4]);
//            voucherAdminDTO.setEndDate((Date) row[5]);
            voucherAdminDTO.setConditions((BigDecimal) row[6]);
            voucherAdminDTO.setCreateName((String) row[7]);
            voucherAdminDTO.setVoucherType((Integer) row[8]);
            voucherAdminDTO.setReducedValue((BigDecimal) row[9]);
            voucherAdminDTO.setDescription((String) row[10]);
            voucherAdminDTO.setStatus((Integer) row[11]);
            voucherAdminDTO.setQuantity((Integer) row[12]);
            voucherAdminDTO.setMaxReduced((BigDecimal) row[13]);
            voucherAdminDTO.setLimitCustomer((Integer) row[14]);
            voucherAdminDTO.setAllow((Integer) row[15]);
            list.add(voucherAdminDTO);
            List<CustomerAdminDTO> customerAdminDTOList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                customerAdminDTOList.add(customerAdminDTO);
                list.get(i).setCustomerAdminDTOList(customerAdminDTOList);
            }
        }

        return list;
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
