package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.ProductAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.*;
import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.dto.CategoryDTO;
import com.example.backend.core.view.dto.MaterialDTO;
import com.example.backend.core.view.dto.SoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductAdminServiceIplm  implements ProductAdminService {
    @Autowired
    private ProductAdminRepository prdrp;
    @Autowired
    private ProductAdminMapper productAdminMapper;
    @Autowired
    private CategoryAdminRepository ctrp;
    @Autowired
    private CategoryAdminMapper categoryAdminMapper;
    @Autowired
    private BrandAdminRepository brrp;
    @Autowired
    private BrandAdminMapper brandAdminMapper;
    @Autowired
    private MaterialAdminRepository mtrp;
    @Autowired
    private MaterialAdminMapper materialAdminMapper;
    @Autowired
    private SoleAdminRepository slrp;
    @Autowired
    private SoleAdminMapper soleAdminMapper;

    private ServiceResult<ProductAdminDTO> result = new ServiceResult<>();
    @Override
    public List<ProductAdminDTO> getAll() {

        List<ProductAdminDTO>  list = productAdminMapper.toDto(prdrp.findAll());
        for (int i = 0; i < list.size(); i++) {
          SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(slrp.findById(list.get(i).getIdSole()).get());
          list.get(i).setSoleAdminDTO(soleAdminDTO);
          MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(mtrp.findById(list.get(i).getIdMaterial()).get());
          list.get(i).setMaterialAdminDTO(materialAdminDTO);
          BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brrp.findById(list.get(i).getIdBrand()).get());
          list.get(i).setBrandAdminDTO(brandAdminDTO);
          CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(ctrp.findById(list.get(i).getIdCategory()).get());
          list.get(i).setCategoryAdminDTO(categoryAdminDTO);
        }
        return list;
    }

    @Override
    public ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO) {
        Product product = productAdminMapper.toEntity(productAdminDTO);
        Optional<Brand> brand = brrp.findById(product.getIdBrand());
        Optional<Material> material = mtrp.findById(product.getIdMaterial());
        Optional<Sole> sole = slrp.findById(product.getIdSole());
        Optional<Category> category = ctrp.findById(product.getIdCategory());
        MaterialAdminDTO materialDTO = materialAdminMapper.toDto(material.orElse(null));
        SoleAdminDTO soleDTO = soleAdminMapper.toDto(sole.get());
        CategoryAdminDTO categoryDTO = categoryAdminMapper.toDto(category.get());
        BrandAdminDTO brandDTO = brandAdminMapper.toDto(brand.orElse(null));
        product.setIdBrand(brandDTO.getId());
        product.setIdCategory(categoryDTO.getId());
        product.setIdMaterial(materialDTO.getId());
        product.setIdSole(soleDTO.getId());
        productAdminDTO.setSoleAdminDTO(soleDTO);
        productAdminDTO.setBrandAdminDTO(brandDTO);
        productAdminDTO.setCategoryAdminDTO(categoryDTO);
        productAdminDTO.setMaterialAdminDTO(materialDTO);
        product.setCreateDate(Instant.now());
        product.setUpdateDate(Instant.now());
        this.prdrp.save(product);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(productAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id) {
        Optional<Product> optional = this.prdrp.findById(id);
        if (optional.isPresent()){
            Product product = optional.get();
            product.setId(id);
            product.setCode(productAdminDTO.getCode());
            product.setName(productAdminDTO.getName());
            product.setUpdateDate(Instant.now());
            product.setUpdateName(productAdminDTO.getUpdateName());
            product.setIdBrand(productAdminDTO.getIdBrand());
            product.setIdSole(productAdminDTO.getIdSole());
            product.setIdMaterial(productAdminDTO.getIdMaterial());
            product.setIdCategory(productAdminDTO.getIdCategory());
            product.setDescription(productAdminDTO.getDescription());
            product.setStatus(productAdminDTO.getStatus());
            product =  this.prdrp.save(product);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(productAdminMapper.toDto(product));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductAdminDTO> delete(Long id) {
        Optional<Product> optional = this.prdrp.findById(id);
        if (optional.isPresent()){
            this.prdrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }
}
//        Instant instant = Instant.now();
//        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Múi giờ Việt Nam
//        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
//        Instant vietnamInstant = zonedDateTime.toInstant();
//        Long idsole = productAdminDTO.getIdSole();
//        ServiceResult<SoleAdminDTO> soleAdminResult = slsv.findbyid(idsole);
//        Long idbrand = productAdminDTO.getIdBrand();
//        ServiceResult<BrandAdminDTO> brandAdminResult = brsv.findbyid(idbrand);
//        Long idcategory = productAdminDTO.getIdCategory();
//        ServiceResult<CategoryAdminDTO> categoryAdminResult = ctsv.findbyid(idcategory);
//        Long idmaterial = productAdminDTO.getIdMaterial();
//        ServiceResult<MaterialAdminDTO> materialAdminResult = mtsv.findbyid(idmaterial);
//        if (soleAdminResult.getStatus() != HttpStatus.OK) {
//            return ResponseEntity.status(soleAdminResult.getStatus()).build();
//        }
//        if (brandAdminResult.getStatus() != HttpStatus.OK) {
//            return ResponseEntity.status(brandAdminResult.getStatus()).build();
//        }
//        if (categoryAdminResult.getStatus() != HttpStatus.OK) {
//            return ResponseEntity.status(categoryAdminResult.getStatus()).build();
//        }
//        if (materialAdminResult.getStatus() != HttpStatus.OK) {
//            return ResponseEntity.status(materialAdminResult.getStatus()).build();
//        }
//        SoleAdminDTO soleAdminDTO = soleAdminResult.getData();
//        BrandAdminDTO brandAdminDTO = brandAdminResult.getData();
//        CategoryAdminDTO categoryAdminDTO = categoryAdminResult.getData();
//        MaterialAdminDTO materialAdminDTO = materialAdminResult.getData();
