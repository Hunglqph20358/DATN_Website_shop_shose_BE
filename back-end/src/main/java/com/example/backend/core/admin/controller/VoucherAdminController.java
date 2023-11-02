package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.ServiceResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class VoucherAdminController {
    @Autowired
    private VoucherAdminService voucherAdminService;
    @GetMapping("/voucher")
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherAdminService.getAll());
    }
    @PostMapping("/voucher")
    public ResponseEntity<?> createVoucher( @RequestBody VoucherAdminDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.createVoucher(voucherAdminDTO));
    }
    @PutMapping("/voucher/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id,@Valid @RequestBody VoucherAdminDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.updateVoucher(id,voucherAdminDTO));
    }
    @DeleteMapping("/voucher/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
        ServiceResult<Void> result = voucherAdminService.deleteVoucher(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/voucher/{id}")
    public ResponseEntity<?> detailVoucher(@PathVariable Long id) {
        return ResponseEntity.ok( voucherAdminService.detailById(id));
    }
}
