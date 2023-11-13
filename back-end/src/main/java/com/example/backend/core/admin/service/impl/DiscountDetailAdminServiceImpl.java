package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.dto.CategoryDTO;
import com.example.backend.core.view.dto.ProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountDetailAdminServiceImpl implements DiscountDetailAdminService {
    @Autowired
    private DiscountDetailAdminRepository discountDetailRepository;
    @Autowired
    private DiscountAdminRepository discountAdminRepository;
    @Autowired
    private DiscountDetailAdminMapper discountDetailAdminMapper;
    @Autowired
    private DiscountAdminMapper discountAdminMapper;
    @Autowired
    EntityManager entityManager;

    @Override
    public List<DiscountAdminDTO> getAllDiscount() {
        List<DiscountAdminDTO> list = discountAdminMapper.toDto(discountAdminRepository.getAll());
        return list ;
    }


    @Override
    public List<DiscountDetailAdminDTO> getAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT\n" +
                    "    dd.id,\n" +
                    "    dd.id_product,\n" +
                    "    dd.id_discount,\n" +
                    "    dd.reduced_value,\n" +
                    "    dd.discount_type,\n" +
                    "    dd.status,\n" +
                    "    d.code,\n" +
                    "    d.name,\n" +
                    "    d.create_date,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel,\n" +
                    "    p.name AS product_name,\n" +
                    "    p.create_date AS product_create_date,\n" +
                    "    p.code AS product_code,\n" +
                    "    p.update_name AS product_update_name,\n" +
                    "    p.id_brand,\n" +
                    "    p.id_category,\n" +
                    "    p.id_material,\n" +
                    "    p.id_sole,\n" +
                    "    p.description AS product_description,\n" +
                    "    p.status AS product_status,\n" +
                    "    p.idel AS product_idel,\n" +
                    "    b.name AS brand_name, -- Tên thương hiệu\n" +
                    "    c.name AS category_name -- Tên danh mục\n" +
                    "FROM\n" +
                    "    discount_detail dd\n" +
                    "INNER JOIN\n" +
                    "    discount d ON dd.id_discount = d.id\n" +
                    "Inner JOIN\n" +
                    "    product p ON dd.id_product = p.id\n" +
                    "LEFT JOIN\n" +
                    "    brand b ON p.id_brand = b.id -- Liên kết với bảng brand\n" +
                    "LEFT JOIN\n" +
                    "    category c ON p.id_category = c.id -- Liên kết với bảng category\n" +
                    "where d.idel= 0");

            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();
            List<DiscountDetailAdminDTO> discountDetails = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountDetailAdminDTO discountDetail = new DiscountDetailAdminDTO();
                discountDetail.setId(Long.valueOf(row[0].toString()));
                discountDetail.setIdProduct(( row[1].toString()));
                discountDetail.setIdDiscount(( row[2].toString()));
                discountDetail.setReducedValue( row[3].toString());
                discountDetail.setDiscountType( row[4].toString());
                discountDetail.setStatus((Integer) row[5]);

                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setCode( row[6].toString());
                discount.setName( row[7].toString());
                discount.setCreateDate((Date) row[8]);
                discount.setStartDate((Date) row[9]);
                discount.setEndDate((Date) row[10]);
                discount.setDescription( row[11].toString());
                discount.setIdel( row[12].toString());

                ProductDTO product = new ProductDTO();
                product.setName((String) row[13]);
                product.setCreateDate(((Timestamp) row[14]).toInstant());
                product.setCode((String) row[15]);
                product.setUpdateName((String) row[16]);
                product.setIdBrand(((Number) row[17]).longValue());
                product.setIdCategory(((Number) row[18]).longValue());
                product.setIdMaterial(((Number) row[19]).longValue());
                product.setIdSole(((Number) row[20]).longValue());
                product.setDescription((String) row[21]);
                product.setStatus((Integer) row[22]);
                product.setIdel((Integer) row[23]);

                BrandDTO brand = new BrandDTO();
                brand.setName((String) row[24]);
                product.setBrandDTO(brand);

                CategoryDTO category = new CategoryDTO();
                category.setName((String) row[25]);
                product.setCategoryDTO(category);

                discountDetail.setDiscountAdminDTO(discount);
                discountDetail.setProductDTO(product);

                discountDetails.add(discountDetail);
            }

            return discountDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    @Override
    public ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO discountDetailAdminDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();

        // Chuyển DTO sang Entity cho DiscountAdmin
        Discount discountAdminEntity = discountAdminMapper.toEntity(discountDetailAdminDTO.getDiscountAdminDTO());
        discountAdminEntity.setCode("GG" + Instant.now().getEpochSecond());
        discountAdminEntity.setCreateDate(Date.valueOf(LocalDate.now()));
        discountAdminEntity.setStatus(0);
        discountAdminEntity.setIdel(0);

