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
@RequestMapping("/api/admin/discount")
@CrossOrigin("*")
public class DiscountDetailAdminController {
    @Autowired
    private DiscountDetailAdminService discountService;
    @GetMapping()
    public ResponseEntity<?> getAllDiscount(){
        return ResponseEntity.ok(discountService.getAll());
    }
    @GetMapping("/KH")
    public ResponseEntity<?> getAllDiscountKH(){
        return ResponseEntity.ok(discountService.getAllKichHoat());
    }
    @GetMapping("/KKH")
    public ResponseEntity<?> getAllDiscountKhongKH(){
        return ResponseEntity.ok(discountService.getAllKhongKichHoat());
    }
    @GetMapping("/product")
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(discountService.getAllProduct());
    }
    @PostMapping()
    public ResponseEntity<?> createDiscount( @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        return ResponseEntity.ok(discountService.createDiscount(khuyenMaiDTO));
    }
    @PutMapping("/{idDiscount}")
    public ResponseEntity<?> updateDiscount(@PathVariable(name = "idDiscount") Long idDiscount, @Valid @RequestBody DiscountDetailAdminDTO khuyenMaiDTO){
        System.out.println(idDiscount);
        khuyenMaiDTO.setIdDiscount(idDiscount);
        return ResponseEntity.ok(discountService.updateDiscount(khuyenMaiDTO));
    }
    @PutMapping("/kichHoat/{id}")
    public ResponseEntity<?> kichHoat(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.KichHoat(id));
    }
    @GetMapping("/{idDiscount}")
    public ResponseEntity<?> getDetailDiscount(@PathVariable Long idDiscount) {
        return ResponseEntity.ok(discountService.getDetailDiscount(idDiscount));
    }
    @DeleteMapping("/{idDiscount}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long idDiscount) {
        return ResponseEntity.ok(discountService.deleteDiscount(idDiscount));
    }
    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String code,
                                             @RequestParam(required = false) String name) {
        return ResponseEntity.ok(discountService.getProduct(code,name));
    }
    @GetMapping("/searchByDate")
    public List<DiscountAdminDTO> searchByDateRange(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return discountService.getAllByDateRange(fromDate, toDate);
    }
    @GetMapping("/searchByDiscount")
    public List<DiscountAdminDTO> searchByName(
            @RequestParam(name = "search")  String search) {

        return discountService.getAllByCodeOrName(search);
    }
    @GetMapping("/searchByBrand")
    public List<DiscountAdminDTO> searchByBrand(
            @RequestParam(name = "search")  String search) {

        return discountService.getAllByBrand(search);
    }
    @GetMapping("/searchByCategory")
    public List<DiscountAdminDTO> searchByCategory(
            @RequestParam(name = "search")  String category) {

        return discountService.getAllByCategory(category);
    }
    @GetMapping("/searchByProduct")
    public List<DiscountAdminDTO> searchByProduct(
            @RequestParam(name = "search")  String product) {

        return discountService.getAllByProductNameOrCode(product);
    }
}
