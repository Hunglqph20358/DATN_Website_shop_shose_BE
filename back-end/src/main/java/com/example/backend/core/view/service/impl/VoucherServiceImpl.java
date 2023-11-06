package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.dto.VoucherDTO;
import com.example.backend.core.view.mapper.VoucherMapper;
import com.example.backend.core.view.repository.VoucherRepository;
import com.example.backend.core.view.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public List<VoucherDTO> getAllVoucher() {
        return voucherMapper.toDto(voucherRepository.getAllVoucher());
    }

    @Override
    public ServiceResult<VoucherDTO> findByCode(String code) {
        ServiceResult<VoucherDTO> result = new ServiceResult<>();
        Voucher voucher = voucherRepository.findByCode(code);
        result.setData(voucherMapper.toDto(voucher));
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
