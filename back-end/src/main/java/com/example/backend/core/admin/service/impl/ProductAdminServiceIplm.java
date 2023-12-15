package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.BrandAdminService;
import com.example.backend.core.admin.service.CategoryAdminService;
import com.example.backend.core.admin.service.MaterialAdminService;
import com.example.backend.core.admin.service.ProductAdminService;
import com.example.backend.core.admin.service.SoleAdminService;
import com.example.backend.core.commons.CellConfigDTO;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.FileImportUtil;
import com.example.backend.core.commons.ResponseImportDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.commons.SheetConfigDTO;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.*;
import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.dto.CategoryDTO;
import com.example.backend.core.view.dto.MaterialDTO;
import com.example.backend.core.view.dto.SoleDTO;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductAdminServiceIplm implements ProductAdminService {
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
    @Autowired
    private StaffRepository strp;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductDetailAdminMapper productDetailMapper;
    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;
    @Autowired
    private ProductAdminCustomRepository productAdminCustomRepository;

    @Autowired
    private BrandAdminService brandAdminService;
    @Autowired
    private CategoryAdminService categoryAdminService;
    @Autowired
    private MaterialAdminService materialAdminService;
    @Autowired
    private SoleAdminService soleAdminService;

    @Autowired
    FileExportUtil fileExportUtil;

    @Autowired
    private ImageAdminRepository imageAdminRepository;

    @Autowired
    private CategoryAdminRepository categoryAdminRepository;

    private ServiceResult<ProductAdminDTO> result = new ServiceResult<>();

    @Override
    public List<ProductAdminDTO> getAll() {

        List<ProductAdminDTO> list = productAdminMapper.toDto(prdrp.findAll());
        for (int i = 0; i < list.size(); i++) {
            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(slrp.findById(list.get(i).getIdSole()).get());
            list.get(i).setSoleAdminDTO(soleAdminDTO);
            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(mtrp.findById(list.get(i).getIdMaterial()).get());
            list.get(i).setMaterialAdminDTO(materialAdminDTO);
            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brrp.findById(list.get(i).getIdBrand()).get());
            list.get(i).setBrandAdminDTO(brandAdminDTO);
            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(ctrp.findById(list.get(i).getIdCategory()).get());
            list.get(i).setCategoryAdminDTO(categoryAdminDTO);
//          StaffDTO staffDTO = staffMapper.toDto(strp.findById(list.get(i).getStaffDTO().getId()).get());
//            list.get(i).setStaffDTO(staffDTO);
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
        product.setPrice(productAdminDTO.getPrice());
//        product.setCreateName(staffDTO.getFullname());
        this.prdrp.save(product);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(productAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id) {
        Optional<Product> optional = this.prdrp.findById(id);
        if (optional.isPresent()) {
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
            product.setPrice(productAdminDTO.getPrice());
            product = this.prdrp.save(product);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(productAdminMapper.toDto(product));

        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductAdminDTO> delete(Long id) {
        Optional<Product> optional = this.prdrp.findById(id);
        if (optional.isPresent()) {
            this.prdrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductAdminDTO> getById(Long id) {
        ServiceResult<ProductAdminDTO> result = new ServiceResult<>();
        Optional<Product> optional = this.prdrp.findById(id);

        if (optional.isPresent()) {
            Product product = optional.get();
            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(product);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Lấy thông tin thành công");
            result.setData(productAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy sản phẩm");
        }

        return result;
    }

    @Override
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

    @Override
    public List<ProductAdminDTO> findByNameLikeOrCodeLike(String param) {
        List<ProductAdminDTO> list = productAdminMapper.toDto(prdrp.findByNameLikeOrCodeLike("%" + param + "%", "%" + param + "%"));
        for (int i = 0; i < list.size(); i++) {
            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(slrp.findById(list.get(i).getIdSole()).get());
            list.get(i).setSoleAdminDTO(soleAdminDTO);
            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(mtrp.findById(list.get(i).getIdMaterial()).get());
            list.get(i).setMaterialAdminDTO(materialAdminDTO);
            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brrp.findById(list.get(i).getIdBrand()).get());
            list.get(i).setBrandAdminDTO(brandAdminDTO);
            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(ctrp.findById(list.get(i).getIdCategory()).get());
            list.get(i).setCategoryAdminDTO(categoryAdminDTO);
            ProductDetailAdminDTO productDetailAdminDTO = productDetailMapper.toDto(productDetailAdminRepository.findById(list.get(i).getId()).get());
            list.get(i).setProductDetailAdminDTO(productDetailAdminDTO);
        }

        return list;
    }

    @Override
    public List<ProductAdminDTO> findByName(String name) {
        return null;
    }

    @Override
    public byte[] exportExcelProduct() throws IOException {
        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
        List<ProductAdminDTO> productAdminDTOS = productAdminCustomRepository.getAllProductExport();
        sheetConfigList = getDataForExcel("Danh Sách Sản Phẩm", productAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
        try {
            String title = "DANH SÁCH SẢN PHẨM";
            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
        }
    }

    @Override
    public byte[] exportExcelTemplateProduct() throws IOException {
        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
        List<ProductAdminDTO> productAdminDTOS = new ArrayList<>();
        sheetConfigList = getDataForExcel("Danh Sách Sản Phẩm", productAdminDTOS, sheetConfigList, AppConstant.EXPORT_TEMPLATE);
        try {
            return fileExportUtil.exportXLSXTemplate(true, sheetConfigList, null);
        } catch (IOException | ReflectiveOperationException ioE) {
            throw new IOException("Lỗi Export Template" + ioE.getMessage(), ioE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult<ResponseImportDTO> importFileProduct(MultipartFile fileUploads, Long typeImport) throws IOException, ParseException {
        boolean check = FileImportUtil.checkValidFileFormat(fileUploads.getOriginalFilename());
        if (!check) {
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng", null);
        }
        if (!FileImportUtil.isNotNullOrEmpty(fileUploads))
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không tồn tại", null);
        //check extension file
        String extention = FilenameUtils.getExtension(fileUploads.getOriginalFilename());
        if (!AppConstant.EXTENSION_XLSX.equalsIgnoreCase(extention) && !AppConstant.EXTENSION_XLS.equalsIgnoreCase(extention)
        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng", null);
        List<List<String>> records;
        try {
            records = FileImportUtil.excelReader(fileUploads);
        } catch (IllegalStateException | IOException e) {
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Đọc file bị lỗi", null);
        }
        if (records.size() <= 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không có dữ liệu", null);
        records.remove(0);
        List<Product> dataSuccess = new ArrayList<>();
        List<ProductAdminDTO> dataErrors = new ArrayList<>();
        List<Brand> brandList = brrp.findAll();
        List<Category> categoryList = categoryAdminRepository.findAll();
        List<Material> materialList = mtrp.findAll();
        List<Sole> soleList = slrp.findAll();
        int countSuccess = 0;
        int countError = 0;
        int total = 0;
        for (List<String> myRecord : records) {
            if (myRecord.size() != 11)
                return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng file mẫu", null);
            ProductAdminDTO dto = processRecord(myRecord, brandList, categoryList, materialList, soleList, typeImport);
            if (!dto.getMessageErr().isEmpty()) {
                countError++;
                dataErrors.add(dto);
            } else {
                Product product = new Product(dto);
                dataSuccess.add(product);
                product = prdrp.save(product);
                if(AppConstant.IMPORT_UPDATE.equals(typeImport)){
                    imageAdminRepository.deleteByIdProduct(product.getId());
                }
                for (String str: dto.getImageNameImport().split(",")) {
                    Images images = new Images();
                    images.setIdProduct(product.getId());
                    images.setCreateDate(Instant.now());
                    images.setImageName(str.trim());
                    images = imageAdminRepository.save(images);
                }
                countSuccess++;
            }
            total++;
        }
        ResponseImportDTO responseImportDTO = new ResponseImportDTO(countError, countSuccess, total);
        responseImportDTO.setListErrors(dataErrors);
        responseImportDTO.setListSuccess(dataSuccess);
        return new ServiceResult<>(HttpStatus.OK, " Import thành công", responseImportDTO);
    }

    @Override
    public byte[] exportExcelProductErrors(List<ProductAdminDTO> listDataErrors) throws IOException {
        List<SheetConfigDTO> sheetConfigList = getDataForExcel("DANH SÁCH SẢN PHẨM", listDataErrors, new ArrayList<>(), AppConstant.EXPORT_ERRORS);
        try {
            return fileExportUtil.exportXLSX(true, sheetConfigList, null);
        } catch (IOException | ReflectiveOperationException ioE) {
            throw new IOException("Lỗi Export Error" + ioE.getMessage(), ioE);
        }
    }

    private List<SheetConfigDTO> getDataForExcel(String sheetName,
                                                 List<ProductAdminDTO> listDataSheet,
                                                 List<SheetConfigDTO> sheetConfigList,
                                                 Long exportType) {
        SheetConfigDTO sheetConfig = new SheetConfigDTO();
        String[] headerArr = null;
        if (AppConstant.EXPORT_DATA.equals(exportType)) {
            headerArr =
                    new String[]{
                            "STT",
                            "Mã Sản Phẩm",
                            "Tên Sản Phẩm",
                            "Ngày Tạo",
                            "Hãng",
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
                            "Mã Sản Phẩm (*) \n (maxlength = 250 kí tự)",
                            "Tên Sản Phẩm (*) \n (maxlength = 250 kí tự)",
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
                            "Mã Sản Phẩm (*) \n (maxlength = 250 kí tự)",
                            "Tên Sản Phẩm (*) \n (maxlength = 250 kí tự)",
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
            List<String> listBrand = brandAdminService.getAllBrandExport();
            List<String> lstCategory = categoryAdminService.getAllListExport();
            List<String> lstMaterial = materialAdminService.getAllListExport();
            List<String> lstSole = soleAdminService.getAllListExport();
            cellConfigCustomList.add(
                    new CellConfigDTO("brand", AppConstant.ALIGN_LEFT, listBrand.toArray(new String[0]), 1, 99, 3, 3)
            );
            cellConfigCustomList.add(
                    new CellConfigDTO("category", AppConstant.ALIGN_LEFT, lstCategory.toArray(new String[0]), 1, 99, 4, 4)
            );
            cellConfigCustomList.add(
                    new CellConfigDTO("material", AppConstant.ALIGN_LEFT, lstMaterial.toArray(new String[0]), 1, 99, 5, 5)
            );
            cellConfigCustomList.add(
                    new CellConfigDTO("sole", AppConstant.ALIGN_LEFT, lstSole.toArray(new String[0]), 1, 99, 6, 6)
            );
            if (AppConstant.EXPORT_TEMPLATE.equals(exportType)) {
                for (int i = 1; i < 4; i++) {
                    ProductAdminDTO data = new ProductAdminDTO();
                    data.setRecordNo(i);
                    listDataSheet.add(data);
                }
            }
            if (AppConstant.EXPORT_ERRORS.equals(exportType)) {
                for (ProductAdminDTO item : listDataSheet) {
                    item.setRecordNo(recordNo++);
                    item.setMessageStr(String.join(AppConstant.NEXT_LINE, item.getMessageErr()));
                }
            }
        } else {
            for (ProductAdminDTO item : listDataSheet) {
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

    private ProductAdminDTO processRecord(
            List<String> myRecord,
            List<Brand> brandList,
            List<Category> categoryList,
            List<Material> materialList,
            List<Sole> soleList,
            Long typeImport
    ) throws ParseException {
        ProductAdminDTO productAdminDTO = new ProductAdminDTO();
        List<String> messErr = new ArrayList<>();
        List<String> fieldErr = new ArrayList<>();
        String regex = "^[0-9]+$";
        String regexLink = "^(http://|https://).+";
        int col = 1;


        String code = myRecord.get(col++);
        productAdminDTO.setCode(code.trim());
        Product product = prdrp.findByCode(code);
        if (StringUtils.isBlank(code.trim())) {
            messErr.add("Mã không được để trống");
            fieldErr.add("code");
        } else if (code.length() > 250 || code.length() < 0) {
            messErr.add("Mã phải <= 250 kí tự");
            fieldErr.add("code");
        } else if (AppConstant.IMPORT_UPDATE.equals(typeImport)) {
            if (null == product){
                messErr.add("Mã sản phẩm không tồn tại");
                fieldErr.add("code");
            }else {
                productAdminDTO.setId(product.getId());
                productAdminDTO.setCreateDate(product.getCreateDate());
            }
        }else {
            if(null != product){
                messErr.add("Mã đã tồn tại");
                fieldErr.add("code");
            }else {
                productAdminDTO.setCreateDate(Instant.now());
            }
        }

        String name = myRecord.get(col++);
        productAdminDTO.setName(name);
        if (StringUtils.isBlank(code.trim())) {
            messErr.add("Tên không được để trống");
            fieldErr.add("name");
        } else if (code.length() > 250 || code.length() < 0) {
            messErr.add("Tên phải <= 250 kí tự");
            fieldErr.add("name");
        }
        String b = myRecord.get(col++);

        if(StringUtils.isBlank(b)){
            messErr.add("Hãng không được để trống");
            fieldErr.add("brandName");
        }else {
            productAdminDTO.setIdBrand(Long.parseLong(b.split("-")[0]));
            Brand brand = brrp.findById(Long.parseLong(b.split("-")[0])).orElse(null);
            if(null == brand){
                messErr.add("Hãng không tồn tại");
                fieldErr.add("brandName");
            }
        }

        String c = myRecord.get(col++);

        if(StringUtils.isBlank(c)){
            messErr.add("Danh Mục không được để trống");
            fieldErr.add("categoryName");
        }else {
            productAdminDTO.setIdCategory(Long.parseLong(c.split("-")[0]));
            Category category = ctrp.findById(Long.parseLong(c.split("-")[0])).orElse(null);
            if(null == category){
                messErr.add("Danh mục không tồn tại");
                fieldErr.add("categoryName");
            }
        }

        String m = myRecord.get(col++);

        if(StringUtils.isBlank(m)){
            messErr.add("Chất Liệu không được để trống");
            fieldErr.add("materialName");
        }else {
            productAdminDTO.setIdMaterial(Long.parseLong(m.split("-")[0]));
            Material material = mtrp.findById(Long.parseLong(m.split("-")[0])).orElse(null);
            if(null == material){
                messErr.add("Chất Liệu không tồn tại");
                fieldErr.add("materialName");
            }
        }

        String s = myRecord.get(col++);
        if(StringUtils.isBlank(s)){
            messErr.add("Đế giày không được để trống");
            fieldErr.add("soleHeight");
        }else {
            productAdminDTO.setIdSole(Long.parseLong(s.split("-")[0]));
            Sole sole = slrp.findById(Long.parseLong(s.split("-")[0])).orElse(null);
            if(null == sole){
                messErr.add("Đế giày không tồn tại");
                fieldErr.add("soleHeight");
            }
        }


        String price = myRecord.get(col++);

        if(StringUtils.isBlank(price)){
            messErr.add("Giá không được để trống");
            fieldErr.add("price");
        }else if(!price.matches(regex)){
            messErr.add("Vui lòng nhập giá là số");
            fieldErr.add("price");
        }else if(Double.parseDouble(price) < 0){
            messErr.add("Vui lòng nhập giá lớn hơn 0");
            fieldErr.add("price");
        }else {
            productAdminDTO.setPrice(new BigDecimal(price));
        }
        String mota = myRecord.get(col++);
        productAdminDTO.setDescription(mota);
        if(mota.length() > 500){
            messErr.add("Vui lòng nhập mô tả không vượt quá 500 kí tự");
            fieldErr.add("description");
        }
        String status = myRecord.get(col++);
        if(!StringUtils.isBlank(status)){
            productAdminDTO.setStatus(Integer.valueOf(status));
        }
        String anhSanPham = myRecord.get(col++);
        productAdminDTO.setImageNameImport(anhSanPham.trim());
        if(StringUtils.isBlank(anhSanPham)){
            messErr.add("Vui lòng điền ảnh của sản phẩm");
            fieldErr.add("images");
        }else if(!anhSanPham.matches(regexLink)){
            messErr.add("Vui lòng điền ảnh của sản phẩm là link");
            fieldErr.add("images");
        }
        productAdminDTO.setMessageErr(messErr);
        productAdminDTO.setFieldErr(fieldErr);
        return productAdminDTO;
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
