package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.mysql.cj.util.SaslPrep;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.descriptor.java.BigDecimalJavaType;
import org.hibernate.type.descriptor.java.InstantJavaType;
import org.hibernate.type.descriptor.java.IntegerJavaType;
import org.hibernate.type.descriptor.java.StringJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProductAdminCustomRepositoryImpl implements ProductAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ProductAdminDTO> getAllProductExport() {
        List<ProductAdminDTO> lstProduct = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("select p.code as code , p.name as name , p.create_date as createDate, b.name as brandName , c.name as categoryName, \n" +
                    "m.name as materialName , concat(s.sole_height, ' cm') as soleHeight , p.price as price , p.description as description, p.status\n" +
                    "from product p \n" +
                    "left join brand b on p.id_brand = b.id \n" +
                    "left join category c on p.id_category = c.id\n" +
                    "left join material m on p.id_material = m.id\n" +
                    "left join sole s on p.id_sole = s.id");
            Query query = entityManager.createNativeQuery(sql.toString());
            List<Object[]> lstObj = query.getResultList();
            for (Object[] obj : lstObj) {
                ProductAdminDTO productAdminDTO = new ProductAdminDTO();
                productAdminDTO.setCode((String) obj[0]);
                productAdminDTO.setName((String) obj[1]);
                productAdminDTO.setCreateDate(((Timestamp) obj[2]).toInstant());
                productAdminDTO.setBrandName((String) obj[3]);
                productAdminDTO.setCategoryName((String) obj[4]);
                productAdminDTO.setMaterialName((String) obj[5]);
                productAdminDTO.setSoleHeight((String) obj[6]);
                productAdminDTO.setPrice(new BigDecimal(obj[7].toString()));
                productAdminDTO.setDescription((String) obj[8]);
                productAdminDTO.setStatus((Integer) obj[9]);
                lstProduct.add(productAdminDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }
}
