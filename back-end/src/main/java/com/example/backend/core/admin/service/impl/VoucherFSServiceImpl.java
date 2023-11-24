package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.repository.VoucherFreeShipRepository;
import com.example.backend.core.admin.service.VoucherFSService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.view.mapper.VoucherFSMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoucherFSServiceImpl implements VoucherFSService {
    @Autowired
    private VoucherFreeShipRepository voucherFreeShipRepository;

    @Autowired
    private VoucherFSMapper voucherAdminMapper;

    @Autowired
    EntityManager entityManager;

    public List<VoucherFreeShipDTO> getAllVouchers() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date, " +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity, " +
                    "  v.allow, " +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN order_detail o ON o.code_discount = v.code " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.idel, v.quantity, v.allow";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setStartDate((Date) row[3]);
                voucher.setEndDate((Date) row[4]);
                voucher.setConditions(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setAllow(Integer.valueOf(row[10].toString()));
                voucher.setUseVoucher(Integer.valueOf(row[11].toString()));

                if (new Date(System.currentTimeMillis()).after(voucher.getEndDate())) {
                    voucher.setStatus(1);
                } else {
                    voucher.setStatus(0);
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
        return null;
    }

    @Override
    public List<VoucherFreeShipDTO> getAll() {
        return null;
    }

    public ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherAdminDTO) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();

        VoucherFreeShip voucherFreeShip = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucherFreeShip.setCode("FS" + Instant.now().getEpochSecond());
        voucherFreeShip.setCreateDate(java.sql.Date.valueOf((LocalDate.now())));
        voucherFreeShip.setStatus(0);
        voucherFreeShip.setIdel(0);
        voucherFreeShip.setCreateName("xuân");
        voucherFreeShip.setStartDate(voucherAdminDTO.getStartDate());
        voucherFreeShip.setEndDate(voucherAdminDTO.getEndDate());

        for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
            CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
            voucherFreeShip.setIdCustomer(customerAdminDTO.getId());
            voucherFreeShipRepository.save(voucherFreeShip);
        }

        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO) {
        return null;
    }

    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        return null;
    }

    @Override
    public List<VoucherFreeShipDTO> detailById(Long voucherId) {
        return null;
    }

}
