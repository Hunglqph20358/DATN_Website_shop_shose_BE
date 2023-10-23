package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.service.ProductAdminService;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdminServiceIplm  implements ProductAdminService {
    @Override
    public List<ProductAdminDTO> getAll() {
        return null;
    }

    @Override
    public ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO) {
        return null;
    }

    @Override
    public ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id) {
        return null;
    }

    @Override
    public ServiceResult<ProductAdminDTO> delete(Long id) {
        return null;
    }
}
