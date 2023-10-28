package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountDetailAdminServiceImpl implements DiscountDetailAdminService {
    @Autowired
    private DiscountDetailAdminRepository discountDetailRepository;
    @Autowired
    private DiscountAdminRepository discountAdminRepository;
    @Autowired
    private DiscountDetailAdminMapper discountDetailAdminMapper;
    @Autowired
    private DiscountAdminMapper discountAdminMapper;
    @Autowired
    EntityManager entityManager;

    @Override
    public Page<DiscountDetailAdminDTO> getAllKhuyenMai(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<DiscountDetail> page = discountDetailRepository.findAll(pageable);
        return page.map(discountDetailAdminMapper::toDto);
    }


    @Override
    public List<DiscountDetailAdminDTO> getAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT dd.id, dd.id_product, dd.id_discount, dd.reduced_value, dd.discount_type, dd.status, " +
                    "d.code, d.name, d.create_date,d.start_date, d.end_date, d.description, d.idel " +
                    "FROM discount_detail dd " +
                    "INNER JOIN discount d ON dd.id_discount = d.id "
            );
            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();
            List<DiscountDetailAdminDTO> discountDetails = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountDetailAdminDTO discountDetail = new DiscountDetailAdminDTO();
                discountDetail.setId(((Number) row[0]).longValue());
                discountDetail.setIdProduct((Integer) row[1]);
                discountDetail.setIdDiscount((Integer) row[2]);
                discountDetail.setReducedValue((BigDecimal) row[3]);
                discountDetail.setDiscountType((Integer) row[4]);
                discountDetail.setStatus((Integer) row[5]);

                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setCode((String) row[6]);
                discount.setName((String) row[7]);
                discount.setCreateDate(((Timestamp) row[8]).toInstant());
                discount.setStartDate(((Timestamp) row[9]).toInstant());
                discount.setEndDate(((Timestamp) row[10]).toInstant());
                discount.setDescription((String) row[11]);
                discount.setIdel((Integer) row[12]);

                discountDetail.setDiscountAdminDTO(discount);
                discountDetails.add(discountDetail);
            }

            return discountDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ServiceResult<DiscountDetailAdminDTO> createKhuyenMai(DiscountDetailAdminDTO discountDetailAdminDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();

        // Chuyển DTO sang Entity cho DiscountAdmin
        Discount discountAdminEntity = discountAdminMapper.toEntity(discountDetailAdminDTO.getDiscountAdminDTO());
        discountAdminEntity = discountAdminRepository.save(discountAdminEntity);

        // Chuyển DTO sang Entity cho DiscountDetail
        DiscountDetail discountDetailEntity = discountDetailAdminMapper.toEntity(discountDetailAdminDTO);

        // Thiết lập quan hệ giữa DiscountAdmin và DiscountDetail
        discountDetailEntity.setIdDiscount(discountAdminEntity.getId());

        discountDetailRepository.save(discountDetailEntity);

        serviceResult.setData(discountDetailAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<DiscountDetailAdminDTO> updateKhuyenMai(DiscountDetailAdminDTO khuyenMaiDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();
        // Kiểm tra xem khuyenMaiDTO có tồn tại trong cơ sở dữ liệu không
        Optional<DiscountDetail> existingDiscount = discountDetailRepository.findById(khuyenMaiDTO.getId());
        if (existingDiscount.isPresent()) {
            // Cập nhật thông tin khuyenMaiDTO vào bản ghi đã tồn tại
            discountDetailRepository.save(discountDetailAdminMapper.toEntity(khuyenMaiDTO));
            serviceResult.setData(khuyenMaiDTO);
            serviceResult.setMessage("Cập nhật thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Void> deleteKhuyenMai(Long khuyenMaiId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        if (discountDetailRepository.existsById(khuyenMaiId)) {
            discountDetailRepository.deleteById(khuyenMaiId);
            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi ");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
}
