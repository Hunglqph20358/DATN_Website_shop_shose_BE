package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Address;
import com.example.backend.core.view.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> findByIdCustomer(Long idCustomer);
    List<AddressDTO> findByIdStaff(Long idStaff);

    ServiceResult<AddressDTO> save(AddressDTO addressDTO);
}
