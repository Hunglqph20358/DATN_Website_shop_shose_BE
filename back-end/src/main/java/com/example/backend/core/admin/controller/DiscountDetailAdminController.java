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
    public ResponseEntity<?> getAllDiscount(){
        return ResponseEntity.ok(discountService.getAll());
    }
    @GetMapping("/product")
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(discountService.getAllProduct());
    }
    @PostMapping("/discount")
    public ResponseEntity<?> createDiscount( @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createDiscount(khuyenMaiDTO));
    }
    @PutMapping("/discount/{idDiscount}")
    public ResponseEntity<?> updateDiscount(@PathVariable Long idDiscount,@Valid @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.updateDiscount(idDiscount,khuyenMaiDTO));
    }
    @DeleteMapping("/discount/{idDiscount}")
    public ResponseEntity<ServiceResult<Void>> deleteDiscount(@PathVariable Long idDiscount) {
        ServiceResult<Void> result = discountService.deleteDiscount(idDiscount);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/discount/{idDiscount}")
    public ResponseEntity<?> getDetailDiscount(@PathVariable Long idDiscount) {
        return ResponseEntity.ok(discountService.getDetailDiscount(idDiscount));
    }
}
