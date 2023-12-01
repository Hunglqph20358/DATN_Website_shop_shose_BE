package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class ProductAdminController {
    @Autowired
    private ProductAdminService prdsv;
    @Autowired
    private ProductAdminMapper productAdminMapper;
    @Autowired
    private MaterialAdminService mtsv;
    @Autowired
    private SoleAdminService slsv;
    @Autowired
    private BrandAdminService brsv;
    @Autowired
    private CategoryAdminService ctsv;


    @GetMapping("product/hien-thi")
    public ResponseEntity<List<ProductAdminDTO>> hienthi(){
        return ResponseEntity.ok(prdsv.getAll());
    }
    @PostMapping("product/add")
    public ResponseEntity<?> add(@RequestBody ProductAdminDTO productAdminDTO){
        return ResponseEntity.ok(prdsv.add(productAdminDTO));
    }
    @PutMapping("product/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody ProductAdminDTO productAdminDTO){
        return ResponseEntity.ok(prdsv.update(productAdminDTO,id));
    }
    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(prdsv.delete(id));
    }
    @GetMapping ("product/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id")Long id){
        return ResponseEntity.ok(prdsv.getById(id));
    }
    @GetMapping("product/search/{param}")
    public ResponseEntity<?> searchProduct(
            @PathVariable("param") String param
    ){
        return ResponseEntity.ok(prdsv.findByNameLikeOrCodeLike(param));
    }
}
