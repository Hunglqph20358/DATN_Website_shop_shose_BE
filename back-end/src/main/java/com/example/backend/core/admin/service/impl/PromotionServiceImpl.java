package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.PromotionDTO;
import com.example.backend.core.admin.mapper.PromotionMapper;
import com.example.backend.core.admin.repository.PromotionRepository;
import com.example.backend.core.admin.service.PromotionService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.PromotionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private PromotionMapper khuyenMaiMapper;
    @Override
    public Page<PromotionDTO> getAllKhuyenMai(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<PromotionDetail> page = promotionRepository.findAll(pageable);
        return page.map(khuyenMaiMapper::toDto);
    }
    @Override
    public List<PromotionDTO> getAll() {
        List<PromotionDetail> khuyenMaiChiTietList = promotionRepository.getAll(); // Sử dụng phương thức thích hợp

        List<PromotionDTO> khuyenMaiDTOList = new ArrayList<>();
        for (PromotionDetail khuyenMaiChiTiet : khuyenMaiChiTietList) {
            PromotionDTO khuyenMaiDTO = khuyenMaiMapper.toDto(khuyenMaiChiTiet);
            khuyenMaiDTOList.add(khuyenMaiDTO);
        }
        return khuyenMaiDTOList;
    }

    @Override
    public ServiceResult<PromotionDTO> createKhuyenMai(PromotionDTO khuyenMaiDTO) {
        ServiceResult<PromotionDTO> serviceResult = new ServiceResult<>();
        promotionRepository.save(khuyenMaiMapper.toEntity(khuyenMaiDTO));
        serviceResult.setData(khuyenMaiDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);
        return serviceResult;
    }

}
