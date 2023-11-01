package com.example.backend.core.view.repository;

import com.example.backend.core.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByIdCustomer(Long idCustomer);
    List<Address> findByIdStaff(Long idStaff);
}
