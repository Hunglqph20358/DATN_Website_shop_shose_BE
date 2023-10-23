package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.mapper.MaterialAdminMapper;
import com.example.backend.core.admin.repository.MaterialAdminRepository;
import com.example.backend.core.admin.service.MaterialAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Material;
import com.example.backend.core.model.Sole;
import com.example.backend.core.view.dto.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialAdminServiceIplm implements MaterialAdminService {
    @Autowired
    private MaterialAdminRepository mtrp;
    @Autowired
    private MaterialAdminMapper materialAdminMapper;

    private ServiceResult<MaterialAdminDTO> result = new ServiceResult<>();
    @Override
    public List<MaterialAdminDTO> getAll() {
        List<MaterialAdminDTO> list =  materialAdminMapper.toDto(this.mtrp.findAll());
        return list;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> add(MaterialAdminDTO materialAdminDTO) {
        Material material =  materialAdminMapper.toEntity(materialAdminDTO);
        this.mtrp.save(material);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(materialAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> update(MaterialAdminDTO materialAdminDTO, Long id) {
        Optional<Material> optional = this.mtrp.findById(id);
        if (optional.isPresent()){
            Material material = materialAdminMapper.toEntity(materialAdminDTO);
            material.setId(id);
            Material mateUpdate =  this.mtrp.save(material);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(materialAdminMapper.toDto(mateUpdate));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> delete(Long id) {
        Optional<Material> optional = this.mtrp.findById(id);
        if (optional.isPresent()){
            this.mtrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }


}
