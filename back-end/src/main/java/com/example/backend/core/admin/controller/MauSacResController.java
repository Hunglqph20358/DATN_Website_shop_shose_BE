package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.MauSacDTO;
import com.example.backend.core.admin.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class MauSacResController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/mau-sac")
    public ResponseEntity<?> getAllMauSac(@RequestParam(name = "page", defaultValue = "0") Integer page){
        return ResponseEntity.ok(mauSacService.getAllMauSac(page));
    }

    @PostMapping("/mau-sac")
    public ResponseEntity<?> createColor(@Valid  @RequestBody MauSacDTO mauSacDTO){
        return ResponseEntity.ok(mauSacService.createMauSac(mauSacDTO));
    }
}
