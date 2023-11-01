package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/get-all-address/by-customer")
    public ResponseEntity<?> getAllAddressByCustomer(@RequestParam(name = "idCustomer") Long idCustomer){
        return ResponseEntity.ok(addressService.findByIdCustomer(idCustomer));
    }
    @GetMapping("/get-all-address/by-staff")
    public ResponseEntity<?> getAllAddressByStaff(@RequestParam(name = "idStaff") Long idStaff){
        return ResponseEntity.ok(addressService.findByIdStaff(idStaff));
    }

    @PostMapping("/create-address")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.save(addressDTO));
    }
}
