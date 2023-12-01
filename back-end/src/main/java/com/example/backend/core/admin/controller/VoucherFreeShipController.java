package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.admin.service.VoucherFSService;
import com.example.backend.core.commons.ServiceResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class VoucherFreeShipController {
    @Autowired
    private VoucherFSService voucherAdminService;
    @GetMapping("/voucherFS")
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherAdminService.getAllVouchers());
    }
    @PostMapping("/voucherFS")
    public ResponseEntity<?> createVoucher( @RequestBody VoucherFreeShipDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.createVoucher(voucherAdminDTO));
    }
    @PutMapping("/kichHoatFS/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        return ResponseEntity.ok(voucherAdminService.KichHoat(id));
    }
//    @PutMapping("/voucherFS/{id}")
//    public ResponseEntity<?> updateVoucher(@PathVariable Long id,@Valid @RequestBody VoucherFreeShipDTO voucherAdminDTO){
//        return ResponseEntity.ok(voucherAdminService.updateVoucher(id,voucherAdminDTO));
//    }
//    @DeleteMapping("/voucherFS/{id}")
//    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
//        ServiceResult<Void> result = voucherAdminService.deleteVoucher(id);
//        return ResponseEntity.ok(result);
//    }
//    @GetMapping("/voucherFS/{id}")
//    public ResponseEntity<?> detailVoucher(@PathVariable Long id) {
//        return ResponseEntity.ok( voucherAdminService.detailById(id));
//    }
//    @GetMapping("/customer")
//    public ResponseEntity<?> getAllCustomer(){
//        return ResponseEntity.ok(voucherAdminService.getAllCustomer());
//    }
}
