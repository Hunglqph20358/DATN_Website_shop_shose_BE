package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.ImagesAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.ProductDTO;
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

    @Override
    public List<ProductAdminDTO> topProductBestSeller() {
        List<ProductAdminDTO> lstProduct = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.id, p.code, p.name, p.price, images.image_names, b.name, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM product p\n" +
                    "left JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "join brand b on b.id = p.id_brand\n" +
                    "LEFT JOIN order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN (\n" +
                    "    SELECT id_product, GROUP_CONCAT(image_name) AS image_names\n" +
                    "    FROM images\n" +
                    "    GROUP BY id_product\n" +
                    ") images ON images.id_product = p.id\n" +
                    "left join `order` o on o.id = od.id_order\n" +
                    "where month(o.payment_date) = month(now())\n" +
                    "GROUP BY p.id, p.code, p.name, p.price, images.image_names, b.name\n" +
                    "ORDER BY total_sold DESC\n" +
                    "LIMIT 5;");
            Query query  = entityManager.createNativeQuery(sql.toString());
            List<Object[]> lst = query.getResultList();
            for (Object[] obj: lst) {
                ProductAdminDTO dto = new ProductAdminDTO();
                List<ImagesAdminDTO> imagesAdminDTOLis = new ArrayList<>();
                dto.setId(((Number) obj[0]).longValue());
                dto.setCode((String) obj[1]);
                dto.setName((String) obj[2]);
                dto.setPrice((BigDecimal) obj[3]);
                dto.setBrandName((String) obj[5]);
                dto.setTotalBestSeller(((Number) obj[6]).intValue());
                String imagesString = (String) obj[4];
                if (imagesString != null && !imagesString.isEmpty()) {
                    for (String str : imagesString.split(",")) {
                        if (!str.trim().isEmpty()) { // Kiểm tra và bỏ qua chuỗi trống
                            ImagesAdminDTO imagesAdminDTO = new ImagesAdminDTO();
                            imagesAdminDTO.setImageName(str.trim());
                            imagesAdminDTOLis.add(imagesAdminDTO);
                        }
                    }
                }
                dto.setImagesDTOList(imagesAdminDTOLis);
                lstProduct.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }
}
