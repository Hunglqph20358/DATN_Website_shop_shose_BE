package com.example.backend.core.security.config.custom;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.repositories.CustomerSPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    CustomerSPRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByUsername(username);
        if (customer==null){
            throw  new UsernameNotFoundException("Khong Tim Thay User");
        }
        System.out.println(CustomerUserDetails.mapCustomerToUserDetail(customer));
        return  CustomerUserDetails.mapCustomerToUserDetail(customer);
    }
}
