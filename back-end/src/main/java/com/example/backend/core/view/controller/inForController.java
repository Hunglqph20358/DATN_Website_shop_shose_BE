package com.example.backend.core.view.controller;

import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.service.CustomerInforSerivce;
import com.example.backend.core.view.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class inForController {
    @Autowired
    private CustomerService  customerService;

    @Autowired
    private CustomerInforSerivce customerInforSerivce;
    @PutMapping("/update-infor/{id}")
    public ResponseEntity<?> updateinFor(
            @PathVariable("id") Customer  customer,
            @RequestBody CustomerDTO customerDTO
            ){
        return ResponseEntity.ok(this.customerService.updateInforCustomer(customerDTO, customer));
    }
    @PutMapping("/changePass/{id}")
    public ResponseEntity<?> changePass(
            @PathVariable("id") Customer customer,
            @RequestBody CustomerDTO customerDTO
    ){
        return ResponseEntity.ok(this.customerInforSerivce.updatePassword(customerDTO, customer));
    }
}
