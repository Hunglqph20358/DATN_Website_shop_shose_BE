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
    @PutMapping("/voucher")
    public ResponseEntity<?> updateVoucher(@Valid @RequestBody VoucherAdminDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.updateVoucher(voucherAdminDTO));
    }
    @DeleteMapping("/voucher/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
        ServiceResult<Void> result = voucherAdminService.deleteVoucher(id);
        return ResponseEntity.ok(result);
    }
}
