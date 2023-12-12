package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminCustomRepository;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.repository.ProductAdminRepository;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.dto.CategoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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
    private ProductAdminRepository productAdminRepository;
    @Autowired
    private ProductAdminMapper productAdminMapper;
    @Autowired
    EntityManager entityManager;

    @Autowired
    private DiscountAdminCustomRepository discountAdminCustomRepository;

    @Override
    public List<DiscountAdminDTO> getAllDiscount() {
        List<DiscountAdminDTO> list = discountAdminMapper.toDto(discountAdminRepository.getAll());
        return list;
    }


    @Override
    public List<DiscountAdminDTO> getAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +

                    "   d.id, " +
                    " d.code,\n" +
                    "    d.name,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +

                    "FROM discount d " +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "where dele=0 " +
                    "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description,d.idel;\n");


            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();

            List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();

                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                if (discount.getStatus() == 1) {
                    discount.setIdel(0);//Nếu hết hạn thì sẽ thành ko hiển thị
                }
                discounts.add(discount);
            }
            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllKichHoat() {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id,  d.code,\n" +
                    "    d.name,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel," +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "where idel = 1 and dele= 0 \n" +
                    "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description, d.idel  \n\n");


            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();

            List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();

                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                if (discount.getStatus() == 1) {
                    discount.setIdel(0);//Nếu hết hạn thì sẽ thành ko hiển thị
                }

                discounts.add(discount);
            }
            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllKhongKichHoat() {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id,  d.code,\n" +
                    "    d.name,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "where idel = 0 and dele=0 \n" +
                    "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description, d.idel  \n\n");


            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();

            List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();

                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                if (discount.getStatus() == 1) {
                    discount.setIdel(0);//Nếu hết hạn thì sẽ thành ko hiển thị
                }

                discounts.add(discount);
            }
            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllByCodeOrName(String search) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id, " +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d " +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount" +
                    " where dele=0\n");

            // Kiểm tra xem có search được cung cấp không
            if (search != null && !search.isEmpty()) {
                sql.append("WHERE LOWER(d.code) LIKE LOWER(:search) OR LOWER(d.name) LIKE LOWER(:search)\n");
            }

            sql.append("GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel;\n");

            Query query = entityManager.createNativeQuery(sql.toString());

            // Nếu có search, thiết lập tham số
            if (search != null && !search.isEmpty()) {
                query.setParameter("search", "%" + search + "%");
            }

            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                if (discount.getStatus() == 1) {
                    discount.setIdel(0); // Nếu hết hạn thì sẽ thành không hiển thị
                } else {
                    discount.setIdel(1);
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllByCategory(String category) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id,\n" +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel,\n" +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d\n" +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                    "LEFT JOIN category AS c ON p.id_category = c.id\n" +
                    "WHERE LOWER(c.name) LIKE LOWER(:category)\n" +
                    "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel " +
                    " where dele=0 \n");

            Query query = entityManager.createNativeQuery(sql.toString());
            if (category != null && !category.isEmpty()) {
                query.setParameter("category", "%" + category + "%");
            }


            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                if (discount.getStatus() == 1) {
                    discount.setIdel(0); // Nếu hết hạn thì sẽ thành không hiển thị
                } else {
                    discount.setIdel(1);
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id, " +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d\n" +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                    "WHERE LOWER(p.name) LIKE LOWER(:productNameOrCode) OR LOWER(p.code) LIKE LOWER(:productNameOrCode)\n" +
                    "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel" +
                    " where dele=0 ;\n");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("productNameOrCode", "%" + productNameOrCode + "%");

            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                if (discount.getStatus() == 1) {
                    discount.setIdel(0); // Nếu hết hạn thì sẽ thành không hiển thị
                } else {
                    discount.setIdel(1);
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAllByBrand(String brand) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id, " +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d\n" +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                    "LEFT JOIN brand AS b ON p.id_brand = b.id\n" +
                    "WHERE LOWER(b.name) LIKE LOWER(:brand)\n" +
                    "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel" +
                    " where dele=0 ;\n");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("brand", "%" + brand + "%");

            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                if (discount.getStatus() == 1) {
                    discount.setIdel(0); // Nếu hết hạn thì sẽ thành không hiển thị
                } else {
                    discount.setIdel(1);
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ServiceResult<Void> deleteDiscount(Long discountId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<Discount> discount = discountAdminRepository.findById(discountId);

        if (discount.isPresent()) {
            Discount discount1 = discount.get();
            discount1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            discountAdminRepository.save(discount1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

    @Override
    public List<DiscountAdminDTO> getAllByDateRange(Date fromDate, Date toDate) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id, " +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel, " +
                    "COUNT(od.id) AS used_count\n" +
                    ", d.quantity " +
                    "FROM discount d " +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "WHERE d.start_date >= :fromDate AND d.end_date <= :toDate\n" +
                    "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel" +
                    " where dele=0;\n");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(Integer.valueOf(row[7].toString()));
                discount.setQuantity(Integer.valueOf(row[8].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                if (discount.getStatus() == 1) {
                    discount.setIdel(0); // Nếu hết hạn thì sẽ thành không hiển thị
                } else {
                    discount.setIdel(1);
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO discountDetailAdminDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();
        for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
            ProductAdminDTO productDTO1 = discountDetailAdminDTO.getProductDTOList().get(i);
            BigDecimal reducedValue1 = discountDetailAdminDTO.getReducedValue();

            if (reducedValue1 == null && reducedValue1.doubleValue() > productDTO1.getPrice().doubleValue()) {
                serviceResult.setData(null);
                serviceResult.setMessage("ERROR");
                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
                return serviceResult;

            }
        }
        // Chuyển DTO sang Entity cho DiscountAdmin
        Discount discountAdminEntity = discountAdminMapper.toEntity(discountDetailAdminDTO.getDiscountAdminDTO());
        discountAdminEntity.setCode("GG" + Instant.now().getEpochSecond());
        Date nowDate = new Date();
        discountAdminEntity.setCreateDate(nowDate);
        discountAdminEntity.setStatus(0);
        discountAdminEntity.setIdel(0);
        discountAdminEntity.setDelete(0);
        discountAdminEntity.setQuantity(discountDetailAdminDTO.getDiscountAdminDTO().getQuantity());
        discountAdminEntity.setStartDate(discountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
        discountAdminEntity.setEndDate(discountDetailAdminDTO.getDiscountAdminDTO().getEndDate());

        discountAdminEntity = discountAdminRepository.save(discountAdminEntity);
        DiscountDetail discountDetailEntity = discountDetailAdminMapper.toEntity(discountDetailAdminDTO);
        for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
            ProductAdminDTO productDTO = discountDetailAdminDTO.getProductDTOList().get(i);
            discountDetailEntity.setIdProduct(productDTO.getId());
            BigDecimal reducedValue = discountDetailAdminDTO.getReducedValue();
            if (reducedValue != null && reducedValue.doubleValue() <= productDTO.getPrice().doubleValue()) {
                // Nếu giảm giá không lớn hơn hoặc bằng giá sản phẩm, thì thực hiện lưu thông tin giảm giá
                discountDetailEntity.setIdDiscount(discountAdminEntity.getId());
                discountDetailEntity.setReducedValue(discountDetailAdminDTO.getReducedValue());
                discountDetailEntity.setDiscountType(discountDetailAdminDTO.getDiscountType());
                if (discountDetailEntity.getDiscountType() == 0) {
                    discountDetailEntity.setMaxReduced(discountDetailAdminDTO.getReducedValue());
                }
            }
            discountDetailRepository.save(discountDetailEntity);
        }

        serviceResult.setData(discountDetailAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<DiscountDetailAdminDTO> updateDiscount(DiscountDetailAdminDTO discountDetailAdminDTO) {
        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();
        Optional<Discount> discountAdminOptional = discountAdminRepository.findById(discountDetailAdminDTO.getDiscountAdminDTO().getId());

        if (discountAdminOptional.isPresent()) {
            Discount discountAdminEntity = discountAdminOptional.get();
            discountAdminEntity.setStatus(0);
            discountAdminEntity.setIdel(0);
            discountAdminEntity.setDelete(0);
            discountAdminEntity.setQuantity(discountDetailAdminDTO.getDiscountAdminDTO().getQuantity());
            discountAdminEntity.setStartDate(discountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
            discountAdminEntity.setEndDate(discountDetailAdminDTO.getDiscountAdminDTO().getEndDate());
            discountAdminEntity.setDescription(discountDetailAdminDTO.getDiscountAdminDTO().getDescription());

            discountAdminEntity = discountAdminRepository.save(discountAdminEntity);


            discountAdminCustomRepository.deleteAllDiscountDetailByDiscount(discountDetailAdminDTO.getIdDiscount());

            for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
                DiscountDetail discountDetail = new DiscountDetail();
                discountDetail.setIdDiscount(discountAdminEntity.getId());
                discountDetail.setIdProduct(discountDetailAdminDTO.getProductDTOList().get(i).getId());
                discountDetail.setDiscountType(discountDetailAdminDTO.getDiscountType());
                discountDetail.setReducedValue(discountDetailAdminDTO.getReducedValue());
                discountDetail.setStatus(0);
                discountDetail.setMaxReduced(discountDetailAdminDTO.getMaxReduced() != null ? discountDetailAdminDTO.getMaxReduced() : null);
                discountDetailRepository.save(discountDetail);
            }
        serviceResult.setData(discountDetailAdminDTO);
        serviceResult.setMessage("Cập nhật thành công!");
        serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }



    @Override
    public ServiceResult<Void> KichHoat(Long idDiscount) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);

        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();

            if (discount.getIdel() == 1) {
                discount.setIdel(0);

            } else {
                discount.setIdel(1);
            }
            discountAdminRepository.save(discount); // Lưu lại thay đổi vào cơ sở dữ liệu
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }


    @Override
    public DiscountAdminDTO getDetailDiscount(Long id) {
        Discount discount = discountAdminRepository.findById(id).get();
        if (discount == null) {
            return null;
        }
        DiscountAdminDTO discountAdminDTO = discountAdminMapper.toDto(discount);
        List<DiscountDetail> discountDetailList = discountDetailRepository.findAllByDiscount(discount.getId());

        List<ProductAdminDTO> lstPruct = new ArrayList<>();
        if (discountDetailList.size() > 0) {
            for (int i = 0; i < discountDetailList.size(); i++) {
                ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(discountDetailList.get(i).getIdProduct()).orElse(null));
                lstPruct.add(productAdminDTO);

            }
            discountAdminDTO.setReducedValue(discountDetailList.get(0).getReducedValue());
            discountAdminDTO.setDiscountType(discountDetailList.get(0).getDiscountType());
            discountAdminDTO.setProductDTOList(lstPruct);
        }
        return discountAdminDTO;
    }


    public List<ProductAdminDTO> getAllProduct() {
        try {
            String sql = "SELECT p.id, p.code, p.name, b.name as brand_name, c.name as category_name, IFNULL(SUM(od.quantity), 0) AS total_sold , p.price   \n" +
                    "FROM product p\n" +
                    "JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "LEFT JOIN order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN brand b ON p.id_brand = b.id\n" +
                    "LEFT JOIN category c ON p.id_category = c.id\n" +
                    "GROUP BY p.id, p.code, p.name, brand_name, category_name\n" +
                    "ORDER BY total_sold;";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<ProductAdminDTO> productDTOList = new ArrayList<>();

            for (Object[] row : resultList) {
                ProductAdminDTO product = new ProductAdminDTO();
                product.setId(((Number) row[0]).longValue());
                product.setCode((String) row[1]);
                product.setName((String) row[2]);
                product.setPrice(new BigDecimal(row[6].toString()));
                BrandAdminDTO brand = new BrandAdminDTO();
                brand.setName((String) row[3]);
                product.setBrandAdminDTO(brand);

                CategoryAdminDTO category = new CategoryAdminDTO();
                category.setName((String) row[4]);
                product.setCategoryAdminDTO(category);
                product.setTotalQuantity((((Number) row[5]).intValue()));

                productDTOList.add(product);
            }

            return productDTOList;
        } catch (PersistenceException e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log it or throw a custom exception
            return null;
        }
    }

    public List<ProductAdminDTO> getProduct(String code, String name) {
        try {
            String jpql = "SELECT NEW com.example.backend.core.admin.dto.ProductAdminDTO(p.id, p.code, p.name,p.price,pd.quantity) " +
                    "FROM Product p, ProductDetail pd " +  // Sử dụng CROSS JOIN
                    "WHERE p.name LIKE :productName AND p.code LIKE :productCode";


            TypedQuery<ProductAdminDTO> query = entityManager.createQuery(jpql, ProductAdminDTO.class);
            query.setParameter("productName", "%" + name + "%");
            query.setParameter("productCode", "%" + code + "%");

            return query.getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
    }

}
