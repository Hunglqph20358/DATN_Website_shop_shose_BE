package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountDTO;
import com.example.backend.core.admin.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class DiscountResController {
    @Autowired
    private DiscountService discountService;
    @GetMapping("/khuyen-mai")
    public ResponseEntity<?> getAllKhuyenMai(){
        return ResponseEntity.ok(discountService.getAll());
    }
    @PostMapping("/khuyen-mai")
    public ResponseEntity<?> createKhuyenMai(@Valid @RequestBody DiscountDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createKhuyenMai(khuyenMaiDTO));
    }
    @PutMapping("/khuyen-mai")
    public ResponseEntity<?> updateKhuyenMai(@Valid @RequestBody DiscountDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createKhuyenMai(khuyenMaiDTO));
    }
    @DeleteMapping("/khuyen-mai")
    public ResponseEntity<?> deleteKhuyenMai(@Valid @RequestBody DiscountDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createKhuyenMai(khuyenMaiDTO));
    }
}
