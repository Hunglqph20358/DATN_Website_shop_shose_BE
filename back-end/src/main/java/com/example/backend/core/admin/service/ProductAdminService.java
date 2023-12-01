package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface ProductAdminService {
    List<ProductAdminDTO> getAll();
    ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO);
    ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO,Long id);
    ServiceResult<ProductAdminDTO> delete(Long id);
    ServiceResult<ProductAdminDTO> getById(Long id);
    List<ProductAdminDTO> getProduct(String code, String name);
    List<ProductAdminDTO> findByNameLikeOrCodeLike(String param);
}
