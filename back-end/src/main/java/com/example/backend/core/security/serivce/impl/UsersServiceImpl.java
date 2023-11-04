package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.UserRepository;
import com.example.backend.core.security.serivce.UserService;
import com.example.backend.core.view.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Users findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public String findByRole(String role) {
        return repository.findByRole(role);
    }

    @Override
    public ServiceResult<Users> saveOrUpdate(SignUpRepquest signUpRepquest) {
        ServiceResult<Users> result = new ServiceResult<>();
        Customer customer = new Customer();
        customer.setCode("KH" + Instant.now().getEpochSecond());
        customer.setFullname(signUpRepquest.getFullname());
        customer.setCreateDate(Instant.now());
        customer.setBirthday(signUpRepquest.getBirthday());
        customer.setGender(signUpRepquest.getGender());
        customer.setPhone(signUpRepquest.getPhone());
        customer = customerRepository.save(customer);
        if(null != customer){
            Users users = new Users();
            users.setUsername(signUpRepquest.getUsername());
            users.setPassword(signUpRepquest.getPassword());
            users.setEmail(signUpRepquest.getEmail());
            users.setCreateDate(Instant.now());
            users.setId_customer(customer.getId().intValue());
            users = repository.save(users);
            result.setData(users);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
        }
        return result;
    }

    @Override
    public boolean isUser(String username) {
        Users users = repository.findByUsername(username);
        if (users.getRole()==null){
            return false;
        }
        return true;
    }


}
