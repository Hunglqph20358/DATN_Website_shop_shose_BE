package com.example.backend.core.admin.service.impl;
import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminCustomRepository;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.repository.ProductAdminRepository;
import com.example.backend.core.admin.service.DiscountAdminService;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.CellConfigDTO;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.commons.SheetConfigDTO;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountDetailAdminServiceImpl implements DiscountDetailAdminService {
    @Autowired
    private DiscountDetailAdminRepository discountDetailRepository;
    @Autowired
    private DiscountAdminService discountAdminService;
    @Autowired
    private DiscountDetailAdminMapper discountDetailAdminMapper;
    @Autowired
    private DiscountAdminRepository discountAdminRepository;
    @Autowired
    private DiscountAdminMapper discountAdminMapper;
    @Autowired
    private ProductAdminRepository productAdminRepository;
    @Autowired
    private ProductAdminMapper productAdminMapper;
    @Autowired
    private DiscountAdminCustomRepository discountAdminCustomRepository;
    @Autowired
    FileExportUtil fileExportUtil;



    @Override
    public List<DiscountAdminDTO> getAll() {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAll();
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllKichHoat() {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllKichHoat();
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllKhongKichHoat() {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllKhongKichHoat();
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllByCodeOrName(String search) {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByCodeOrName(search);
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllByCategory(String category) {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByCategory(category);
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode) {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByProductNameOrCode(productNameOrCode);
        return list;
    }

    @Override
    public List<DiscountAdminDTO> getAllByBrand(String brand) {
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByBrand(brand);
        return list;
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
        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByDateRange(fromDate,toDate);
        return list;
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
        List<ProductAdminDTO> list= discountAdminCustomRepository.getAllProduct();
        return list;
    }
    @Override
    public byte[] exportExcelDiscount() throws IOException {
        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
        List<DiscountAdminDTO> discountAdminDTOS = discountAdminCustomRepository.getAll();
        sheetConfigList = getDataForExcel("Danh Sách Giảm giá", discountAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
        try {
            String title = "DANH SÁCH SẢN PHẨM";
            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
        }
    }
    @Override
    public byte[] exportExcelProductErrors(List<DiscountAdminDTO> listDataErrors) throws IOException {
        List<SheetConfigDTO> sheetConfigList = getDataForExcel("DANH SÁCH SẢN PHẨM", listDataErrors, new ArrayList<>(), AppConstant.EXPORT_ERRORS);
        try {
            return fileExportUtil.exportXLSX(true, sheetConfigList, null);
        } catch (IOException | ReflectiveOperationException ioE) {
            throw new IOException("Lỗi Export Error" + ioE.getMessage(), ioE);
        }
    }

    private List<SheetConfigDTO> getDataForExcel(String sheetName,
                                                 List<DiscountAdminDTO> listDataSheet,
                                                 List<SheetConfigDTO> sheetConfigList,
                                                 Long exportType) {
        SheetConfigDTO sheetConfig = new SheetConfigDTO();
        String[] headerArr = null;
        if (AppConstant.EXPORT_DATA.equals(exportType)) {
            headerArr =
                    new String[]{
                            "STT",
                            "Mã Giảm Giá",
                            "Tên Giảm Giá",
                            "Ngày Bắt Đầu",
                            "Ngày Kết Thúc",
                            "Danh Mục",
                            "Chất Liệu",
                            "Đế giày",
                            "Gía sản phẩm",
                            "Mô Tả",
                            "Trạng Thái",
                    };
        } else if(AppConstant.EXPORT_ERRORS.equals(exportType)){
            headerArr =
                    new String[]{
                            "STT",
                            "Mã Giảm Giá",
                            "Tên Giảm Giá",
                            "Ngày Bắt Đầu",
                            "Ngày Kết Thúc",
                            "Hãng (*) \n",
                            "Danh Mục (*) \n",
                            "Chất Liệu (*) \n",
                            "Đế giày (*) \n",
                            "Gía sản phẩm (*) \n (Gía phải là số)",
                            "Mô Tả \n",
                            "Trạng Thái \n",
                            "Ảnh sản phẩm (*) \n (Link ảnh và cách nhau bởi dấu phẩy)",
                            "Mô tả lỗi"
                    };
        } else {
            headerArr =
                    new String[]{
                            "STT",
                            "Mã Giảm Giá",
                            "Tên Giảm Giá",
                            "Ngày Bắt Đầu",
                            "Ngày Kết Thúc",
                            "Hãng (*) \n",
                            "Danh Mục (*) \n",
                            "Chất Liệu (*) \n",
                            "Đế giày (*) \n",
                            "Gía sản phẩm (*) \n (Gía phải là số)",
                            "Mô Tả \n",
                            "Trạng Thái \n",
                            "Ảnh sản phẩm (*) \n (Link ảnh và cách nhau bởi dấu phẩy)",
                    };
        }
        sheetConfig.setSheetName(sheetName);
        sheetConfig.setHeaders(headerArr);
        int recordNo = 1;
        List<CellConfigDTO> cellConfigCustomList = new ArrayList<>();
        if (!AppConstant.EXPORT_DATA.equals(exportType)) {
            List<String> listDiscount = discountAdminService.getAllDiscountExport();
            cellConfigCustomList.add(
                    new CellConfigDTO("discount", AppConstant.ALIGN_LEFT, listDiscount.toArray(new String[0]), 1, 99, 3, 3)
            );
            if (AppConstant.EXPORT_TEMPLATE.equals(exportType)) {
                for (int i = 1; i < 4; i++) {
                    DiscountAdminDTO data = new DiscountAdminDTO();
                    data.setRecordNo(i);
                    listDataSheet.add(data);
                }
            }
        } else {
            for (DiscountAdminDTO item : listDataSheet) {
                item.setRecordNo(recordNo++);
            }
        }
        List<CellConfigDTO> cellConfigList = new ArrayList<>();
        sheetConfig.setList(listDataSheet);
        cellConfigList.add(new CellConfigDTO("recordNo", AppConstant.ALIGN_LEFT, AppConstant.NO));
        cellConfigList.add(new CellConfigDTO("code", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("createDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("brandName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("categoryName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("materialName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("soleHeight", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("price", AppConstant.ALIGN_LEFT, AppConstant.DOUBLE));
        cellConfigList.add(new CellConfigDTO("description", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("status", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
        if (AppConstant.EXPORT_DATA.equals(exportType) || AppConstant.EXPORT_ERRORS.equals(exportType)) {
            cellConfigList.add(new CellConfigDTO("messageStr", AppConstant.ALIGN_LEFT, AppConstant.ERRORS));
        }
        sheetConfig.setHasIndex(false);
        sheetConfig.setHasBorder(true);
        sheetConfig.setExportType(exportType.intValue());
        sheetConfig.setCellConfigList(cellConfigList);
        sheetConfig.setCellCustomList(cellConfigCustomList);
        sheetConfigList.add(sheetConfig);
        return sheetConfigList;
    }


}
