package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.service.ProductDetailAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/admin")
public class ProductDetailAdminController {
    @Autowired
    private ProductDetailAdminService productDetailAdminService;

    @GetMapping("PrdDetail/hien-thi")
    public ResponseEntity<List<ProductDetailAdminDTO>> hienthi(){
        return ResponseEntity.ok(productDetailAdminService.getAll());
    }
    @PostMapping("PrdDetail/add")
    public ResponseEntity<?> add(@RequestBody ProductDetailAdminDTO productAdminDTO) {
        return ResponseEntity.ok(productDetailAdminService.add(productAdminDTO));
    }

    @PutMapping("PrdDetail/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductDetailAdminDTO productDetailAdminDTO) {
        return ResponseEntity.ok(productDetailAdminService.update(productDetailAdminDTO, id));
    }

    @DeleteMapping("PrdDetail/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productDetailAdminService.delete(id));
    }
}
