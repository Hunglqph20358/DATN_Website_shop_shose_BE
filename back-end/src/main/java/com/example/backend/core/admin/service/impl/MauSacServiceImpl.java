package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.MauSacDTO;
import com.example.backend.core.admin.mapper.MauSacMapper;
import com.example.backend.core.admin.repository.MauSacRepository;
import com.example.backend.core.admin.service.MauSacService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.MauSac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;
    @Autowired
    private MauSacMapper mauSacMapper;

    @Override
    public Page<MauSacDTO> getAllMauSac(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<MauSac> page = mauSacRepository.findAll(pageable);
        return page.map(mauSacMapper::toDto);
    }

    @Override
    public ServiceResult<MauSacDTO> createMauSac(MauSacDTO mauSacDTO) {
        ServiceResult<MauSacDTO> serviceResult = new ServiceResult<>();
        mauSacRepository.save(mauSacMapper.toEntity(mauSacDTO));
        serviceResult.setData(mauSacDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);
        return serviceResult;
    }
}
