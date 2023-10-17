package com.example.backend.core.view.repository.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.repository.ProductCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    EntityManager entityManager;


    @Override
    public List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu) {
        try {
            StringBuilder sql = new StringBuilder("SELECT p.id, p.code, p.name, pd.listed_price, pd.price, images.image_names, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM product p\n" +
                    "JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "LEFT JOIN order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN (\n" +
                    "    SELECT id_product, GROUP_CONCAT(image_name) AS image_names\n" +
                    "    FROM images\n" +
                    "    GROUP BY id_product\n" +
                    ") images ON images.id_product = p.id ");
            sql.append("  left join brand b on b.id = p.id_brand ");
            sql.append(" {1} ");
            sql.append("GROUP BY p.id, p.code, p.name,pd.listed_price , pd.price  " +
                    "  ORDER BY total_sold DESC limit 8");
            String sqlStr = sql.toString();
            if (null != thuongHieu && thuongHieu > 0) {
                sqlStr = sqlStr.replace("{1}", "  where b.id = :idBrand");
            } else {
                sqlStr = sqlStr.replace("{1}", " ");
            }
            Query query = entityManager.createNativeQuery(sqlStr);
            if(null != thuongHieu && thuongHieu > 0){
                query.setParameter("idBrand", thuongHieu);
            }
            List<Object[]> lst = query.getResultList();
            List<ProductDTO> lstProduct = new ArrayList<>();
            for (Object[] obj : lst) {
                ProductDTO productDTO = new ProductDTO();
                List<ImagesDTO> imagesDTOLis = new ArrayList<>();
                productDTO.setId(((Number) obj[0]).longValue());
                productDTO.setCode((String) obj[1]);
                productDTO.setName((String) obj[2]);
                productDTO.setListedPrice((BigDecimal) obj[3]);
                productDTO.setPrice((BigDecimal) obj[4]);

                String imagesString = (String) obj[5];
                if (imagesString != null && !imagesString.isEmpty()) {
                    for (String str : imagesString.split(",")) {
                        if (!str.trim().isEmpty()) { // Kiểm tra và bỏ qua chuỗi trống
                            ImagesDTO imagesDTO = new ImagesDTO();
                            imagesDTO.setImageName(str.trim());
                            imagesDTOLis.add(imagesDTO);
                        }
                    }
                }

                productDTO.setImagesDTOList(imagesDTOLis);
                lstProduct.add(productDTO);
            }
            return lstProduct;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
