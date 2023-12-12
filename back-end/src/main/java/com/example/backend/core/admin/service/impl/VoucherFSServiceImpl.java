package com.example.backend.core.admin.service.impl;
import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.VoucherFSCustomerRepository;
import com.example.backend.core.admin.repository.VoucherFreeShipRepository;
import com.example.backend.core.admin.service.VoucherFSService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.view.mapper.VoucherFSMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherFSServiceImpl implements VoucherFSService {
    @Autowired
    private VoucherFreeShipRepository voucherFreeShipRepository;
    @Autowired
    private CustomerAdminRepository  customerAdminRepository;

    @Autowired
    private VoucherFSMapper voucherAdminMapper;
    @Autowired
    private CustomerAdminMapper customerAdminMapper;
    @Autowired
    private VoucherFSCustomerRepository voucherFSCustomerRepository;


    @Override
    public List<VoucherFreeShipDTO> getAllVouchers() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllVouchers();
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKichHoat() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllKichHoat();
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKhongKH() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllKhongKH();
        return list;
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByTimeRange(Date fromDate, Date toDate) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByTimeRange(fromDate,toDate);
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByKeyword(keyword);
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByCustomer(searchTerm);
        return list;
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherAdminDTO) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();
        VoucherFreeShip voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucher.setCode("GG" + Instant.now().getEpochSecond());
        voucher.setCreateDate(java.util.Date.from(Instant.now()));
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setCreateName(voucherAdminDTO.getCreateName());
        voucher.setStartDate(voucherAdminDTO.getStartDate());
        voucher.setEndDate(voucherAdminDTO.getEndDate());
        voucher.setAllow(voucher.getAllow());
        if(voucher.getOptionCustomer()==0){
            voucher.setIdCustomer(null);
        }else {
            StringBuilder customer = new StringBuilder();
            for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                CustomerAdminDTO customerAdminDTO=voucherAdminDTO.getCustomerAdminDTOList().get(i);
                customer.append(customerAdminDTO.getId());
                customer.append(",");
            }
            voucher.setIdCustomer(Long.parseLong(String.valueOf(customer)));
        }
        voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
        voucherFreeShipRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherFreeShipDTO updatedVoucherAdminDTO) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();

        // Tìm kiếm đối tượng Voucher trong cơ sở dữ liệu dựa trên id
        Optional<VoucherFreeShip> voucherOptional = voucherFreeShipRepository.findById(id);

        if (voucherOptional.isPresent()) {
            VoucherFreeShip existingVoucher = voucherOptional.get();

            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            existingVoucher.setStartDate(updatedVoucherAdminDTO.getStartDate());
            existingVoucher.setEndDate(updatedVoucherAdminDTO.getEndDate());
            existingVoucher.setDescription(updatedVoucherAdminDTO.getDescription());
            existingVoucher.setConditions(updatedVoucherAdminDTO.getConditions());
            existingVoucher.setReducedValue(updatedVoucherAdminDTO.getReducedValue());
            existingVoucher.setQuantity(updatedVoucherAdminDTO.getQuantity());

            // Lưu đối tượng Voucher đã cập nhật
            voucherFreeShipRepository.save(existingVoucher);

            serviceResult.setData(updatedVoucherAdminDTO);
            serviceResult.setMessage("Cập nhật thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setData(null);
            serviceResult.setMessage("Không tìm thấy Voucher cần cập nhật");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }


    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> voucher = voucherFreeShipRepository.findById(voucherId);

        if (voucher.isPresent()) {
            VoucherFreeShip voucher1 = voucher.get();
            voucher1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherFreeShipRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
//    @Override
//    public List<VoucherFreeShipDTO> detailById(Long voucherId) {
//        List<VoucherAdminDTO> list = new ArrayList<>();
//
//        // Thực hiện câu truy vấn JOIN để lấy thông tin từ cả bảng voucher và customer
//        String query = "SELECT "
//                + "c.code as customer_code, c.fullname as customer_fullname, "
//                + " v.code as voucher_code, v.name as voucher_name, v.start_date as voucher_start_date, v.end_date as voucher_end_date, "
//                + "v.conditions as voucher_conditions, v.create_name as voucher_create_name, v.voucher_type as voucher_type, "
//                + "v.reduced_value as voucher_reduced_value, v.description as voucher_description, v.status as voucher_status, "
//                + "v.quantity as voucher_quantity, v.max_reduced as voucher_max_reduced, v.limit_customer as voucher_limit_customer, v.allow as voucher_allow "
//                + " FROM\n" +
//                "       voucher v \n" +
//                "    LEFT JOIN\n" +
//                "        customer c ON c.id = v.id_customer "
//                + "WHERE v.id = :voucherId";
//
//        Query nativeQuery = entityManager.createNativeQuery(query);
//        nativeQuery.setParameter("voucherId", voucherId);
//
//        List<Object[]> result = nativeQuery.getResultList();
//        // Chuyển kết quả từ nativeQuery sang DTO
//        for (Object[] row : result) {
//            VoucherFreeShipDTO voucherAdminDTO = new VoucherFreeShipDTO();
//            CustomerAdminDTO customerAdminDTO=new CustomerAdminDTO();
//            customerAdminDTO.setCode((String) row[0]);
//            customerAdminDTO.setFullname((String) row[1]);
//
//
//            voucherAdminDTO.setCode((String) row[2]);
//            voucherAdminDTO.setName((String) row[3]);
////            voucherAdminDTO.setStartDate((Date) row[4]);
////            voucherAdminDTO.setEndDate((Date) row[5]);
//            voucherAdminDTO.setConditions((BigDecimal) row[6]);
//            voucherAdminDTO.setCreateName((String) row[7]);
//            voucherAdminDTO.setVoucherType((Integer) row[8]);
//            voucherAdminDTO.setReducedValue((BigDecimal) row[9]);
//            voucherAdminDTO.setDescription((String) row[10]);
//            voucherAdminDTO.setStatus((Integer) row[11]);
//            voucherAdminDTO.setQuantity((Integer) row[12]);
//            voucherAdminDTO.setMaxReduced((BigDecimal) row[13]);
//            voucherAdminDTO.setLimitCustomer((Integer) row[14]);
//            voucherAdminDTO.setAllow((Integer) row[15]);
//            list.add(voucherAdminDTO);
//            List<CustomerAdminDTO> customerAdminDTOList = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                customerAdminDTOList.add(customerAdminDTO);
//                list.get(i).setCustomerAdminDTOList(customerAdminDTOList);
//            }
//        }
//
//        return list;
//    }
    @Override
    public VoucherFreeShipDTO getDetailVoucher(Long id) {
        Optional<VoucherFreeShip> optionalVoucher = voucherFreeShipRepository.findById(id);

        VoucherFreeShip voucher = optionalVoucher.orElse(null);

        if (voucher == null) {
            return null; // hoặc ném một exception để thông báo rằng không tìm thấy voucher với ID đã cho
        }

        VoucherFreeShipDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

        Optional<Customer> optionalCustomer = customerAdminRepository.findById(voucher.getIdCustomer());

        Customer customer = optionalCustomer.orElse(null);

        if (customer == null) {
            return null; // hoặc ném một exception để thông báo rằng không tìm thấy khách hàng với ID của voucher
        }

        CustomerAdminDTO customerAdminDTO = customerAdminMapper.toDto(customer);

        voucherAdminDTO.setCustomerAdminDTO(customerAdminDTO);

        return voucherAdminDTO;
    }



    @Override
    public List<CustomerAdminDTO> getAllCustomer() {
        List<CustomerAdminDTO> list= voucherFSCustomerRepository.getAllCustomer();
        return list;
    }
    @Override
    public ServiceResult<Void> KichHoat(Long idVoucher) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> optionalVoucher = voucherFreeShipRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            VoucherFreeShip voucher = optionalVoucher.get();

            if (voucher.getIdel() ==1) {
                voucher.setIdel(0);

            } else {
                voucher.setIdel(1);
            }
            voucherFreeShipRepository.save(voucher); // Lưu lại thay đổi vào cơ sở dữ liệu
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }
}