//        String startDateStr = discountDetailAdminDTO.getDiscountAdminDTO().getStartDateStr();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate localStartDate = LocalDate.parse(startDateStr, formatter);
//        Instant endDateInstant = localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//        String endDateStr = discountDetailAdminDTO.getDiscountAdminDTO().getStartDateStr();
//        LocalDate localStartDate2 = LocalDate.parse(endDateStr, formatter);
//        Instant startDateInstant = localStartDate2.atStartOfDay(ZoneId.systemDefault()).toInstant();

        discountAdminEntity.setStartDate(discountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
        discountAdminEntity.setEndDate(discountDetailAdminDTO.getDiscountAdminDTO().getEndDate());

        discountAdminEntity = discountAdminRepository.save(discountAdminEntity);
        for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
            DiscountDetail discountDetailEntity = discountDetailAdminMapper.toEntity(discountDetailAdminDTO);
            discountDetailEntity.setIdDiscount(discountAdminEntity.getId());
            discountDetailEntity.setIdProduct(discountDetailAdminDTO.getProductDTOList().get(i).getId());
            discountDetailRepository.save(discountDetailEntity);
        }
        serviceResult.setData(discountDetailAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<DiscountDetailAdminDTO> updateDiscount(Long idDiscount, DiscountDetailAdminDTO updatedDiscountDetailAdminDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();

        // Kiểm tra xem discountDetailId có tồn tại trong cơ sở dữ liệu không
        Optional<DiscountDetail> existingDiscount = discountDetailRepository.findById(updatedDiscountDetailAdminDTO.getId());

        if (existingDiscount.isPresent()) {
            // Cập nhật thông tin của bản ghi đã tồn tại dựa trên updatedDiscountDetailAdminDTO
            DiscountDetail existingEntity = existingDiscount.get();
            existingEntity.setDiscountType(Integer.valueOf(updatedDiscountDetailAdminDTO.getDiscountType()));
            existingEntity.setStatus(1);
            existingEntity.setReducedValue(new BigDecimal(updatedDiscountDetailAdminDTO.getReducedValue()));
            for (int i = 0; i < updatedDiscountDetailAdminDTO.getProductDTOList().size(); i++) {
               existingEntity.setIdProduct(updatedDiscountDetailAdminDTO.getProductDTOList().get(i).getId());
            }

            Optional<Discount> discountAdmin= discountAdminRepository.findById(idDiscount);
            if(discountAdmin.isPresent()){
                Discount discount=discountAdmin.get();
//

                discount.setStartDate(updatedDiscountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
                discount.setEndDate(updatedDiscountDetailAdminDTO.getDiscountAdminDTO().getEndDate());
                discount.setName(updatedDiscountDetailAdminDTO.getDiscountAdminDTO().getName());
                discount.setDescription(updatedDiscountDetailAdminDTO.getDiscountAdminDTO().getDescription());
                System.out.println(discount.toString());
                discountDetailRepository.save(existingEntity);
                discountAdminRepository.save(discount);
                serviceResult.setData(updatedDiscountDetailAdminDTO);
                serviceResult.setMessage("Cập nhật thành công!");
                serviceResult.setStatus(HttpStatus.OK);
            }

        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }


    @Override
    public ServiceResult<Void> deleteDiscount(Long idDiscount) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        // Chuyển DTO sang Entity cho DiscountAdmin
        Optional<Discount> discount=discountAdminRepository.findById(idDiscount);
        if (discount.isPresent()) {
        Discount discount1=discount.get();
        discount1.setIdel(1);
            discountAdminRepository.save(discount1); // Lưu lại thay đổi vào cơ sở dữ liệu
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
    @Override
    public List<DiscountDetailAdminDTO> getDetailDiscount(long id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT\n" +
                    "    dd.id,\n" +
                    "    dd.id_product,\n" +
                    "    dd.id_discount,\n" +
                    "    dd.reduced_value,\n" +
                    "    dd.discount_type,\n" +
                    "    dd.status,\n" +
                    "    d.code,\n" +
                    "    d.id,\n" +
                    "    d.name,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel,\n" +
                    "    p.name AS product_name,\n" +
                    "    p.code AS product_code,\n" +
                    "    p.id AS product_id,\n" +
                    "    p.id_brand,\n" +
                    "    p.id_category,\n" +
                    "    p.id_material,\n" +
                    "    p.id_sole,\n" +
                    "    p.description AS product_description,\n" +
                    "    p.status AS product_status,\n" +
                    "    p.idel AS product_idel,\n" +
                    "    b.name AS brand_name, -- Tên thương hiệu\n" +
                    "    c.name AS category_name -- Tên danh mục\n" +
                    "FROM\n" +
                    "    discount d\n" +
                    "LEFT JOIN\n" +
                    "   discount_detail dd \n" +
                    "            ON dd.id_discount = d.id \n" +
                    "LEFT JOIN\n" +
                    "    product p ON dd.id_product = p.id\n" +
                    "LEFT JOIN\n" +
                    "    brand b ON p.id_brand = b.id -- Liên kết với bảng brand\n" +
                    "LEFT JOIN\n" +
                    "    category c ON p.id_category = c.id -- Liên kết với bảng category\n" +
                    "WHERE d.id = :id"); // Thêm điều kiện WHERE để lọc theo id

            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            query.setParameter("id", id); // Đặt giá trị tham số id
            List<Object[]> resultList = query.getResultList();
            List<DiscountDetailAdminDTO> discountDetails = new ArrayList<>();
            for (Object[] row : resultList) {
                DiscountDetailAdminDTO discountDetail = new DiscountDetailAdminDTO();

                discountDetail.setId(row[0] != null ? Long.valueOf(row[0].toString()) : null);
                discountDetail.setIdProduct(row[1] != null ? row[1].toString() : null);
                discountDetail.setIdDiscount(row[2] != null ? row[2].toString() : null);
                discountDetail.setReducedValue(row[3] != null ? row[3].toString() : null);
                discountDetail.setDiscountType(row[4] != null ? row[4].toString() : null);
                discountDetail.setStatus(row[5] != null ? Integer.parseInt(row[5].toString()) : 0);

                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setCode(row[6] != null ? row[6].toString() : null);
                discount.setId(row[7] != null ? Long.valueOf(row[7].toString()) : null);
                discount.setName(row[8] != null ? row[8].toString() : null);
                discount.setStartDate(row[9] != null ? (Date) row[9] : null);
                discount.setEndDate(row[10] != null ? (Date) row[10] : null);
                discount.setDescription(row[11] != null ? row[11].toString() : null);
                discount.setIdel(row[12] != null ? row[12].toString() : null);

                ProductDTO product = new ProductDTO();
                product.setName(row[13] != null ? (String) row[13] : null);
                product.setCode(row[14] != null ? (String) row[14] : null);
                product.setId(row[15] != null ? Long.valueOf(row[15].toString()) : null);
                product.setIdBrand(row[16] != null ? ((Number) row[16]).longValue() : null);
                product.setIdCategory(row[17] != null ? ((Number) row[17]).longValue() : null);
                product.setIdMaterial(row[18] != null ? ((Number) row[18]).longValue() : null);
                product.setIdSole(row[19] != null ? ((Number) row[19]).longValue() : null);
                product.setDescription(row[20] != null ? (String) row[20] : null);
                product.setStatus(row[21] != null ? (Integer) row[21] : null);
                product.setIdel(row[22] != null ? (Integer) row[22] : null);

                BrandDTO brand = new BrandDTO();
                brand.setName(row[23] != null ? (String) row[23] : null);
                product.setBrandDTO(brand);

                CategoryDTO category = new CategoryDTO();
                category.setName(row[24] != null ? (String) row[24] : null);
                product.setCategoryDTO(category);

                discountDetail.setDiscountAdminDTO(discount);
                discountDetails.add(discountDetail);
                List<ProductDTO> productList = new ArrayList<>();
                for (int i = 0; i < discountDetails.size(); i++) {
                    productList.add(product);
                    discountDetails.get(i).setProductDTOList(productList);
                }
                System.out.println(productList.size());

            }

            return discountDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDTO> getAllProduct() {
        try {
            String sql = "SELECT p.id, p.code, p.name, b.name as brand_name, c.name as category_name, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM product p\n" +
                    "JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "LEFT JOIN order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN brand b ON p.id_brand = b.id\n" +
                    "LEFT JOIN category c ON p.id_category = c.id\n" +
                    "GROUP BY p.id, p.code, p.name, brand_name, category_name\n" +
                    "ORDER BY total_sold;";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<ProductDTO> productDTOList = new ArrayList<>();

            for (Object[] row : resultList) {
                ProductDTO product = new ProductDTO();
                product.setId(((Number) row[0]).longValue());
                product.setCode((String) row[1]);
                product.setName((String) row[2]);
                BrandDTO brand = new BrandDTO();
                brand.setName((String) row[3]);
                product.setBrandDTO(brand);

                CategoryDTO category = new CategoryDTO();
                category.setName((String) row[4]);
                product.setCategoryDTO(category);
                product.setTotalSold(new BigDecimal(((Number) row[5]).doubleValue()));

                productDTOList.add(product);
            }

            return productDTOList;
        } catch (PersistenceException e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log it or throw a custom exception
            return null;
        }
    }


}
