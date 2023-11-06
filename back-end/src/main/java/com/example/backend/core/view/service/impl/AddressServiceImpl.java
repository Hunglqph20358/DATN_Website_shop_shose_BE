package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Address;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.mapper.AddressMapper;
import com.example.backend.core.view.repository.AddressRepository;
import com.example.backend.core.view.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl  implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<AddressDTO> getAllAddress(AddressDTO addressDTO) {
        List<AddressDTO> lst = new ArrayList<>();
       if(addressDTO.getIdCustomer() != null){
           lst = addressMapper.toDto(addressRepository.findByIdCustomerOrderByCreateDateDesc(addressDTO.getIdCustomer()));
       }
        return lst;
    }

    @Override
    public ServiceResult<AddressDTO> getAddress(AddressDTO addressDTO) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressRepository.getAddressByCustomer(addressDTO.getIdCustomer());
        if(null != address){
            result.setMessage("Success");
            result.setData(addressMapper.toDto(address));
            result.setStatus(HttpStatus.OK);
        }
        return result;
    }

    @Override
    public ServiceResult<AddressDTO> save(AddressDTO addressDTO) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressMapper.toEntity(addressDTO);
        address.setCreateDate(Instant.now());
        address = addressRepository.save(address);
        if(address != null){
            result.setMessage("Success!");
            result.setStatus(HttpStatus.OK);
            result.setData(addressDTO);
        }
        return result;
    }
}
