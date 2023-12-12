package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.mapper.VoucherAdminMapper;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.VoucherAdminCustomRepository;
import com.example.backend.core.admin.repository.VoucherAdminRepository;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.OrderDetailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherAdminServiceImpl implements VoucherAdminService {
    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    private VoucherAdminRepository voucherAdminRepository;
    @Autowired
    private CustomerAdminRepository customerAdminRepository;
    @Autowired
    private VoucherAdminMapper voucherAdminMapper;
    @Autowired
    private CustomerAdminMapper customerAdminMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VoucherAdminCustomRepository voucherAdminCustomRepository;

    public VoucherAdminServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }


    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("hunglqph20358@fpt.edu.vn");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(VoucherAdminDTO voucherAdminDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        Customer customer = customerRepository.findById(voucherAdminDTO.getIdCustomer()).get();
        String emailTo = customer.getEmail();
        String subject = " Voucher xịn 2T Store tặng bạn";
        thymeleafContext.setVariable("voucher", voucherAdminDTO);
        String htmlBody = templateEngine.process("sendEmailVoucher", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
    }

    @Override
    public List<VoucherAdminDTO> getAllVouchers() {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getAllVouchers();
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getAllKichHoat() {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getAllKichHoat();
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getAllKhongKH() {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getAllKhongKH();
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByTimeRange(Date fromDate, Date toDate) {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getVouchersByTimeRange(fromDate,toDate);
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByKeyword(String keyword) {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getVouchersByKeyword(keyword);
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm) {
        List<VoucherAdminDTO> list= voucherAdminCustomRepository.getVouchersByCustomer(searchTerm);
        return list;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        Voucher voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucher.setCode("GG" + Instant.now().getEpochSecond());
        voucher.setCreateDate(java.util.Date.from(Instant.now()));
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setCreateName(voucherAdminDTO.getCreateName());
        voucher.setStartDate(voucherAdminDTO.getStartDate());
        voucher.setEndDate(voucherAdminDTO.getEndDate());
        voucher.setAllow(voucher.getAllow());
        if (voucher.getVoucherType() == 0) {
            voucher.setMaxReduced(voucher.getMaxReduced());
        }
        if (voucher.getOptionCustomer() == 0) {
            voucher.setIdCustomer(null);
        } else {
            StringBuilder customer = new StringBuilder();

            for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
                customer.append(customerAdminDTO.getId());
                customer.append(",");
            }

// Kiểm tra xem có phần tử nào trong danh sách không
            if (customer.length() > 0) {
                // Cắt bỏ dấu phẩy cuối cùng
                customer.setLength(customer.length() - 1);

                // Tách chuỗi thành mảng các giá trị id
                String[] idArray = customer.toString().split(",");

                // Tạo danh sách để lưu các giá trị id được chuyển đổi thành Long
                List<Long> idList = new ArrayList<>();

                // Chuyển đổi từng giá trị thành Long và thêm vào danh sách
                for (String id : idArray) {
                    voucher.setIdCustomer(Long.parseLong(id.trim()));
                }

            } else {
                // Xử lý trường hợp không có customer nào
                voucher.setIdCustomer(null); // hoặc gán giá trị mong muốn khác
            }
        }
        voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
        voucherAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        // Tìm kiếm đối tượng Voucher trong cơ sở dữ liệu dựa trên id
        Optional<Voucher> voucherOptional = voucherAdminRepository.findById(id);

        if (voucherOptional.isPresent()) {
            Voucher voucher = voucherOptional.get();

            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            voucher.setStatus(0);
            voucher.setCreateName(voucherAdminDTO.getCreateName());
            voucher.setStartDate(voucherAdminDTO.getStartDate());
            voucher.setEndDate(voucherAdminDTO.getEndDate());
            voucher.setAllow(voucher.getAllow());
            if (voucher.getVoucherType() == 0) {
                voucher.setMaxReduced(voucher.getMaxReduced());
            }
            if (voucher.getOptionCustomer() == 0) {
                voucher.setIdCustomer(null);
            } else {
                StringBuilder customer = new StringBuilder();

                for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                    CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
                    customer.append(customerAdminDTO.getId());
                    customer.append(",");
                }

// Kiểm tra xem có phần tử nào trong danh sách không
                if (customer.length() > 0) {
                    // Cắt bỏ dấu phẩy cuối cùng
                    customer.setLength(customer.length() - 1);

                    // Tách chuỗi thành mảng các giá trị id
                    String[] idArray = customer.toString().split(",");

                    // Tạo danh sách để lưu các giá trị id được chuyển đổi thành Long
                    List<Long> idList = new ArrayList<>();

                    // Chuyển đổi từng giá trị thành Long và thêm vào danh sách
                    for (String i : idArray) {
                        voucher.setIdCustomer(Long.parseLong(i.trim()));
                    }

                } else {
                    // Xử lý trường hợp không có customer nào
                    voucher.setIdCustomer(null); // hoặc gán giá trị mong muốn khác
                }
            }
            voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
            voucherAdminRepository.save(voucher);

            serviceResult.setData(voucherAdminDTO);
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
        Optional<Voucher> voucher = voucherAdminRepository.findById(voucherId);

        if (voucher.isPresent()) {
            Voucher voucher1 = voucher.get();
            voucher1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherAdminRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

//    @Override
//    public List<VoucherAdminDTO> detailById(Long voucherId) {
//        List<VoucherAdminDTO> list = new ArrayList<>();
//
//        // Thực hiện câu truy vấn JOIN để lấy thông tin từ cả bảng voucher và customer
//        String query = "SELECT "
//                + "c.code as customer_code, c.fullname as customer_fullname, "
//                + " v.code as voucher_code, v.name as voucher_name, v.start_date as voucher_start_date, v.end_date as voucher_end_date, "
//                + "v.conditions as voucher_conditions, v.create_name as voucher_create_name, v.voucher_type as voucher_type, "
//                + "v.reduced_value as voucher_reduced_value, v.description as voucher_description, v.status as voucher_status, "
//                + "v.quantity as voucher_quantity, v.max_reduced as voucher_max_reduced, v.limit_customer as voucher_limit_customer, v.allow as voucher_allow ,v.apply "
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
//            VoucherAdminDTO voucherAdminDTO = new VoucherAdminDTO();
//            CustomerAdminDTO customerAdminDTO = new CustomerAdminDTO();
//            customerAdminDTO.setCode((String) row[0]);
//            customerAdminDTO.setFullname((String) row[1]);
//
//
//            voucherAdminDTO.setCode((String) row[2]);
//            voucherAdminDTO.setName((String) row[3]);
//            voucherAdminDTO.setStartDate((Date) row[4]);
//            voucherAdminDTO.setEndDate((Date) row[5]);
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
//            voucherAdminDTO.setApply((Integer) row[16]);
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
    public VoucherAdminDTO getDetailVoucher(Long id) {
        Optional<Voucher> optionalVoucher = voucherAdminRepository.findById(id);

        Voucher voucher = optionalVoucher.orElse(null);

        if (voucher == null) {
            return null; // hoặc ném một exception để thông báo rằng không tìm thấy voucher với ID đã cho
        }

        VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

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
        List<CustomerAdminDTO> list= voucherAdminCustomRepository.getAllCustomer();
        return list;
    }

    @Override
    public ServiceResult<Void> KichHoat(Long idVoucher) throws MessagingException {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<Voucher> optionalVoucher = voucherAdminRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();

            // Invert the value of idel
            voucher.setIdel(voucher.getIdel() == 1 ? 0 : 1);

            VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

            // Ensure that the email sending logic is within the transaction
            try {
                sendMessageUsingThymeleafTemplate(voucherAdminDTO);
            } catch (MessagingException e) {
                // Handle the exception or log it, depending on your requirements
                serviceResult.setMessage("Lỗi khi gửi email");
                serviceResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                return serviceResult;
            }

            voucherAdminRepository.save(voucher); // Lưu lại thay đổi vào cơ sở dữ liệu
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }
}
