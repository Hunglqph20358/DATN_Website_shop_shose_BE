package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherAdminServiceImpl implements VoucherAdminService {
    @Autowired
    private VoucherAdminRepository voucherAdminRepository;
    @Autowired
    private VoucherAdminMapper voucherAdminMapper;

    @Override
    public Page<VoucherAdminDTO> getAllVoucher(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<Voucher> page = voucherAdminRepository.findAll(pageable);
        return page.map(voucherAdminMapper::toDto);
    }

    @Override
    public List<VoucherAdminDTO> getAll() {
        return voucherAdminMapper.toDto(voucherAdminRepository.getAll());
    }

    @Override
    public ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        Voucher voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucherAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> updateVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        // Kiểm tra xem voucherDTO có tồn tại trong cơ sở dữ liệu không
        Optional<Voucher> existingDiscount = voucherAdminRepository.findById(voucherAdminDTO.getId());
        if (existingDiscount.isPresent()) {
            // Cập nhật thông tin voucherDTO vào bản ghi đã tồn tại
            voucherAdminRepository.save(voucherAdminMapper.toEntity(voucherAdminDTO));
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setMessage("Cập nhật thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        if (voucherAdminRepository.existsById(voucherId)) {
            voucherAdminRepository.deleteById(voucherId);
            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi ");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
}
