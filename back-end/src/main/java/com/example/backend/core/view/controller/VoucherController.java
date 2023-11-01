package com.example.backend.core.view.controller;

import com.example.backend.core.view.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/get-all-voucher")
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherService.getAllVoucher());
    }
}
