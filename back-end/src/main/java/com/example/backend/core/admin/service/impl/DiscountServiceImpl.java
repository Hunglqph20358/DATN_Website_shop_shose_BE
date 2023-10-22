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

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountRepository promotionRepository;
    @Autowired
    private DiscountMapper khuyenMaiMapper;
    @Override
    public Page<DiscountDTO> getAllKhuyenMai(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<DiscountDetail> page = promotionRepository.findAll(pageable);
        return page.map(khuyenMaiMapper::toDto);
    }
    @Override
    public List<DiscountDTO> getAll() {
        List<DiscountDetail> khuyenMaiChiTietList = promotionRepository.getAll(); // Sử dụng phương thức thích hợp

        List<DiscountDTO> khuyenMaiDTOList = new ArrayList<>();
        for (DiscountDetail khuyenMaiChiTiet : khuyenMaiChiTietList) {
            DiscountDTO khuyenMaiDTO = khuyenMaiMapper.toDto(khuyenMaiChiTiet);
            khuyenMaiDTOList.add(khuyenMaiDTO);
        }
        return khuyenMaiDTOList;
    }

    @Override
    public ServiceResult<DiscountDTO> createKhuyenMai(DiscountDTO khuyenMaiDTO) {
        ServiceResult<DiscountDTO> serviceResult = new ServiceResult<>();
        promotionRepository.save(khuyenMaiMapper.toEntity(khuyenMaiDTO));
        serviceResult.setData(khuyenMaiDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);
        return serviceResult;
    }

}
