package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface CategoryAdminService {
    List<CategoryAdminDTO> getAll();
    ServiceResult<CategoryAdminDTO> add(CategoryAdminDTO categoryAdminDTO);
    ServiceResult<CategoryAdminDTO> update(CategoryAdminDTO categoryAdminDTO,Long id);
    ServiceResult<CategoryAdminDTO> delete(Long id);
}
