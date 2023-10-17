package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.PromotionDTO;
import com.example.backend.core.admin.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class PromotionResController {
    @Autowired
    private PromotionService khuyenMaiService;
    @GetMapping("/khuyen-mai")
    public ResponseEntity<?> getAllKhuyenMai(){
        return ResponseEntity.ok(khuyenMaiService.getAll());
    }
    @PostMapping("/khuyen-mai")
    public ResponseEntity<?> createKhuyenMai(@Valid @RequestBody PromotionDTO khuyenMaiDTO){
        return ResponseEntity.ok(khuyenMaiService.createKhuyenMai(khuyenMaiDTO));
    }
    @PutMapping("/khuyen-mai")
    public ResponseEntity<?> updateKhuyenMai(@Valid @RequestBody PromotionDTO khuyenMaiDTO){
        return ResponseEntity.ok(khuyenMaiService.createKhuyenMai(khuyenMaiDTO));
    }
    @DeleteMapping("/khuyen-mai")
    public ResponseEntity<?> deleteKhuyenMai(@Valid @RequestBody PromotionDTO khuyenMaiDTO){
        return ResponseEntity.ok(khuyenMaiService.createKhuyenMai(khuyenMaiDTO));
    }
}
