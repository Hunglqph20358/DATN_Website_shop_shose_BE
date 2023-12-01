package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    @GetMapping("/discountKH")
    public ResponseEntity<?> getAllDiscountKH(){
        return ResponseEntity.ok(discountService.getAllKichHoat());
    }
    @GetMapping("/discountKKH")
    public ResponseEntity<?> getAllDiscountKhongKH(){
        return ResponseEntity.ok(discountService.getAllKhongKichHoat());
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
    @PutMapping("/kichHoatD/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.KichHoat(id));
    }
    @GetMapping("/discount/{idDiscount}")
    public ResponseEntity<?> getDetailDiscount(@PathVariable Long idDiscount) {
        return ResponseEntity.ok(discountService.getDetailDiscount(idDiscount));
    }
    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String code,
                                             @RequestParam(required = false) String name) {
        return ResponseEntity.ok(discountService.getProduct(code,name));
    }
    @GetMapping("/search")
    public List<DiscountAdminDTO> searchByDateRange(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return discountService.getAllByDateRange(fromDate, toDate);
    }
    @GetMapping("/searchByName")
    public List<DiscountAdminDTO> searchByName(
            @RequestParam(name = "search")  String search) {

        return discountService.getAllByCodeOrName(search);
    }
}
