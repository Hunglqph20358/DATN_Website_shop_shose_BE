package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.admin.service.VoucherFSService;
import com.example.backend.core.commons.ServiceResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin/voucherFS")
@CrossOrigin("*")
public class VoucherFreeShipController {
    @Autowired
    private VoucherFSService voucherAdminService;
    @GetMapping()
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherAdminService.getAllVouchers());
    }
    @PostMapping()
    public ResponseEntity<?> createVoucher( @RequestBody VoucherFreeShipDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.createVoucher(voucherAdminDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id,@Valid @RequestBody VoucherFreeShipDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.updateVoucher(id,voucherAdminDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
        ServiceResult<Void> result = voucherAdminService.deleteVoucher(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailVoucher(@PathVariable Long id) {
        return ResponseEntity.ok( voucherAdminService.getDetailVoucher(id));
    }
    @PutMapping("/kichHoat/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        return ResponseEntity.ok(voucherAdminService.KichHoat(id));
    }
    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(voucherAdminService.getAllCustomer());
    }
    @GetMapping("/searchByDate")
    public List<VoucherFreeShipDTO> searchByDateRange(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return voucherAdminService.getVouchersByTimeRange(fromDate, toDate);
    }
    @GetMapping("/searchByVoucherFS")
    public List<VoucherFreeShipDTO> searchByVoucher(
            @RequestParam(name = "search")  String search) {

        return voucherAdminService.getVouchersByKeyword(search);
    }
    @GetMapping("/searchByCustomer")
    public List<VoucherFreeShipDTO> searchByCustomer(
            @RequestParam(name = "search")  String search) {

        return voucherAdminService.getVouchersByCustomer(search);
    }
    @GetMapping("/KH")
    public ResponseEntity<?> getAllDiscountKH(){
        return ResponseEntity.ok(voucherAdminService.getAllKichHoat());
    }
    @GetMapping("/KKH")
    public ResponseEntity<?> getAllDiscountKhongKH(){
        return ResponseEntity.ok(voucherAdminService.getAllKhongKH());
    }
}
