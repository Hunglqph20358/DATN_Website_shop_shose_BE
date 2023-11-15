package com.example.backend.core.view.repository.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.repository.DiscountDetailRepository;
import com.example.backend.core.view.repository.DiscountRepository;
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

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Override
    public List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu) {
        try {
            StringBuilder sql = new StringBuilder("SELECT p.id, p.code, p.name, p.price, images.image_names, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
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
            sql.append("GROUP BY p.id, p.code, p.name, p.price  " +
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
                productDTO.setPrice((BigDecimal) obj[3]);

                String imagesString = (String) obj[4];
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
                List<Discount> discountList = discountRepository.getDiscountConApDung();
                for (int i = 0; i < discountList.size(); i++) {
                    List<DiscountDetail> discountDetailList = discountDetailRepository.findByIdDiscount(discountList.get(i).getId());
                    for (int j = 0; j < discountDetailList.size(); j++) {
                        if(productDTO.getId().equals(discountDetailList.get(i).getIdProduct()) ){
                            if(discountDetailList.get(i).getDiscountType() == 0){
                                productDTO.setReducePrice(discountDetailList.get(i).getReducedValue());
                                productDTO.setPercentageReduce(Math.round(discountDetailList.get(i).getReducedValue().divide(productDTO.getPrice()).multiply(new BigDecimal(100)).floatValue()));
                            }
                            if(discountDetailList.get(i).getDiscountType() == 1){
                                productDTO.setReducePrice(discountDetailList.get(i).getReducedValue().divide(new BigDecimal(100).multiply(productDTO.getPrice())));
                                productDTO.setPercentageReduce(discountDetailList.get(i).getReducedValue().intValue());
                            }
                        }
                    }
                }
                lstProduct.add(productDTO);
            }
            return lstProduct;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
