package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.mapper.VoucherAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import com.example.backend.core.admin.repository.VoucherAdminRepository;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherAdminServiceImpl implements VoucherAdminService {
    @Autowired
    private VoucherAdminRepository voucherAdminRepository;
    @Autowired
    private VoucherAdminMapper voucherAdminMapper;

    @Override
    public Page<VoucherAdminDTO> getAllVoucher(Integer i) {
        Pageable pageable = PageRequest.of(i, AppConstant.PAGE_SIZE);
        Page<Voucher> page = voucherAdminRepository.findAll(pageable);
        return page.map(voucherAdminMapper::toDto);
    }

    @Override
    public List<VoucherAdminDTO> getAll() {
        return voucherAdminMapper.toDto(voucherAdminRepository.getAll());
    }

    @Override
    public ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        Voucher voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        voucher.setCode("GG" + Instant.now().getEpochSecond());
        voucher.setCreateDate(Date.valueOf(LocalDate.now()));
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setCreateName("xuân");
        voucher.setUpdateName("");

//        String startDateStr = voucherAdminDTO.getStartDateStr();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate localStartDate = LocalDate.parse(startDateStr, formatter);
//        Instant endDateInstant = localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//        String endDateStr = voucherAdminDTO.getEndDateStr();
//        LocalDate localStartDate2 = LocalDate.parse(endDateStr, formatter);
//        Instant startDateInstant = localStartDate2.atStartOfDay(ZoneId.systemDefault()).toInstant();

        voucher.setStartDate(voucherAdminDTO.getStartDate());
        voucher.setEndDate(voucherAdminDTO.getEndDate());
        voucherAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        // Tìm kiếm đối tượng Voucher trong cơ sở dữ liệu dựa trên id
        Optional<Voucher> voucherOptional = voucherAdminRepository.findById(id);

        if (voucherOptional.isPresent()) {
            Voucher existingVoucher = voucherOptional.get();

            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            existingVoucher.setStartDate(updatedVoucherAdminDTO.getStartDate());
            existingVoucher.setEndDate(updatedVoucherAdminDTO.getEndDate());
            existingVoucher.setDescription(updatedVoucherAdminDTO.getDescription());
           existingVoucher.setVoucherType(updatedVoucherAdminDTO.getVoucherType());
           existingVoucher.setConditionApply(updatedVoucherAdminDTO.getConditionApply());
//           existingVoucher.set(updatedVoucherAdminDTO.getName());
           existingVoucher.setReducedValue(updatedVoucherAdminDTO.getReducedValue());
           existingVoucher.setQuantity(updatedVoucherAdminDTO.getQuantity());

            // Lưu đối tượng Voucher đã cập nhật
            voucherAdminRepository.save(existingVoucher);

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
        Optional<Voucher> voucher = voucherAdminRepository.findById(voucherId);

        if (voucher.isPresent()) {
            Voucher voucher1 = voucher.get();
            voucher1.setIdel(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherAdminRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
    public List<VoucherAdminDTO> detailById(Long voucherId) {
        List<VoucherAdminDTO> list = new ArrayList<>();

        Optional<Voucher> voucherOptional = voucherAdminRepository.findById(voucherId);

        if (voucherOptional.isPresent()) {
            Voucher voucher = voucherOptional.get();
            VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

           list.add(voucherAdminDTO);
        }

        return list;
    }
}
