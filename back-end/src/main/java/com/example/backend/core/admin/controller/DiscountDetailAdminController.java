package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class DiscountDetailAdminController {
    @Autowired
    private DiscountDetailAdminService discountService;
    @GetMapping("/discount")
    public ResponseEntity<?> getAllKhuyenMai(){
        return ResponseEntity.ok(discountService.getAll());
    }
    @PostMapping("/discount")
    public ResponseEntity<?> createKhuyenMai( @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createKhuyenMai(khuyenMaiDTO));
    }
    @PutMapping("/discount")
    public ResponseEntity<?> updateKhuyenMai(@Valid @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.updateKhuyenMai(khuyenMaiDTO));
    }
    @DeleteMapping("/discount/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteKhuyenMai(@PathVariable Long id) {
        ServiceResult<Void> result = discountService.deleteKhuyenMai(id);
        return ResponseEntity.ok(result);
    }
}
