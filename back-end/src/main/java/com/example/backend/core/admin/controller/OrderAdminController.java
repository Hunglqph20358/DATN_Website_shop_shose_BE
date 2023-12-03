package com.example.backend.core.admin.controller;


import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.service.OrderAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class OrderAdminController {

    @Autowired
    private OrderAdminService orderAdminService;

    @GetMapping("/get-all-order")
    public ResponseEntity<?> getAllOrderAdmin(@RequestParam(name = "status", required = false, defaultValue = "") Integer status){
        return ResponseEntity.ok(orderAdminService.getAllOrderAdmin(status));
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(orderAdminService.huyDonHang(orderAdminDTO));
    }

    @PostMapping("/progressing-order")
    public ResponseEntity<?> progressingOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(orderAdminService.updateStatusChoXuLy(orderAdminDTO));
    }
    @PostMapping("/complete-order")
    public ResponseEntity<?> completeOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(orderAdminService.hoanThanhDonHang(orderAdminDTO));
    }
    @PostMapping("/ship-order")
    public ResponseEntity<?> shipOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(orderAdminService.giaoHangDonHang(orderAdminDTO));
    }
    @PostMapping("/missed-order")
    public ResponseEntity<?> missedOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(orderAdminService.boLoDonHang(orderAdminDTO));
    }
}
