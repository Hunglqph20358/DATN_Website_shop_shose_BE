package com.example.backend.core.view.controller;

import com.example.backend.core.security.dto.UsersDTO;
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

    @PostMapping("/get-all-address")
    public ResponseEntity<?> getAllAddressByCustomer(@RequestBody UsersDTO usersDTO){
        return ResponseEntity.ok(addressService.getAllAddress(usersDTO));
    }

    @PostMapping("/create-address")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.save(addressDTO));
    }
}
