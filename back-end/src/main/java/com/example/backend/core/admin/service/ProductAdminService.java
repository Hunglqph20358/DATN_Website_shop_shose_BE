package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.ResponseImportDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ProductAdminService {
    List<ProductAdminDTO> getAll();
    ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO);
    ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO,Long id);
    ServiceResult<ProductAdminDTO> delete(Long id);
    ServiceResult<ProductAdminDTO> getById(Long id);
    List<ProductAdminDTO> getProduct(String code, String name);
    List<ProductAdminDTO> findByNameLikeOrCodeLike(String param);

    byte[] exportExcelProduct() throws IOException;

    byte[] exportExcelTemplateProduct() throws IOException;

    ServiceResult<ResponseImportDTO> importFileProduct(MultipartFile fileUploads, Long typeImport) throws IOException, ParseException;

    byte[] exportExcelProductErrors(List<ProductAdminDTO> listDataErrors) throws IOException;
}
