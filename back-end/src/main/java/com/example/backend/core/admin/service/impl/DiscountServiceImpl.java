package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountDTO;
import com.example.backend.core.admin.mapper.DiscountMapper;
import com.example.backend.core.admin.repository.DiscountRepository;
import com.example.backend.core.admin.service.DiscountService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.DiscountDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DiscountMapper discountMapper;

    @Override
    public Page<DiscountDTO> getAllKhuyenMai(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<DiscountDetail> page = discountRepository.findAll(pageable);
        return page.map(discountMapper::toDto);
    }

    @Override
    public List<DiscountDTO> getAll() {
        List<DiscountDetail> khuyenMaiChiTietList = discountRepository.getAll();
        List<DiscountDTO> khuyenMaiDTOList = new ArrayList<>();
        for (DiscountDetail khuyenMaiChiTiet : khuyenMaiChiTietList) {
            DiscountDTO khuyenMaiDTO = discountMapper.toDto(khuyenMaiChiTiet);
            khuyenMaiDTOList.add(khuyenMaiDTO);
        }
        return khuyenMaiDTOList;
    }

    @Override
    public ServiceResult<DiscountDTO> createKhuyenMai(DiscountDTO khuyenMaiDTO) {
        ServiceResult<DiscountDTO> serviceResult = new ServiceResult<>();
        discountRepository.save(discountMapper.toEntity(khuyenMaiDTO));
        serviceResult.setData(khuyenMaiDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);
        return serviceResult;
    }

    @Override
    public ServiceResult<DiscountDTO> updateKhuyenMai(DiscountDTO khuyenMaiDTO) {
        ServiceResult<DiscountDTO> serviceResult = new ServiceResult<>();
        // Kiểm tra xem khuyenMaiDTO có tồn tại trong cơ sở dữ liệu không
        Optional<DiscountDetail> existingDiscount = discountRepository.findById(khuyenMaiDTO.getId());
        if (existingDiscount.isPresent()) {
            // Cập nhật thông tin khuyenMaiDTO vào bản ghi đã tồn tại
            discountRepository.save(discountMapper.toEntity(khuyenMaiDTO));
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
        if (discountRepository.existsById(khuyenMaiId)) {
            discountRepository.deleteById(khuyenMaiId);
            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi ");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
}
