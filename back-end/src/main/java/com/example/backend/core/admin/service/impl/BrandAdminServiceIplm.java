package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.mapper.BrandAdminMapper;
import com.example.backend.core.admin.repository.BrandAdminRepository;
import com.example.backend.core.admin.service.BrandAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Sole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandAdminServiceIplm implements BrandAdminService {
    @Autowired
    private BrandAdminRepository brrp;
    @Autowired
    private BrandAdminMapper brandAdminMapper;

    private ServiceResult<BrandAdminDTO> result = new ServiceResult<>();

    @Override
    public List<BrandAdminDTO> getAll() {
        List<BrandAdminDTO> list = brandAdminMapper.toDto(this.brrp.findAll());
        return list;
    }

    @Override
    public ServiceResult<BrandAdminDTO> add(BrandAdminDTO brandAdminDTO) {
        Brand brand =  brandAdminMapper.toEntity(brandAdminDTO);
        this.brrp.save(brand);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(brandAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<BrandAdminDTO> update(BrandAdminDTO brandAdminDTO, Long id) {
        Optional<Brand> optional = this.brrp.findById(id);
        if (optional.isPresent()){
            Brand brand = brandAdminMapper.toEntity(brandAdminDTO);
            brand.setId(id);
            Brand brandUpdate =  this.brrp.save(brand);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(brandAdminMapper.toDto(brandUpdate));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<BrandAdminDTO> delete(Long id) {
        Optional<Brand> optional = this.brrp.findById(id);
        if (optional.isPresent()){
            this.brrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }
}
