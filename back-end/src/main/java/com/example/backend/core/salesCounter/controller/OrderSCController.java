package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import com.example.backend.core.salesCounter.service.OrderSalesCounterDetailService;
import com.example.backend.core.salesCounter.service.OrderSalesCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales-counter/api")
@CrossOrigin("*")
public class OrderSCController {
    @Autowired
    private OrderSalesCounterService service;
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrderSC(@RequestBody OrderSalesDTO orderSalesDTO){
        return ResponseEntity.ok(service.createOrderSales(orderSalesDTO));
    }
}
